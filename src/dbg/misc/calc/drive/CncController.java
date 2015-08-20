package dbg.misc.calc.drive;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dbg.misc.ws.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.Queue;

public class CncController implements MessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(CncController.class);
    public static final int TIMEOUT_MSEC = 2000;

    private CncDriveTarget currentTarget;

    private CncSensors currentSensors = new CncSensors();
    private CncSensors perviousSensors = new CncSensors();
    private CncSensorsMask sensorsMask = CncSensorsMask.LEVERS_ONLY;
    private Type rowType = new TypeToken<CncSensors>(){}.getType();;
    private Gson gson = new Gson();
    private Queue<CncDriveTarget> targetPositions = new LinkedList<>();

    private DriveStateCode driveState = DriveStateCode.IDLE;

    public void moveTargetsQueue() {
        if (currentTarget == null && targetPositions.size() > 0) {
            setNewTarget(targetPositions.remove());
        }
    }

    private void setNewTarget(CncDriveTarget driveTarget) {
        currentTarget = driveTarget;
        currentTarget.apply(TIMEOUT_MSEC, currentSensors);
        log.info("New current target applied: " + currentTarget);
    }



    public void checkCurrentTargetReached() {

    }

    public void onClock(){


        // check enough time elapsed since previous regulation step
        // check regulation step timeout reached
        // check if sensor value is reached, latch this state to avoid jitter
        // calc difference between target and current sensors
        // check if channel is on/off (e.g. during pen lifting left/right disabled)
        // apply regulation 1 or 2 steps
        // update last action time

        moveTargetsQueue();

    }




    public void addTarget(CncDriveTarget target) {

        log.info("New Target to queue: " + target);

        targetPositions.add(target);

    }

    public void onSensors(CncSensors sensors) {

        perviousSensors = currentSensors;
        currentSensors = sensors;

        onClock();

        // TODO: log significant changes of sensors

    }


    @Override
    public void onMessage(String message) throws IOException {

        CncSensors sensors = gson.fromJson(message, rowType);

        onSensors(sensors);

    }
}
