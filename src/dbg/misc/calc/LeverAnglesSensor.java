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

    public LeverAnglesSensor difference(LeverAnglesSensor sensor) {
        return new LeverAnglesSensor(left - sensor.left, right - sensor.right);
    }

    @Override
    public String toString() {
        return "LeverAnglesSensor{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
