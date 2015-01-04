package dbg.misc.calc;

import dbg.misc.format.JsonUtils;
import dbg.misc.format.PositionReport;
import dbg.misc.format.TimedMarker;
import dbg.misc.format.TimedMarkerUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dbg.misc.calc.CncCalc.*;

public class UtilsLauncher {

    public static void main(String[] args) throws IOException {


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

}
