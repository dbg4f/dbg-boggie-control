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

    public boolean isBothDifferentSign(LeverAnglesSensor sensor) {
        return isLeftDifferentSign(sensor) &&
                isRightDifferentSign(sensor);
    }

    public boolean isBothSameSign(LeverAnglesSensor sensor) {
        return !isLeftDifferentSign(sensor) &&
                !isRightDifferentSign(sensor);
    }

    public boolean isRightDifferentSign(LeverAnglesSensor sensor) {
        return isDifferentSign(sensor.right, right);
    }

    public boolean isLeftDifferentSign(LeverAnglesSensor sensor) {
        return isDifferentSign(sensor.left, left);
    }

    private boolean isDifferentSign(double s1, double s2) {
        return Math.signum(s1) != Math.signum(s2);
    }

    public String formatShort() {
        return String.format("%.6f %.6f", left, right);
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
