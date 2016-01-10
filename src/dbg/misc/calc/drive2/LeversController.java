package dbg.misc.calc.drive2;

import dbg.misc.calc.drive.CncSensors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dmitri on 10.01.16.
 */
public class LeversController implements PositionAware {

    private static Logger log = LoggerFactory.getLogger(LeversController.class);

    private LeversActuator actuator;

    private CommandQueue commandQueue;

    public void setActuator(LeversActuator actuator) {
        this.actuator = actuator;
    }

    public void setCommandQueue(CommandQueue commandQueue) {
        this.commandQueue = commandQueue;
    }

    public void onPositionReport(CncSensors sensors) {

        // archive each report
        // keep previous positions to detect speed
        // detect when pen is out of working area
        // detect when command not executed or not sanctioned move occurred

        log.info("Sensors: " + sensors);

    }

    public void onTimer() {
        // detect stalls




    }





}
