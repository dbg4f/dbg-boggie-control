package dbg.misc.calc;

public class PolarPoint {

    public final double r;
    public final double phi;

    public PolarPoint(double r, double phi) {
        this.r = r;
        this.phi = phi;
    }

    public CartesianPoint toCartesianPoint() {
        return new CartesianPoint(r * Math.cos(phi), r * Math.sin(phi));
    }

    @Override
    public String toString() {
        return "PolarPoint{" +
                "r=" + r +
                ", phi=" + phi +
                '}';
    }
}
