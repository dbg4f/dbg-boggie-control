package dbg.misc.calc;

public class LeversPosition {


    private final CoupledTwainLeverPair pair;

    private LinearDependency angleByAdcLeft;
    private LinearDependency angleByAdcRight;

    CartesianPoint pen;
    CartesianPoint rightLowerTop;
    CartesianPoint bothTop;
    CartesianPoint leftLowerTop;

    public LeversPosition(CoupledTwainLeverPair pair) {
        this.pair = pair;
        calcAdcAngleDependency();
    }

    public void calcAdcAngleDependency() {

        int calibrationLen = CoupledTwainLeverPair.ADC_CALIBRATION_DATA.length;

        double leftAdc[] = new double[calibrationLen];
        double rightAdc[] = new double[calibrationLen];
        double leftAngle[] = new double[calibrationLen];
        double rightAngle[] = new double[calibrationLen];

        int i=0;

        for (CalibrationCase c : CoupledTwainLeverPair.ADC_CALIBRATION_DATA) {

            double x = c.x;
            double y = c.y;


            LeverAngles angles = calcAngles(new CartesianPoint(x, y));
            double al = angles.left;
            double ar = angles.right;

            leftAdc[i] = c.near;
            rightAdc[i] = c.far;

            leftAngle[i] = angles.left;
            rightAngle[i] = angles.right;

            i++;

            System.out.println(String.format("%s,%s,%s,%s,%s,%s",
                    String.valueOf(c.near),
                    String.valueOf(c.far),
                    String.valueOf(al),
                    String.valueOf(ar),
                    String.valueOf(x),
                    String.valueOf(y)
            ));


        }


        LinearRegressionResult resultLeft = LinearRegression.calc(leftAdc, leftAngle);
        LinearRegressionResult resultRight = LinearRegression.calc(rightAdc, rightAngle);

        angleByAdcLeft = new LinearDependency(resultLeft.beta1, resultLeft.beta0);
        angleByAdcRight = new LinearDependency(resultRight.beta1, resultRight.beta0);

        System.out.println("resultLeft = " + resultLeft);
        System.out.println("resultRight = " + resultRight);

        System.out.println();
    }


    public LeverAngles getAnglesByAdc(LeverAnglesSensor sensor) {
        return new LeverAngles(
            angleByAdcLeft.getY(sensor.left),
            angleByAdcRight.getY(sensor.right)
        );
    }

    public LeverAnglesSensor getAdcByAngles(LeverAngles angles) {
        return new LeverAnglesSensor(
            angleByAdcLeft.getX(angles.left),
            angleByAdcRight.getX(angles.right)
        );
    }

    public CartesianPoint penByAdc(LeverAnglesSensor sensor) {
        return calcPen(getAnglesByAdc(sensor));
    }

    public LeverAnglesSensor adcByPen(CartesianPoint pen) {
        return getAdcByAngles(calcAngles(pen));
    }

    public LeverAngles calcAngles(CartesianPoint pen) {

        this.pen = pen;

        rightLowerTop = new CircleIntersection(
                new Circle(pair.penToRightUpperBottomDistance, pen),
                new Circle(pair.leverRightLowerLength, pair.leverRightStart)).getRight();


        bothTop = new CircleIntersection(
                new Circle(pair.penToRightUpperTopDistance, pen),
                new Circle(pair.leverRightUpperLength, rightLowerTop)).getLeft();

        leftLowerTop = new CircleIntersection(
                new Circle(pair.leverLeftUpperLength, bothTop),
                new Circle(pair.leverLeftLowerLength, pair.leverLeftStart)).getLeft();

        Section leftLever = new Section(pair.leverLeftStart, leftLowerTop);
        Section rightLever = new Section(pair.leverRightStart, rightLowerTop);
        return new LeverAngles(leftLever.angleOX(), rightLever.angleOX());
    }

    public CartesianPoint leverEnd(double angle, CartesianPoint startPos, double leverLength) {
        return new CartesianPoint(
                startPos.x + Math.cos(angle) * leverLength,
                startPos.y + Math.sin(angle) * leverLength);
    }

    public CartesianPoint calcPen(LeverAngles leverAngles) {
        leftLowerTop = leverEnd(leverAngles.left, pair.leverLeftStart, pair.leverLeftLowerLength);
        rightLowerTop = leverEnd(leverAngles.right, pair.leverRightStart, pair.leverRightLowerLength);

        bothTop = new CircleIntersection(
                new Circle(pair.leverLeftUpperLength, leftLowerTop),
                new Circle(pair.leverRightUpperLength, rightLowerTop)
        ).getUpper();

        pen = new CircleIntersection(
                new Circle(pair.penToRightUpperBottomDistance, rightLowerTop),
                new Circle(pair.penToRightUpperTopDistance, bothTop)
        ).getUpper();


        return pen;
    }

    @Override
    public String toString() {
        return "LeversPosition{" +
                "pair=" + pair +
                ", rightLowerTop=" + rightLowerTop +
                ", bothTop=" + bothTop +
                '}';
    }


}
