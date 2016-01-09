package dbg.misc.calc;

import dbg.misc.format.JsonUtils;
import dbg.misc.format.PositionReport;
import dbg.misc.format.TimedMarker;
import dbg.misc.format.TimedMarkerUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static dbg.misc.calc.CncCalc.*;

public class UtilsLauncher {

    static final double[][] CALIBRATION = new double[][] {

                    {0.618285, 0.192164, 60, 90    } ,
                    {0.545176, 0.162065, 40, 80    } ,
                    {0.490636, 0.074242, 20, 90    } ,
                    {0.532677, 0.070789, 30, 100   } ,
                    {0.653506, 0.170992, 70, 100   } ,
                    {0.582077, 0.193898, 50, 80    } ,
                    {0.685275, 0.181731, 80, 100   } ,
                    {0.650554, 0.250124, 70, 80    } ,
                    {0.635523, 0.103211, 60, 110   } ,
                    {0.533102, 0.002244, 20, 110   } ,


    };
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

    public static void main1(String[] args) throws IOException {


        PositionReport[] reports = TimedMarkerUtils.readArrayPositionReports("docs/logs", "simple-movement3.json");

        double position = reports[0].getPosition();
        int t = reports[0].getT();

        for (PositionReport report : reports) {
            report.setT(report.getT() - t);
            report.setPosition(report.getPosition() - position);
            System.out.println(String.format("%f %d", report.getPosition(), report.getT()));
        }


        String maximaPositions = new TimedMarkerUtils<PositionReport>().toMaximaArrays(Arrays.asList(reports), TimedMarkerUtils.createPositionReportExtractorXY());

        System.out.println("maximaPositions = \n" + maximaPositions);



        TimedMarker[] timedMarkers = TimedMarkerUtils.readArrayTimedMarkers("docs/logs", "both4.json");

        List<PosSensors> dependency = TimedMarkerUtils.merge(Arrays.asList(timedMarkers));

        String jsDependency = new JsonUtils<PosSensors>().toJsonArrayText(dependency);

        System.out.println("jsDependency = " + jsDependency);

        FileWriter wr = new FileWriter("webapp/dependency.json");
        wr.write(jsDependency);
        wr.close();

        System.out.println("TimedMarkerUtils.toMaximaArrays() = \n" + new TimedMarkerUtils<PosSensors>().toMaximaArrays(dependency, TimedMarkerUtils.createSensorsExtractorXY()));

        TwainLever lever = new TwainLever(NEAR, FAR, ANGLE_XAB_BY_SENSOR_NEAR, ANGLE_ABC_BY_SENSOR_FAR);


        List<CartesianPoint> cartesianPoints = new ArrayList<>();
        for (PosSensors posSensors : dependency) {
            CartesianPoint point = lever.position(posSensors).toCartesianPoint();
            cartesianPoints.add(point);
        }

        System.out.println("TimedMarkerUtils.toMaximaArrays() = \n" + new TimedMarkerUtils<CartesianPoint>().toMaximaArrays(cartesianPoints, TimedMarkerUtils.createCartesianPointExtractorXY()));


        List<CartesianPoint> circlePoints = new Circle(30.0, new CartesianPoint(100, 100)).getPoints(40);

        System.out.println("Circle cartesian points = \n" + new TimedMarkerUtils<CartesianPoint>().toMaximaArrays(circlePoints, TimedMarkerUtils.createCartesianPointExtractorXY()));

        List<PosSensors> expectCircleSensors = new ArrayList<>();

        for (CartesianPoint circlePoint : circlePoints) {
            PosSensors sensors =lever.expectSensors(circlePoint.toPolarPoint());
            expectCircleSensors.add(sensors);
        }

        System.out.println("Circle expect sensors = \n" + new TimedMarkerUtils<PosSensors>().toMaximaArrays(expectCircleSensors, TimedMarkerUtils.createSensorsExtractorXY()));


        Section section = new Section(new CartesianPoint(100, 100), new CartesianPoint(120, 180));

        List<PosSensors> sectionSensors = new ArrayList<>();
        for (CartesianPoint sectionPoint : section.split(20)) {
            PosSensors sectionExpectedSensors = lever.expectSensors(sectionPoint.toPolarPoint());
            sectionSensors.add(sectionExpectedSensors);
        }

        System.out.println("Section expect sensors = \n" + new TimedMarkerUtils<PosSensors>().toMaximaArrays(sectionSensors, TimedMarkerUtils.createSensorsExtractorXY()));



    }

