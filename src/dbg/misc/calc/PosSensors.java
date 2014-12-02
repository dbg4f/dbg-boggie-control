package dbg.misc.calc;

public class PosSensors {

    public final double near;
    public final double far;

    public PosSensors(double near, double far) {
        this.near = near;
        this.far = far;
    }

    @Override
    public String toString() {
        return "PosSensors{" +
                "near=" + near +
                ", far=" + far +
                '}';
    }
}
