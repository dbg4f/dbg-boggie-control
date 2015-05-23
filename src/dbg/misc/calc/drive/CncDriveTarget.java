package dbg.misc.calc.drive;

import dbg.misc.calc.Util;

public class CncDriveTarget {

    public final CncSensors sensors;
    public final CncSensorsMask mask;
    public CncSensors sensorsAtStart;
    private long timeApplied;
    private long timeoutMsec;

    public CncDriveTarget(CncSensors sensors, CncSensorsMask mask) {
        this.sensors = sensors;
        this.mask = mask;
    }

    public void apply(long timeoutMsec, CncSensors sensors) {
        timeApplied = Util.now();
        this.timeoutMsec = timeoutMsec;
        sensorsAtStart = sensors;
    }

    public boolean isTimeOver() {
        return Util.now() - timeApplied > timeoutMsec;
    }

    public long getTimeApplied() {
        return timeApplied;
    }

    public long getTimeoutMsec() {
        return timeoutMsec;
    }

    public CncSensors getSensorsAtStart() {
        return sensorsAtStart;
    }

    @Override
    public String toString() {
        return "CncDriveTarget{" +
                "sensors=" + sensors +
                ", mask=" + mask +
                ", sensorsAtStart=" + sensorsAtStart +
                ", timeApplied=" + timeApplied +
                ", timeoutMsec=" + timeoutMsec +
                '}';
    }
}
