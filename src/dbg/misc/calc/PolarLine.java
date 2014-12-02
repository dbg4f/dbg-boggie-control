package dbg.misc.calc;

public class PolarLine {

    public final double r0;
    public final double gamma;

    public PolarLine(double r0, double gamma) {
        this.r0 = r0;
        this.gamma = gamma;
    }

    public double getR(double phi) {
        return r0 / Math.cos(phi - gamma);
    }

    @Override
    public String toString() {
        return "PolarLine{" +
                "r0=" + r0 +
                ", gamma=" + gamma +
                '}';
    }
}
