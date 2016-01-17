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

    public void setActuator(LeversActuator actuator) {
        this.actuator = actuator;
    }

    public void setPosition(LeversPosition position) {
        this.position = position;
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

        return lastSensors != null && System.currentTimeMillis() - lastSensorsTime < 1000;

    }

    public void onTimer() {
        // detect stalls

        if (state == State.IDLE) {



            if (isSensorReady()) {

                LeverAnglesSensor currentSensors = lastSensors;

                CartesianPoint currentPen = position.penByAdc(lastSensors);

                CncCommand command = commandQueue.getNext();

                if (command.getCode() != CncCommandCode.LINE_TO) {
                    throw new IllegalStateException("LINE_TO command supported only");
                }

                CartesianPoint targetPen = new CartesianPoint(command.getX(), command.getY());

                LeverAnglesSensor targetSensor = position.adcByPen(targetPen);









            }





        }




    }





}
