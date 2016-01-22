package dbg.misc.calc.drive2.push;

import dbg.misc.calc.LeverAnglesSensor;
import dbg.misc.calc.drive2.DrivePush;

/**
 * @author bogdel on 22.01.16.
 */
public interface PushCalculator {

    DrivePush calc(double difference);

}
