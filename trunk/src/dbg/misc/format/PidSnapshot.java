package dbg.misc.format;

public class PidSnapshot {

    double farErr;
    double farAcc;
    double farChg;
    double farRes;
    int ct;
    int t;

    public double getFarErr() {
        return farErr;
    }

    public void setFarErr(double farErr) {
        this.farErr = farErr;
    }

    public double getFarAcc() {
        return farAcc;
    }

    public void setFarAcc(double farAcc) {
        this.farAcc = farAcc;
    }

    public double getFarChg() {
        return farChg;
    }

    public void setFarChg(double farChg) {
        this.farChg = farChg;
    }

    public double getFarRes() {
        return farRes;
    }

    public void setFarRes(double farRes) {
        this.farRes = farRes;
    }

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

    @Override
    public String toString() {
        return "PidSnapshot{" +
                "farErr=" + farErr +
                ", farAcc=" + farAcc +
                ", farChg=" + farChg +
                ", farRes=" + farRes +
                ", ct=" + ct +
                ", t=" + t +
                '}';
    }
}
