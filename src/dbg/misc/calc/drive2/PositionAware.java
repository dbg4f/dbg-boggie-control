package dbg.misc.calc.drive2;

import dbg.misc.calc.LeverAnglesSensor;

/**
 * Created by dmitri on 10.01.16.
 */
public interface PositionAware {

    void onPositionReport(LeverAnglesSensor sensor);
}
