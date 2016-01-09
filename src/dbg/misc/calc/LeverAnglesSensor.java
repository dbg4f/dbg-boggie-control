package dbg.misc.calc;

public class LeverAnglesSensor {
    public final double left;
    public final double right;

    LeverAnglesSensor(double left, double right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "LeverAnglesSensor{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
