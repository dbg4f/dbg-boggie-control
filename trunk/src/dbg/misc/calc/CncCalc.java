package dbg.misc.calc;

import com.google.gson.Gson;

/**
 */
public class CncCalc {


    /*

    *
  Y *                    ************ C
    *      B         ****         *
    *            ***           *
    *       ****           *
    *      *          *
    *     *         *
    *    *      *
    *   *     *
    *  *   *
    * *
    * ***************************************************
   A                                  X



Lnear = 86
Lfar  = 165

0.2145 0.14615 204 52
0.3120 0.2990 157 23
0.4355 0.3687 129 25
0.3715 0.2768 144 80
0.5245 0.3687 100 74
0.2275 0.0866 194 116
0.2805 0.1122 173 136
0.3675 0.1950 144 130
0.4880 0.3062 104 119
0.5255 0.3290 090 116
     */

    public static CalibrationCase[] CASES = new CalibrationCase[] {

            new CalibrationCase(0.2145, 0.14615,204, 52 ),
            new CalibrationCase(0.3120, 0.2990, 157, 23 ),
            new CalibrationCase(0.4355, 0.3687, 129, 25 ),
            new CalibrationCase(0.3715, 0.2768, 144, 80 ),
            new CalibrationCase(0.5245, 0.3687, 100, 74 ),
            new CalibrationCase(0.2275, 0.0866, 194, 116),
            new CalibrationCase(0.2805, 0.1122, 173, 136),
            new CalibrationCase(0.3675, 0.1950, 144, 130),
            new CalibrationCase(0.4880, 0.3062, 104, 119),
            new CalibrationCase(0.5255, 0.3290,  90, 116),

    };


    public static double NEAR = 86.0;
    public static double FAR = 165.0;

    // result
    public static LinearDependency ANGLE_XAB_BY_SENSOR_NEAR = new LinearDependency(4.126512001014917, 0.21846109809298997);
    public static LinearDependency ANGLE_ABC_BY_SENSOR_FAR  = new LinearDependency(-4.609018187697576, 2.5929563564270324);

    public static void main(String[] args) {

        double[] as = new double[CASES.length];
        double[] ac = new double[CASES.length];
        double[] bs = new double[CASES.length];
        double[] bc = new double[CASES.length];

        int i=0;

        for (CalibrationCase aCase : CASES) {

            aCase.a = calcAN(aCase.x, aCase.y);
            aCase.b = calcBN(aCase.x, aCase.y);

            System.out.println("" + new Gson().toJson(aCase) + " x=" + X(aCase.far, aCase.near) + " y=" + Y(aCase.far, aCase.near));

            as[i] = aCase.near;
            ac[i] = aCase.a;

            bs[i] = aCase.far;
            bc[i] = aCase.b;

            i++;
        }

        LinearRegressionResult regrA = LinearRegression.calc(as, ac);
        LinearRegressionResult regrB = LinearRegression.calc(bs, bc);

        System.out.println("regrA = " + regrA);
        System.out.println("regrB = " + regrB);

        System.out.println("A(0) = "   + regrA.y(0));
        System.out.println("A(0.5) = " + regrA.y(0.5));
        System.out.println("B(0) = "   + regrB.y(0));
        System.out.println("B(0.5) = " + regrB.y(0.5));


    }

    static double sideAC(double Sf) {
        double angleABC = ANGLE_ABC_BY_SENSOR_FAR.getY(Sf);
        return Math.sqrt(NEAR * NEAR + FAR * FAR - 2.0 * NEAR * FAR * Math.cos(angleABC));
    }

    static double angleXAC(double Sf, double Sn) {
        //double angleABC = ANGLE_ABC_BY_SENSOR_FAR.getY(Sf);
        double angleXAB = ANGLE_XAB_BY_SENSOR_NEAR.getY(Sn);
        double R = sideAC(Sf);
        double angleBAC = angleBySides(FAR, R, NEAR);//Math.asin((FAR * Math.sin(angleABC)) / R);
        return angleXAB - angleBAC;
    }

    static double X(double Sf, double Sn) {
        return sideAC(Sf) * Math.cos(angleXAC(Sf, Sn));
    }

    static double Y(double Sf, double Sn) {
        return sideAC(Sf) * Math.sin(angleXAC(Sf, Sn));
    }



    static double calcR(double x, double y) {
        return Math.sqrt(x*x + y*y);
    }

    static double angle(double x, double y) {
        return Math.atan(y/x);
    }

    static double angleBySides(double a, double b, double c) {
        return Math.acos((b*b + c*c - a*a) / (2.0 * b * c));
    }

    static double oppositeSide(double b, double c, double alpha){
        return Math.sqrt(b*b + c*c - 2.0*b*c*Math.cos(alpha));
    }

    static double calcRByBeta(double beta) {
        return oppositeSide(NEAR, FAR, beta);
    }

    static double calcAlpha(double alpha, double beta) {
        double r = calcRByBeta(beta);
        double angleCAB = angleBySides(NEAR, r, FAR);
        double angleCAX = alpha - angleCAB;
        return angleCAX;
    }

    static double calcAN(double x, double y) {
        double r = calcR(x, y);
        return angle(x, y) + angleBySides(FAR, NEAR, r);
    }

    static double calcBN(double x, double y) {
        double r = calcR(x, y);
        return angleBySides(r, NEAR, FAR);
    }

    static double getY(double x, double beta1, double beta0) {
        return x * beta1 + beta0;
    }










}
