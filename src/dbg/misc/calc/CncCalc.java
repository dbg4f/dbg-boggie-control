package dbg.misc.calc;

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


    static double[] x1 = {
            0.618285,
            0.545176,
            0.490636,
            0.532677,
            0.653506,
            0.582077,
            0.685275,
            0.650554,
            0.635523,
            0.533102,
    };

    static double[] y1 = {
            2.0454798959717033,
            2.296122642612522,
            2.579268806610414,
            2.4093680972398475,
            1.9183787754196733,
            2.151099396369019,
            1.795327038383244,
            1.8942619623916783,
            2.0047351115114713,
            2.4519059510764523,
    };

    static double[] x2 = {
            0.192164,
            0.162065,
            0.074242,
            0.070789,
            0.170992,
            0.193898,
            0.181731,
            0.250124,
            0.103211,
            0.002244,
    };

    static double[] y2 = {
            0.48019848047689356,
            0.6103985090009164,
            1.0582197800982835,
            1.0274180835919953,
            0.5574424603854268,
            0.4531161157609204,
            0.4861845051482322,
            0.19689443831425518,
            0.832994957236855,
            1.3386365957026711,
    };

    public static void main(String[] args) {

        LinearRegressionResult result1 = LinearRegression.calc(y1, x1);

        System.out.println("result1 = " + result1);

        LinearRegressionResult result2 = LinearRegression.calc(y2, x2);

        System.out.println("result2 = " + result2);

    }

    public static void main2(String[] args) {

        double[] as = new double[CASES.length];
        double[] ac = new double[CASES.length];
        double[] bs = new double[CASES.length];
        double[] bc = new double[CASES.length];

        int i=0;

        for (CalibrationCase aCase : CASES) {

            aCase.a = calcAN(aCase.x, aCase.y);
            aCase.b = calcBN(aCase.x, aCase.y);

            //System.out.println("" + new Gson().toJson(aCase) + " x=" + X(aCase.far, aCase.near) + " y=" + Y(aCase.far, aCase.near));

            analyze(aCase);

            as[i] = aCase.near;
            ac[i] = aCase.a;

            bs[i] = aCase.far;
            bc[i] = aCase.b;

            i++;
        }

        LinearRegressionResult regrA = LinearRegression.calc(as, ac);
        LinearRegressionResult regrB = LinearRegression.calc(bs, bc);

        ANGLE_XAB_BY_SENSOR_NEAR = new LinearDependency(regrA.beta1, regrA.beta0);
        ANGLE_ABC_BY_SENSOR_FAR  = new LinearDependency(regrB.beta1, regrB.beta0);

        System.out.println("regrA = " + regrA);
        System.out.println("regrB = " + regrB);

        System.out.println("A(0) = "   + regrA.y(0));
        System.out.println("A(0.5) = " + regrA.y(0.5));
        System.out.println("B(0) = "   + regrB.y(0));
        System.out.println("B(0.5) = " + regrB.y(0.5));



      TwainLever lever = new TwainLever(NEAR, FAR, ANGLE_XAB_BY_SENSOR_NEAR, ANGLE_ABC_BY_SENSOR_FAR);

      for (CalibrationCase calibrationCase : CASES) {

        CartesianPoint point = lever.position(new PosSensors(calibrationCase.near, calibrationCase.far)).toCartesianPoint();
        PosSensors expectedPos = lever.expectSensors(new CartesianPoint(calibrationCase.x, calibrationCase.y).toPolarPoint());

        System.out.println(" case    " + calibrationCase);
        System.out.println(" point   " + point);
        System.out.println(" sensors " + expectedPos);
        System.out.println("-------------------------------------------------------");


      }

      System.out.print("x=[");
      for (CalibrationCase calibrationCase : CASES) {
        System.out.print(calibrationCase.near + ", ");
      }
      System.out.println("];");


      System.out.print("y=[");
      for (CalibrationCase calibrationCase : CASES) {
        System.out.print(calibrationCase.a + ", ");
      }
      System.out.println("];");

      System.out.print("x1=[");
      for (CalibrationCase calibrationCase : CASES) {
        System.out.print(calibrationCase.far + ", ");
      }
      System.out.println("];");


      System.out.print("y1=[");
      for (CalibrationCase calibrationCase : CASES) {
        System.out.print(calibrationCase.b + ", ");
      }
      System.out.println("];");


      Section section = new Section(new CartesianPoint(204, 52), new CartesianPoint(90, 116));


      PosSensors previousSensors = null;
      for (CartesianPoint subSectionPoint : section.split(10)) {
        //System.out.println("cartesianPoint = " + subSectionPoint);
        PosSensors posSensors = lever.expectSensors(subSectionPoint.toPolarPoint());

        if (previousSensors != null) {

            double dFar = posSensors.far - previousSensors.far;
            double dNear = posSensors.near - previousSensors.far;

            //double dRatio =

        }


          previousSensors = posSensors;
        System.out.println("posSensors = " + posSensors);
      }


      /*
        for (double Sn=0; Sn <1.01; Sn += 0.1) {
            for (double Sf = 0; Sf <1.01; Sf += 0.1) {
                System.out.print("," + angleXAC(Sf, Sn));
            }
            System.out.println();
        }
        */


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


    static void printDiff(String meaning, double ref, double calc) {
        double delta = ref - calc;
        System.out.println(String.format("%s: %f - %f = %f (%f %%)  ", meaning, ref, calc, delta, (delta/ref) * 100.0));
    }

    static void analyze(CalibrationCase c) {
        double x = X(c.far, c.near);
        double y = Y(c.far, c.near);

        double Rref = calcR(c.x, c.y);

        double r = sideAC(c.far);

        printDiff("X", c.x, x);
        printDiff("Y", c.y, y);
        printDiff("R", Rref, r);

        System.out.println();


    }








}
