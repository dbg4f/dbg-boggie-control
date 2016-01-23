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

    public CommandProcessingContext(CncCommand command) {
        this.command = command;
    }

    class BlinkPair {
        DrivePush left;
        DrivePush right;
        long time;

        public BlinkPair(DrivePush left, DrivePush right) {
            this.left = left;
            this.right = right;
            this.time = System.currentTimeMillis();
        }
    }

    class PositionReport {
        LeverAnglesSensor sensor;
        long time;

        public PositionReport(LeverAnglesSensor sensor) {
            this.sensor = sensor;
            this.time = System.currentTimeMillis();
        }
    }

    private List<PositionReport> positionReports = new ArrayList<>();
    private List<BlinkPair> slideCommands = new ArrayList<>();

    @Override
    public void blinkBoth(DrivePush left, DrivePush right) {
        slideCommands.add(new BlinkPair(left, right));
    }

    @Override
    public void onPositionReport(LeverAnglesSensor sensors) {
        positionReports.add(new PositionReport(sensors));
    }
}
