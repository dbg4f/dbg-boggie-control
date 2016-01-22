package dbg.misc.calc.drive2;

import dbg.misc.calc.CartesianPoint;
import dbg.misc.calc.LeverAnglesSensor;
import dbg.misc.calc.LeversPosition;
import dbg.misc.calc.drive.CncCommand;
import dbg.misc.calc.drive.CncCommandCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dmitri on 10.01.16.
 */
public class LeversController implements PositionAware {

    public static final double POSITIONING_PRECISION = 0.001;
    public static final int MAX_SENSORS_AGE = 1000;

    enum State {
        IDLE,
        COMMAND_PROCESSING,
        ERROR
    }

    private static Logger log = LoggerFactory.getLogger(LeversController.class);


    private State state;

    private LeverAnglesSensor lastSensors;

    private long lastSensorsTime;

    private LeversActuator actuator;

    private CommandQueue commandQueue;

    private LeversPosition position;

    private PositioningRestrictions positioningRestrictions;

    public void setActuator(LeversActuator actuator) {
        this.actuator = actuator;
    }

    public void setPosition(LeversPosition position) {
        this.position = position;
    }

    public void setPositioningRestrictions(PositioningRestrictions positioningRestrictions) {
        this.positioningRestrictions = positioningRestrictions;
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

        log.info("Sensors: " + sensors);

        lastSensors = sensors;
        lastSensorsTime = System.currentTimeMillis();

    }

    private boolean isSensorReady() {

        return lastSensors != null && System.currentTimeMillis() - lastSensorsTime < MAX_SENSORS_AGE;

    }

    public void onTimer() {

        // detect stalls

        if (state == State.IDLE) {

            if (isSensorReady()) {

                CncCommand command = commandQueue.getNext();

                if (command != null) {

                    state = State.ERROR;

                    if (command.getCode() != CncCommandCode.LINE_TO) {
                        throw new IllegalStateException("LINE_TO command supported only");
                    }

                    CartesianPoint targetPen = new CartesianPoint(command.getX(), command.getY());

                    if (!positioningRestrictions.inBorders(targetPen)) {
                        throw new IllegalArgumentException("Target position " + targetPen + " is out of working area ");
                    }

                    LeverAnglesSensor currentSensors = lastSensors;

                    CartesianPoint currentPen = position.penByAdc(lastSensors);

                    LeverAnglesSensor targetSensor = position.adcByPen(targetPen);

                    LeverAnglesSensor diffToTarget = currentSensors.difference(targetSensor);


                    if (isDifferenceSmallEnough(diffToTarget)) {

                        // reached position

                    }




                    state = State.COMMAND_PROCESSING;


                }










            }





        }




    }


    private boolean isDifferenceSmallEnough(LeverAnglesSensor diff) {
        return Math.abs(diff.left) < POSITIONING_PRECISION && Math.abs(diff.right) < POSITIONING_PRECISION;
    }





}
