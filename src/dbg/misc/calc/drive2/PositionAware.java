package dbg.misc.calc.drive2;

import dbg.misc.calc.LeverAnglesSensor;
import dbg.misc.calc.drive.CncSensors;

/**
 * Created by dmitri on 10.01.16.
 */
public interface PositionAware {

    void onPositionReport(CncSensors sensors);
}
