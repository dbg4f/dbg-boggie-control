package dbg.misc.calc.drive2;

/**
 * Created by dmitri on 30.01.16.
 */
public class PushPair {
    DrivePush left;
    DrivePush right;
    long time;

    public PushPair(DrivePush left, DrivePush right) {
        this.left = left;
        this.right = right;
        this.time = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "BlinkPair{" +
                "left=" + left +
                ", right=" + right +
                ", time=" + time +
                '}';
    }
}
