package dbg.misc.calc;



public class CoupledTwainLeverPair {

    public final CartesianPoint leverLeftStart = new CartesianPoint(35, 5);
    public final CartesianPoint leverRightStart = new CartesianPoint(35+23, 5);
    public final Rectangle workingArea = new Rectangle(50, 70, 80, 110);
    public final CartesianPoint referencePoint = new CartesianPoint(50, 90);


    //public final double fullSensorAngle = (300/360)*(2.0*Math.PI); // ~300 deg
    //public final CncSensors verticalPositionReference = new CncSensors(0.5316, 0.3527, 0.0, 0);

    public final double leverLeftLowerLength = 50;
    public final double leverRightLowerLength = 50;
    public final double leverLeftUpperLength = 50;
    public final double leverRightUpperLength = 50;

    public final double penToRightUpperTopDistance = 33;
    public final double penToRightUpperBottomDistance = 75;

    public final static CalibrationCase[] ADC_CALIBRATION_DATA = {

            new CalibrationCase(0.618285, 0.192164, 60, 90  ), // ADC left, ADC right, x, y
            new CalibrationCase(0.545176, 0.162065, 40, 80  ),
            new CalibrationCase(0.490636, 0.074242, 20, 90  ),
            new CalibrationCase(0.532677, 0.070789, 30, 100 ),
            new CalibrationCase(0.653506, 0.170992, 70, 100 ),
            new CalibrationCase(0.582077, 0.193898, 50, 80  ),
            new CalibrationCase(0.685275, 0.181731, 80, 100 ),
            new CalibrationCase(0.650554, 0.250124, 70, 80  ),
            new CalibrationCase(0.635523, 0.103211, 60, 110 ),
            new CalibrationCase(0.533102, 0.002244, 20, 110 ),
    };



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
