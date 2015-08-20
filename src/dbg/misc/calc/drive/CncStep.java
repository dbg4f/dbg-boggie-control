package dbg.misc.calc.drive;


public class CncStep {

    private final String cmd;
    private final double pwm;
    private final int duration;

    public CncStep(DriveCode cmd, double pwm, int duration) {
        this.cmd = cmd.getCmdCode();
        this.pwm = pwm;
        this.duration = duration;
    }

    public String getCmd() {
        return cmd;
    }

    public double getPwm() {
        return pwm;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "CncStep{" +
                "cmd='" + cmd + '\'' +
                ", pwm=" + pwm +
                ", duration=" + duration +
                '}';
    }


}
