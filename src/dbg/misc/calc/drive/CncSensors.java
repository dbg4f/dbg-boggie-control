package dbg.misc.calc.drive;

public class CncSensors {

    public double left;
    public double right;
    public double lift;
    public int t;

    public CncSensors() {
    }

    public CncSensors(double left, double right, double lift, int t) {
        this.left = left;
        this.right = right;
        this.lift = lift;
        this.t = t;
    }

    public CncSensors distanceTo(CncSensors sensors) {
        return new CncSensors(
                    sensors.left - left,
                    sensors.right - right,
                    sensors.lift - lift,
                    t);
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

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
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

}
