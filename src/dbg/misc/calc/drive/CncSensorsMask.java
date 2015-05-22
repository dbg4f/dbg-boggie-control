package dbg.misc.calc.drive;

public class CncSensorsMask {

    private int left = 1;
    private int right = 1;
    private int lift = 1;


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
