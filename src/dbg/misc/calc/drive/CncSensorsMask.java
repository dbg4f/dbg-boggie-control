package dbg.misc.calc.drive;

public class CncSensorsMask {

    private int left = 1;
    private int right = 1;
    private int lift = 1;


    public static CncSensorsMask LIFT_ONLY = new CncSensorsMask(0, 0, 1);
    public static CncSensorsMask LEVERS_ONLY = new CncSensorsMask(1, 1, 0);

    public CncSensorsMask(int left, int right, int lift) {
        this.left = left;
        this.right = right;
        this.lift = lift;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getLift() {
        return lift;
    }

    public void setLift(int lift) {
        this.lift = lift;
    }

    @Override
    public String toString() {
        return "CncSensorsMask{" +
                "left=" + left +
                ", right=" + right +
                ", lift=" + lift +
                '}';
    }
}
