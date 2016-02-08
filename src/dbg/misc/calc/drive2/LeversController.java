package dbg.misc.calc.drive2;

import dbg.misc.calc.CartesianPoint;
import dbg.misc.calc.LeverAnglesSensor;
import dbg.misc.calc.LeversPosition;
import dbg.misc.calc.SensorPwmDirectionDependency;
import dbg.misc.calc.drive.CncCommand;
import dbg.misc.calc.drive.CncCommandCode;
import dbg.misc.calc.drive2.push.PushCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dmitri on 10.01.16.
 */
public class LeversController implements PositionAware {

    private static Logger log = LoggerFactory.getLogger(LeversController.class);

    public static final double POSITIONING_PRECISION = 0.001;
    public static final int MAX_SENSORS_AGE = 1000;
    public static final int CMD_TIMEOUT_MSEC = 5000;
    enum State {
        IDLE,
        COMMAND_PROCESSING,
        ERROR
    }

    enum PositioningStatus {
        NOT_REACHED,
        REACHED,
        OVERSHOOT,
        TIMEOUT
    }

    private State state = State.IDLE;

    private LeverAnglesSensor lastSensors;

    private long lastSensorsTime;

    private LeversActuator actuator;

    private CommandQueue commandQueue;

    private LeversPosition position;

    private PositioningRestrictions positioningRestrictions;

    private CommandProcessingContext currentCommandProcessingContext;

    private PushCalculator pushCalculator;

    private ContextArchive contextArchive = new ContextArchive();

    private SensorPwmDirectionDependency dependency = SensorPwmDirectionDependency.STRAIGHT;

    public void setActuator(LeversActuator actuator) {
        this.actuator = actuator;
    }

    public void setPosition(LeversPosition position) {
        this.position = position;
    }

    public void setPositioningRestrictions(PositioningRestrictions positioningRestrictions) {
        this.positioningRestrictions = positioningRestrictions;
    }

    public void setPushCalculator(PushCalculator pushCalculator) {
        this.pushCalculator = pushCalculator;
    }

    public void setContextArchive(ContextArchive contextArchive) {
        this.contextArchive = contextArchive;
    }

    public ContextArchive getContextArchive() {
        return contextArchive;
    }

    public void setCommandQueue(CommandQueue commandQueue) {
        this.commandQueue = commandQueue;
    }

    public void onPositionReport(LeverAnglesSensor sensors) {



        // archive each report
        // keep previous positions to detect speed
        // detect when pen is out of working area
        // detect when command not executed or not sanctioned move occurred

        // check if position is updated (not older than 1 sec)
        //

        log.info("Sensors: " + sensors + " pos " + position.penByAdc(sensors).round());

        lastSensors = sensors;
        lastSensorsTime = System.currentTimeMillis();

        if (currentCommandProcessingContext != null) {
            currentCommandProcessingContext.onPositionReport(sensors);
        }

    }

    public LeverAnglesSensor getLastSensors() {
        return lastSensors;
    }



    public boolean isSensorReady() {

        return lastSensors != null && System.currentTimeMillis() - lastSensorsTime < MAX_SENSORS_AGE;

    }

    public void onTimer() {

        if (state != State.ERROR) {

            if (isSensorReady()) {

                if (state == State.IDLE) {

                    CncCommand command = acquireCommand();

                    if (command != null) {

                        state = State.ERROR;

                        validateCommand(command);

                        state = State.COMMAND_PROCESSING;
                    }

                }

                CncCommand command = null;

                if (currentCommandProcessingContext != null) {

                    command = currentCommandProcessingContext.getCommand();

                }


                if (command != null) {

                    LeverAnglesSensor currentSensors = lastSensors;

                    CartesianPoint currentPen = position.penByAdc(lastSensors);

                    CartesianPoint targetPen = new CartesianPoint(command.getX(), command.getY());

                    LeverAnglesSensor targetSensor = position.adcByPen(targetPen);

                    currentCommandProcessingContext.setTargetSensors(targetSensor);

                    LeverAnglesSensor diffToTarget = currentSensors.difference(targetSensor);

                    LeverAnglesSensor fullDifference = currentCommandProcessingContext.getInitialSensors().difference(targetSensor);

                    PositioningStatus positioningStatus = positioningStatus(diffToTarget, fullDifference);

                    log.info("Positioning status: " + positioningStatus + " pen " + currentPen + " diff " + diffToTarget);

                    switch (positioningStatus) {

                        case TIMEOUT:
                        case OVERSHOOT:
                        case REACHED:

                            finalizeCommand();

                            break;

                        case NOT_REACHED:

                            applyPush(diffToTarget);

                            break;

                        default:
                            throw new IllegalStateException("Unknown positioning status: " + positioningStatus);

                    }

                }

            }

        }

    }

    private void applyPush(LeverAnglesSensor diffToTarget) {
        DrivePush drivePushLeft = pushCalculator.calc(diffToTarget.left);
        DrivePush drivePushRight = pushCalculator.calc(diffToTarget.right);

        PushPair pushPair = new PushPair(drivePushLeft, drivePushRight);

        log.info("Diff " + diffToTarget + ", push " + drivePushLeft + " " + drivePushRight);

        currentCommandProcessingContext.blinkBoth(pushPair);

        actuator.blinkBoth(pushPair);
    }

    private void finalizeCommand() {
        log.info("Finalize command: " + currentCommandProcessingContext);

        currentCommandProcessingContext.finalizeCommand(lastSensors);

        currentCommandProcessingContext = null;

        state = State.IDLE;
    }

    private CncCommand acquireCommand() {
        CncCommand command = commandQueue.getNext();

        if (command != null) {

            currentCommandProcessingContext = new CommandProcessingContext(command, lastSensors);

            contextArchive.addCommandContext(currentCommandProcessingContext);

        }

        return command;
    }

    private void validateCommand(CncCommand command) {
        if (command.getCode() != CncCommandCode.LINE_TO) {
            throw new IllegalStateException("LINE_TO command supported only");
        }

        CartesianPoint targetPen = new CartesianPoint(command.getX(), command.getY());

        if (!positioningRestrictions.inBorders(targetPen)) {
            throw new IllegalArgumentException("Target position " + targetPen + " is out of working area " + positioningRestrictions.getWorkingArea());
        }
    }


    private PositioningStatus positioningStatus(LeverAnglesSensor diff, LeverAnglesSensor fullDifference) {

        if (isDifferenceSmallEnough(diff)) {
            return PositioningStatus.REACHED;
        }

        if (currentCommandProcessingContext.timeElapsed() > CMD_TIMEOUT_MSEC) {
            return PositioningStatus.TIMEOUT;
        }

        if (!fullDifference.isSameSign(diff)) {
            return PositioningStatus.OVERSHOOT;
        }

        return PositioningStatus.NOT_REACHED;

    }

    private boolean isDifferenceSmallEnough(LeverAnglesSensor diff) {
        return isSmallEnough(diff.left) && isSmallEnough(diff.right);
    }

    private boolean isSmallEnough(double difference) {
        return Math.abs(difference) < POSITIONING_PRECISION;
    }


}
