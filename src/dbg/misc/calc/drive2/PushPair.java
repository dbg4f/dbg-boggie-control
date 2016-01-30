package dbg.misc.calc.drive2;

import dbg.misc.calc.SensorPwmDirectionDependency;

/**
 * Created by dmitri on 30.01.16.
 */
public class PushPair {
    DrivePush left;
    DrivePush right;
    long time;

    public PushPair(DrivePush left, DrivePush right) {
        this.left = left;
        this.right = right;
        this.time = System.currentTimeMillis();
    }

    public void applyDepenency(SensorPwmDirectionDependency dependency) {
        if (dependency.isLeftStraight()) {
            left = left.invertPwm();
        }

        if (dependency.isRightStraight()) {
            right = right.invertPwm();
        }
    }

    public DrivePush getLeft() {
        return left;
    }

    public DrivePush getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "BlinkPair{" +
                "left=" + left +
                ", right=" + right +
                ", time=" + time +
                '}';
    }
}
