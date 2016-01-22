package dbg.misc.calc.drive2;

/**
 * @author bogdel on 22.01.16.
 */
public class DrivePush {

    public static final int MAX_PUSH_MSEC = 100;
    private final int lengthMsec;

    private final double pwm;


    public DrivePush(int lengthMsec, double pwm) {

        if (lengthMsec < 0) {
            lengthMsec = 0;
        }

        if (lengthMsec > MAX_PUSH_MSEC) {
            lengthMsec = 100;
        }

        this.lengthMsec = lengthMsec;

        if (pwm < -1.0) {
            pwm = -1.0;
        }

        if (pwm > 1.0) {
            pwm = 1.0;
        }

        this.pwm = pwm;
    }

    public int getLengthMsec() {
        return lengthMsec;
    }

    public double getPwm() {
        return pwm;
    }

    @Override
    public String toString() {
        return "DrivePush{" +
                "lengthMsec=" + lengthMsec +
                ", pwm=" + pwm +
                '}';
    }
}
