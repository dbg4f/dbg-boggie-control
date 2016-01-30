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

    public double distanceTo(CartesianPoint point2) {
        double dx = point2.x - x;
        double dy = point2.y - y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public CartesianPoint round() {
        return new CartesianPoint(Math.round(x), Math.round(y));
    }

    @Override
    public String toString() {
        return "CartesianPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
