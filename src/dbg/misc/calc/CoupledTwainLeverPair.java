package dbg.misc.calc;


import dbg.misc.calc.drive.CncSensors;

public class CoupledTwainLeverPair {

    public final CartesianPoint leverLeftStart = new CartesianPoint(100/2-12, 0);
    public final CartesianPoint leverRightStart = new CartesianPoint(100/2+12, 0);
    public final Rectangle workingArea = new Rectangle(0, 75, 100, 125);

    public final double fullSensorAngle = (300/360)*(2.0*Math.PI); // ~300 deg

    public final CncSensors verticalPositionReference = new CncSensors(0.5316, 0.3527, 0.0, 0);

    public final double leverLeftLowerLength = 50;
    public final double leverRightLowerLength = 50;
    public final double leverLeftUpperLength = 50;
    public final double leverRightUpperLength = 50;

    public final double penToRightUpperTopDistance = 33;
    public final double penToRightUpperBottomDistance = 75;


    @Override
    public String toString() {
        return "CoupledTwainLeverPair{" +
                "leverLeftStart=" + leverLeftStart +
                ", leverRightStart=" + leverRightStart +
                ", workingArea=" + workingArea +
                ", leverLeftLowerLength=" + leverLeftLowerLength +
                ", leverRightLowerLength=" + leverRightLowerLength +
                ", leverLeftUpperLength=" + leverLeftUpperLength +
                ", leverRightUpperLength=" + leverRightUpperLength +
                ", penToRightUpperTopDistance=" + penToRightUpperTopDistance +
                ", penToRightUpperBottomDistance=" + penToRightUpperBottomDistance +
                '}';
    }
}
