package dbg.misc.calc;

public class LeverAnglesSensor {
    public final double left;
    public final double right;
    public final long timeMarker;

    public LeverAnglesSensor(double left, double right) {
        this(left, right, 0);
    }

    public LeverAnglesSensor(double left, double right, long timeMarker) {
        this.left = left;
        this.right = right;
        this.timeMarker = timeMarker;
    }

    public LeverAnglesSensor move(double deltaLeft, double deltaRight) {
        return new LeverAnglesSensor(left + deltaLeft,  right + deltaRight);
    }

    public LeverAnglesSensor difference(LeverAnglesSensor sensor) {
        return new LeverAnglesSensor(left - sensor.left, right - sensor.right);
    }

    public boolean isSameSign(LeverAnglesSensor sensor) {
        return Math.signum(sensor.left) == Math.signum(left) &&
                Math.signum(sensor.right) == Math.signum(right);
    }

    public boolean isDifferentSign(LeverAnglesSensor sensor) {
        return Math.signum(sensor.left) != Math.signum(left) &&
                Math.signum(sensor.right) != Math.signum(right);
    }

    @Override
    public String toString() {
        return "LeverAnglesSensor{" +
                "left=" + left +
                ", right=" + right +
                ", timeMarker=" + timeMarker +
                '}';
    }
}
