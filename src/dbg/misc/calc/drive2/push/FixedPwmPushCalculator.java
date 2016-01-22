package dbg.misc.calc.drive2.push;

import dbg.misc.calc.LeverAnglesSensor;
import dbg.misc.calc.drive2.DrivePush;

/**
 * @author bogdel on 22.01.16.
 */
public class FixedPwmPushCalculator implements PushCalculator {

    private final double pwm;

    private final int timeMaxMsec;

    public FixedPwmPushCalculator(double pwm, int timeMaxMsec) {
        this.pwm = pwm;
        this.timeMaxMsec = timeMaxMsec;
    }

    @Override
    public DrivePush calc(double difference) {
        return new DrivePush(timeMaxMsec, difference > 0 ? pwm : -pwm);
    }
}
