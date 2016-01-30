package dbg.misc.calc;

/**
 * Created by dmitri on 30.01.16.
 */
public class SensorPwmDirectionDependency {
    private final boolean leftStraight;
    private final boolean rightStraight;

    public static final SensorPwmDirectionDependency STRAIGHT = new SensorPwmDirectionDependency(true, true);

    public SensorPwmDirectionDependency(boolean leftStraight, boolean rightStraight) {
        this.leftStraight = leftStraight;
        this.rightStraight = rightStraight;
    }

    public boolean isLeftStraight() {
        return leftStraight;
    }

    public boolean isRightStraight() {
        return rightStraight;
    }

    public double applyLeft(double pwm) {
        return leftStraight ? pwm : -pwm;
    }

    public double applyRight(double pwm) {
        return rightStraight ? pwm : -pwm;
    }

    @Override
    public String toString() {
        return "SensorPwmDirectionDependency{" +
                "leftStraight=" + leftStraight +
                ", rightStraight=" + rightStraight +
                '}';
    }
}
