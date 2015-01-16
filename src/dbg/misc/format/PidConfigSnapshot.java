package dbg.misc.format;

public class PidConfigSnapshot {

    double initialErr;
    double initialAcc;
    double initialChg;
    double initialRes;
    int ct;
    int t;


    public double getInitialErr() {
        return initialErr;
    }

    public void setInitialErr(double initialErr) {
        this.initialErr = initialErr;
    }

    public double getInitialAcc() {
        return initialAcc;
    }

    public void setInitialAcc(double initialAcc) {
        this.initialAcc = initialAcc;
    }

    public double getInitialChg() {
        return initialChg;
    }

    public void setInitialChg(double initialChg) {
        this.initialChg = initialChg;
    }

    public double getInitialRes() {
        return initialRes;
    }

    public void setInitialRes(double initialRes) {
        this.initialRes = initialRes;
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
        return "PidConfigSnapshot{" +
                "initialErr=" + initialErr +
                ", initialAcc=" + initialAcc +
                ", initialChg=" + initialChg +
                ", initialRes=" + initialRes +
                ", ct=" + ct +
                ", t=" + t +
                '}';
    }
}
