package dbg.misc.calc;

public class TwainLever {

    public final double nearLen;
    public final double farLen;
    public final LinearDependency nearSensorLaw;
    public final LinearDependency farSensorLaw;


    public TwainLever(double nearLen, double farLen, LinearDependency nearSensorLaw, LinearDependency farSensorLaw) {
        this.nearLen = nearLen;
        this.farLen = farLen;
        this.nearSensorLaw = nearSensorLaw;
        this.farSensorLaw = farSensorLaw;
    }

    public PolarPoint position(PosSensors posSensors) {

        double angleOX_Near = nearSensorLaw.getY(posSensors.near);
        double angleBtwLevers = farSensorLaw.getY(posSensors.far);

        Triangle leversTriangle = Triangle.SAS_ABC(nearLen, farLen, angleBtwLevers);

        return new PolarPoint(leversTriangle.AC, angleOX_Near - leversTriangle.angleBAC);

    }

    public PosSensors expectSensors(PolarPoint position) {

        Triangle leversTriangle = Triangle.SSS(nearLen, farLen, position.r);

        double angleOX_Near = position.phi + leversTriangle.angleBAC;

        return new PosSensors(nearSensorLaw.getX(angleOX_Near), farSensorLaw.getX(leversTriangle.angleABC));

    }


}
