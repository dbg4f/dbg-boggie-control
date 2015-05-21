package dbg.misc.calc.drive;

public class CncSensors {

    public double left;
    public double right;
    public double lift;
    public double t;

    public CncSensors() {
    }

    public CncSensors(double left, double right, double lift, double t) {
        this.left = left;
        this.right = right;
        this.lift = lift;
        this.t = t;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public double getLift() {
        return lift;
    }

    public void setLift(double lift) {
        this.lift = lift;
    }

    public double getT() {
        return t;
    }

    @Override
    public String toString() {
        return "CncSensors{" +
                "left=" + left +
                ", right=" + right +
                ", lift=" + lift +
                ", t=" + t +
                '}';
    }

    public void setT(double t) {
        this.t = t;
    }
}
