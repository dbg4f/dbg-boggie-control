package dbg.misc.calc;

public class LinearDependency {

    public final double k;
    public final double b;

    public LinearDependency(double k, double b) {
        this.k = k;
        this.b = b;
    }

    public double getY(double x) {
        return k * x + b;
    }



}
