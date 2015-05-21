package dbg.misc.calc.drive;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dbg.misc.ws.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;

public class CncController implements MessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(CncController.class);

    private CncSensors targetSensors = new CncSensors();
    private CncSensors currentSensors = new CncSensors();
    private CncSensors perviousSensors = new CncSensors();
    private Type rowType = new TypeToken<CncSensors>(){}.getType();;
    private Gson gson = new Gson();

    private DriveStateCode driveState = DriveStateCode.IDLE;

    public void onClock(){

        // check enough time elapsed since previous regulation step
        // check regulation step timeout reached
        // check if sensor value is reached, latch this state to avoid jitter
        // calc difference between target and current sensors
        // check if channel is on/off (e.g. during pen lifting left/right disabled)
        // apply regulation 1 or 2 steps
        // update last action time

    }

    public void setTargetSensors(CncSensors sensors) {

        log.info("New Target: " + sensors + " " + targetSensors);

        targetSensors = sensors;

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
