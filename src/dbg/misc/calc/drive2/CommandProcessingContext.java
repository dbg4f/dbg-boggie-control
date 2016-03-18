package dbg.misc.calc.drive2;

import dbg.misc.calc.LeverAnglesSensor;
import dbg.misc.calc.drive.CncCommand;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by dmitri on 23.01.16.
 */
public class CommandProcessingContext implements PositionAware, LeversActuator{

    private final CncCommand command;

    private LeverAnglesSensor initialSensors;
    private LeverAnglesSensor targetSensors;
    private LeverAnglesSensor reachedSensors;
    private long commandStartedTime;

    public CommandProcessingContext(CncCommand command, LeverAnglesSensor initialSensors) {
        this.command = command;
        this.initialSensors = initialSensors;
        commandStartedTime = System.currentTimeMillis();
    }

    public void setTargetSensors(LeverAnglesSensor targetSensors) {
        this.targetSensors = targetSensors;
    }

    public LeverAnglesSensor getInitialSensors() {
        return initialSensors;
    }

    class PositionReport {
        LeverAnglesSensor sensor;
        long time;

        public PositionReport(LeverAnglesSensor sensor) {
            this.sensor = sensor;
            this.time = System.currentTimeMillis();
        }


        public String formatShort() {
            return sensor.formatShort();
        }

        @Override
        public String toString() {
            return "PositionReport{" +
                    "sensor=" + sensor +
                    ", time=" + time +
                    '}';
        }
    }

    private List<PositionReport> positionReports = new ArrayList<>();
    private List<PushPair> slideCommands = new ArrayList<>();

    public void finalizeCommand(LeverAnglesSensor sensor) {
        reachedSensors = sensor;
    }

    public CncCommand getCommand() {
        return command;
    }

    public long timeElapsed() {
        return System.currentTimeMillis() - commandStartedTime;
    }


    @Override
    public void blinkBoth(PushPair pushPair) {
        slideCommands.add(pushPair);
    }

    @Override
    public void onPositionReport(LeverAnglesSensor sensors) {
        positionReports.add(new PositionReport(sensors));
    }

    public String sensors(LeverAnglesSensor sensor, String text) {
        return String.format("%10s %s", text, sensor != null ? sensor.formatShort() : "N/A");
    }

    public String sensorsWithDiff(LeverAnglesSensor sensor, LeverAnglesSensor target, String text) {
        return String.format("%10s %s (%s)", text, sensor.formatShort(), sensor.difference(target).formatShort());
    }

    public String pushPair(PushPair pair) {

        return String.format("L %s - R %s",
                pair.getLeft().report(),
                pair.getRight().report());

    }

    public void addLogRecord(long time, String line, SortedMap<Long, List<String>> logRows) {

        if (!logRows.containsKey(time)) {
            logRows.put(time, new ArrayList<>());
        }

        logRows.get(time).add(line);

    }

    public String summary() {


        String summary = "\n";


        LeverAnglesSensor last = null;

        summary += " start " + formatDate(new Date(commandStartedTime)) + "\n";

        SortedMap<Long, List<String>> logRows = new TreeMap<>();


        for (PositionReport report : positionReports) {
            long time = report.time - commandStartedTime;
            addLogRecord(time, sensorsWithDiff(report.sensor, targetSensors, ""), logRows);
            //addLogRecord(time, report.sensor.formatShort(), logRows);;
            last = report.sensor;
        }


        for (PushPair pair : slideCommands) {
            long time = pair.time - commandStartedTime;
            addLogRecord(time, "-----> " + pushPair(pair), logRows);
        }


        summary += sensorsWithDiff(initialSensors, targetSensors, "initial") + "\n";
        summary += sensors(targetSensors,  "target") + "\n";
        summary += sensors(reachedSensors, "reached") + "\n";
        summary += sensorsWithDiff(last, targetSensors, "last") + "\n";

        for (Map.Entry<Long, List<String>> entry : logRows.entrySet()) {

            for (String line : entry.getValue()) {
                summary += String.format("%10s %s", String.valueOf(entry.getKey()), line) + "\n";
            }

        }



        return summary;

    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(date);
    }


    @Override
    public String toString() {

        return summary();
/*
        return "CommandProcessingContext{" +
                "command=" + command +
                ", initialSensors=" + initialSensors +
                ", targetSensors=" + targetSensors +
                ", reachedSensors=" + reachedSensors +
                ", commandStartedTime=" + commandStartedTime +
                ", positionReports=" + positionReports +
                ", slideCommands=" + slideCommands +
                '}';*/
    }
}
