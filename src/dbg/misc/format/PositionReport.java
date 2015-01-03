package dbg.misc.format;

public class PositionReport {

    public int ct;
    public int t;
    public double position;

    public int getCt() {
        return ct;
    }

    public void setCt(int ct) {
        this.ct = ct;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "PositionReport{" +
                "ct=" + ct +
                ", t=" + t +
                ", position=" + position +
                '}';
    }
}
