package dbg.misc.calc.drive2;

import dbg.misc.calc.LeverAnglesSensor;
import dbg.misc.calc.drive.CncCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitri on 23.01.16.
 */
public class CommandProcessingContext implements PositionAware, LeversActuator{

    private List<DrivePush> pushList = new ArrayList<>();

    private final CncCommand command;

    private LeverAnglesSensor initialSensors;
    private LeverAnglesSensor reachedSensors;
    private long commandStartedTime;

    public CommandProcessingContext(CncCommand command, LeverAnglesSensor initialSensors) {
        this.command = command;
        this.initialSensors = initialSensors;
        commandStartedTime = System.currentTimeMillis();
    }

    class PositionReport {
        LeverAnglesSensor sensor;
        long time;

        public PositionReport(LeverAnglesSensor sensor) {
            this.sensor = sensor;
            this.time = System.currentTimeMillis();
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
    public void blinkBoth(DrivePush left, DrivePush right) {
        slideCommands.add(new PushPair(left, right));
    }

    @Override
    public void onPositionReport(LeverAnglesSensor sensors) {
        positionReports.add(new PositionReport(sensors));
    }

    @Override
    public String toString() {
        return "CommandProcessingContext{" +
                "pushList=" + pushList +
                ", command=" + command +
                ", initialSensors=" + initialSensors +
                ", reachedSensors=" + reachedSensors +
                ", positionReports=" + positionReports +
                ", slideCommands=" + slideCommands +
                '}';
    }
}