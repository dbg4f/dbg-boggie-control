package dbg.misc.calc;

/**
 * @author bogdel
 */
public class PosCalc {

  public static final Pair LEVER_LENGTHS = new Pair(87.0, 160.0);

  public static final Pair NEAR_CALIBRATION_POINT1 = new Pair(degreesToAngle(90.0), 0.3165); // degrees - sensor
  public static final Pair NEAR_CALIBRATION_POINT2 = new Pair(degreesToAngle(33.0), 0.179);

  public static final double NEAR_CALIBRATION_ANGLE = degreesToAngle(33.0);

  public static final Pair FAR_CALIBRATION_POINT1 = new Pair(degreesToAngle(0.0), 0.1008);
  public static final Pair FAR_CALIBRATION_POINT2 = new Pair(degreesToAngle(46.0), 0.24435);

  static class Pair {
    final double near;
    final double far;

    Pair(double near, double far) {
      this.near = near;
      this.far = far;
    }

    @Override
    public String toString() {
      return "Pair{" +
             "near=" + near +
             ", far=" + far +
             '}';
    }
  }

  private static Pair calcCaretPos(Pair sensors) {

    double angleNear = interpolateFindNear(sensors.near, NEAR_CALIBRATION_POINT1, NEAR_CALIBRATION_POINT2);

    double angleFar = interpolateFindNear(sensors.far, FAR_CALIBRATION_POINT1, FAR_CALIBRATION_POINT2);

    angleFar = angleNear - NEAR_CALIBRATION_ANGLE + angleFar;

    Pair pointNearLever = new Pair(LEVER_LENGTHS.near * Math.cos(angleNear), LEVER_LENGTHS.near * Math.sin(angleNear));
    Pair pointFarLeverFromStart = new Pair(LEVER_LENGTHS.far * Math.cos(angleFar), LEVER_LENGTHS.far * Math.sin(angleFar));

    return new Pair(pointNearLever.near + pointFarLeverFromStart.near, pointNearLever.far + pointFarLeverFromStart.far);
  }

  private static Pair calcPosTarget(Pair targetCaretPos) {

    // TODO: check for max/min possible values


    double a = LEVER_LENGTHS.far;
    double b = LEVER_LENGTHS.near;

    double polarR = Math.sqrt(targetCaretPos.near * targetCaretPos.near + targetCaretPos.far * targetCaretPos.far);
    double polarA = StrictMath.atan(targetCaretPos.far / targetCaretPos.near);

    double c = polarR;

    double alpha = Math.acos((b * b + c * c - a * a) / (2.0 * b * c));

    double beta = Math.acos((a * a + c * c - b * b) / (2.0 * a * c));


    double targetAngleNear = alpha + polarA;
    double targetAngleFar = -beta;

    double targetPosNear = interpolateFindFar(targetAngleNear, NEAR_CALIBRATION_POINT1, NEAR_CALIBRATION_POINT2);
    double targetPosFar = interpolateFindFar(targetAngleFar, FAR_CALIBRATION_POINT1, FAR_CALIBRATION_POINT2);

    return new Pair(targetPosNear, targetPosFar);

  }


  private static double interpolateFindNear(double far, Pair calibrationPair1, Pair calibrationPair2) {
      // y = k*x + c
      // k = tg(alpha)
      // tg(a) = dy/dx
      // c = y - k*x
      // x = (y - c) / k

    double k = (calibrationPair2.far - calibrationPair1.far) / (calibrationPair2.near - calibrationPair1.near);
    double c = calibrationPair1.far - k * calibrationPair1.near;

    return (far - c) / k;

  }

  private static double interpolateFindFar(double near, Pair calibrationPair1, Pair calibrationPair2) {
      // y = k*x + c
      // k = tg(alpha)
      // tg(a) = dy/dx
      // c = y - k*x
      // x = (y - c) / k

    double k = (calibrationPair2.far - calibrationPair1.far) / (calibrationPair2.near - calibrationPair1.near);
    double c = calibrationPair1.far - k * calibrationPair1.near;

    return k * near + c;

  }

  private static double degreesToAngle(double degrees) {
    return (2.0*Math.PI / 360.0) * degrees;
  }

  private static double angleToDegrees(double angle) {
    return (360.0 / (2.0*Math.PI)) * angle;
  }


  public static void main(String[] args) {

      // 0.212824,0.152784


      Pair p = new Pair(212.6, 109.5);

      System.out.println("p = " + p);

     p = calcPosTarget(p);

    System.out.println("p = " + p);

    //p = calcCaretPos(new Pair(0.212824,0.152784));
    p = calcCaretPos(p);

    System.out.println("p = " + p);



  }



}
