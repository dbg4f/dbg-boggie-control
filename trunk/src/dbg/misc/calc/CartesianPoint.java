package dbg.misc.calc;

public class CartesianPoint {

    public final double x;
    public final double y;

    public CartesianPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public PolarPoint toPolarPoint() {
        return new PolarPoint(Math.sqrt(x*x + y*y), Math.atan2(y, x));
    }

    @Override
    public String toString() {
        return "CartesianPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
