package dbg.misc.calc.drive2;

import dbg.misc.calc.LeverAnglesSensor;

/**
 * Created by dmitri on 10.01.16.
 */
public class LeversController implements PositionAware {


    private LeversActuator actuator;

    public void setActuator(LeversActuator actuator) {
        this.actuator = actuator;
    }

    public void onPositionReport(LeverAnglesSensor sensor) {

        // archive each report
        // keep previous positions to detect speed
        // detect when pen is out of working area
        // detect when command not executed or not sanctioned move occurred

    }

    public void onTimer() {
        // detect stalls
    }





}