    public static String draw(CartesianPoint p1, CartesianPoint p2) {
        StringBuffer buffer = new StringBuffer();

        buffer.append(String.format("ctx.moveTo(%s,%s);\n", 3*Math.round(p1.x) + 300, -Math.round(p1.y)*3 + 400));
        buffer.append(String.format("ctx.lineTo(%s,%s);\n", 3*Math.round(p2.x) + 300, -Math.round(p2.y)*3 + 400));
        buffer.append("ctx.stroke();\n");

        return buffer.toString();
    }

    public static void calibration() {

        CoupledTwainLeverPair pair = new CoupledTwainLeverPair();
        LeversPosition pos = new LeversPosition(pair);

        int calibrationLen = CoupledTwainLeverPair.ADC_CALIBRATION_DATA.length;

        double leftAdc[] = new double[calibrationLen];
        double rightAdc[] = new double[calibrationLen];
        double leftAngle[] = new double[calibrationLen];
        double rightAngle[] = new double[calibrationLen];

        int i=0;

        for (CalibrationCase c : CoupledTwainLeverPair.ADC_CALIBRATION_DATA) {

            double x = c.x;
            double y = c.y;


            LeverAngles angles = pos.calcAngles(new CartesianPoint(x, y));
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

        System.out.println("resultLeft = " + resultLeft);
        System.out.println("resultRight = " + resultRight);

        System.out.println();
    }

    public static void restrictedArea() {

        CoupledTwainLeverPair pair = new CoupledTwainLeverPair();

        LeversPosition position = new LeversPosition(pair);

        for (Section section : pair.workingArea.getSections()) {
            dumpSection(position, section);
        }


    }

    private static void dumpSection(LeversPosition position, Section sideSection) {
        Collection<CartesianPoint> sidePoints = sideSection.split(10);

        for (CartesianPoint sidePoint : sidePoints) {

            LeverAngles leverAngles = position.calcAngles(sidePoint);

            //System.out.println(side + " = " + sidePoint + " " + leverAngles);
            System.out.println(leverAngles.left + " " + leverAngles.right);
        }
    }

    public static void main(String[] args) {



        //calibration();

        //restrictedArea();

        //correlateLevers();






        CoupledTwainLeverPair pair = new CoupledTwainLeverPair();
        LeversPosition pos = new LeversPosition(pair);

        LeverAngles leverAngles = pos.calcAngles(new CartesianPoint(70, 100));

        for (CalibrationCase c : CoupledTwainLeverPair.ADC_CALIBRATION_DATA){

            CartesianPoint point = pos.penByAdc(new LeverAnglesSensor(c.near, c.far));

            System.out.println("point = " + point);
            System.out.println("c = " + c);

            System.out.println();



        }

        LeverAnglesSensor sensor = pos.adcByPen(new CartesianPoint(50, 80));

        CartesianPoint point = pos.penByAdc(new LeverAnglesSensor(0.582077, 0.193898));

        // 0.582077, 0.193898, 50, 80

        System.out.println("sensor = " + sensor);
        System.out.println("point = " + point);

        if(true) {
            return;
        }


        System.out.println("pos = " + pos);
        System.out.println("pen             = " + pos.pen);
        System.out.println("rightLowerTop   = " + pos.rightLowerTop);
        System.out.println("bothTop         = " + pos.bothTop);
        System.out.println("leftLowerTop    = " + pos.leftLowerTop);
        System.out.println("leverAngles     = " + leverAngles);


        System.out.println(draw(pair.leverRightStart, pos.rightLowerTop));
        System.out.println(draw(pos.rightLowerTop,  pos.pen));
        System.out.println(draw(pos.bothTop,        pos.pen));
        System.out.println(draw(pos.bothTop,        pos.rightLowerTop));

        System.out.println(draw(pair.leverLeftStart,  pos.leftLowerTop));
        System.out.println(draw(pos.leftLowerTop,     pos.bothTop));
        pos = new LeversPosition(pair);

        pos.calcPen(leverAngles);

        System.out.println("pen             = " + pos.pen);
        System.out.println("rightLowerTop   = " + pos.rightLowerTop);
        System.out.println("bothTop         = " + pos.bothTop);
        System.out.println("leftLowerTop    = " + pos.leftLowerTop);



    }

    static void correlateLevers() {
        LinearRegressionResult result1 = LinearRegression.calc(y1, x1);

        System.out.println("result1 = " + result1);

        LinearRegressionResult result2 = LinearRegression.calc(y2, x2);

        System.out.println("result2 = " + result2);
    }
}
