package dbg.misc.calc;

public class LinearDependency {

    public final double k;
    public final double b;

    public LinearDependency(double k, double b) {
        this.k = k;
        this.b = b;
    }

    public LinearDependency (CartesianPoint p1, CartesianPoint p2) {

        if (p1.y == p2.y && p1.x== p2.x) {
            throw new IllegalArgumentException("Cannot calc linear dependency based on one and the same point: " + p1 + " " + p2);
        }

        k = (p2.y - p1.y) / (p2.x - p1.x);
        b = p1.y - k * p1.x;

    }

    public double getY(double x) {
        return k * x + b;
    }


    @Override
    public String toString() {
        return "LinearDependency{" +
                "k=" + k +
                ", b=" + b +
                '}';
    }
}
