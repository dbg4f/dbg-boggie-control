package dbg.misc.calc;

public class Triangle {

    public final double AB;
    public final double BC;
    public final double AC;

    public final double angleABC;
    public final double angleBCA;
    public final double angleBAC;


    public Triangle(double AB, double BC, double AC, double angleABC, double angleBCA, double angleBAC) {
        this.AB = AB;
        this.BC = BC;
        this.AC = AC;
        this.angleABC = angleABC;
        this.angleBCA = angleBCA;
        this.angleBAC = angleBAC;
    }

    public static Triangle SSS(double AB, double BC, double AC) {

        double angleABC = angleBySides(AB, BC, AC);
        double angleBCA = angleBySides(BC, AC, AB);
        double angleBAC = Math.PI - angleABC - angleBCA;

        return new Triangle(AB, BC, AC, angleABC, angleBCA, angleBAC);

    }

    public static Triangle SAS_ABC(double AB, double BC, double angleABC) {

        double AC = oppositeSide(AB, BC, angleABC);
        double angleBCA = angleBySides(BC, AC, AB);
        double angleBAC = Math.PI - angleABC - angleBCA;

        return new Triangle(AB, BC, AC, angleABC, angleBCA, angleBAC);

    }


    public static double angleBySides(double s1, double s2, double s3) {
        return Math.acos((s1*s1 + s2*s2 - s3*s3) / (2.0 * s1 * s2));
    }

    public static double oppositeSide(double s1, double s2, double angle) {
        return Math.sqrt(s1*s1 + s2*s2 - 2.0*s1*s2*Math.cos(angle));
    }


    @Override
    public String toString() {
        return "Triangle{" +
                "AB=" + AB +
                ", BC=" + BC +
                ", AC=" + AC +
                ", angleABC=" + angleABC +
                ", angleBCA=" + angleBCA +
                ", angleBAC=" + angleBAC +
                '}';
    }
}
