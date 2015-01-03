package dbg.misc.format;

import com.google.gson.Gson;
import dbg.misc.calc.CartesianPoint;
import dbg.misc.calc.PosSensors;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class TimedMarkerUtils<T> {

    /*

#define MARKER_TYPE_PWM_NEAR 'N'
#define MARKER_TYPE_PWM_FAR  'F'
#define MARKER_TYPE_POS_NEAR 'n'
#define MARKER_TYPE_POS_FAR  'f'
     */

    public static final int  MARKER_TYPE_PWM_NEAR = 'N';
    public static final int  MARKER_TYPE_PWM_FAR  = 'F';
    public static final int  MARKER_TYPE_POS_NEAR = 'n';
    public static final int  MARKER_TYPE_POS_FAR  = 'f';

    public static List<PosSensors> merge(List<TimedMarker> timedMarkers) {
        double currentFar = getFirstEntry(timedMarkers, MARKER_TYPE_POS_FAR);
        double currentNear = getFirstEntry(timedMarkers, MARKER_TYPE_POS_NEAR);

        List<PosSensors> dependency = new ArrayList<>();

        for (TimedMarker timedMarker : timedMarkers) {

            if (timedMarker.getTypeCode() == MARKER_TYPE_POS_FAR) {
                currentFar = timedMarker.getValue();
                dependency.add(new PosSensors(currentNear, currentFar));
            }
            if (timedMarker.getTypeCode() == MARKER_TYPE_POS_NEAR) {
                currentNear = timedMarker.getValue();
                dependency.add(new PosSensors(currentNear, currentFar));
            }

        }

        return dependency;

    }

    public static double getFirstEntry(List<TimedMarker> timedMarkers, int typeCode) {
        for (TimedMarker marker : timedMarkers) {
            if (typeCode == marker.getTypeCode()) {
                return marker.getValue();
            }
        }
        throw new IllegalArgumentException("Type " + typeCode + " not found in list");
    }

    public static TimedMarker[] readArrayTimedMarkers(String path, String fileName) throws IOException {
        byte[] content = Files.readAllBytes(FileSystems.getDefault().getPath(path, fileName));

        Gson gson = new Gson();

        TimedMarker[] markers = gson.fromJson(new String(content), TimedMarker[].class);

        return markers;

    }

    public static PositionReport[] readArrayPositionReports(String path, String fileName) throws IOException {
        byte[] content = Files.readAllBytes(FileSystems.getDefault().getPath(path, fileName));

        Gson gson = new Gson();

        PositionReport[] reports = gson.fromJson(new String(content), PositionReport[].class);

        return reports;

    }

    public static XY<PosSensors> createSensorsExtractorXY() {
        return new XY<PosSensors>() {
            @Override
            public double x(PosSensors posSensors) {
                return posSensors.near;
            }

            @Override
            public double y(PosSensors posSensors) {
                return posSensors.far;
            }
        };
    }

    public static XY<PositionReport> createPositionReportExtractorXY() {
        return new XY<PositionReport>(){

            @Override
            public double x(PositionReport positionReport) {
                return positionReport.getT();
            }

            @Override
            public double y(PositionReport positionReport) {
                return positionReport.getPosition();
            }
        };

    }

    public static XY<CartesianPoint> createCartesianPointExtractorXY() {
        return new XY<CartesianPoint>() {
            @Override
            public double x(CartesianPoint posSensors) {
                return posSensors.x;
            }

            @Override
            public double y(CartesianPoint posSensors) {
                return posSensors.y;
            }
        };
    }

    public String toMaximaArrays(List<T> list, XY<T> xyExtractor) {

        StringBuilder x = new StringBuilder();
        StringBuilder y = new StringBuilder();

        x.append("x:[");
        y.append("y:[");

        for (T t : list) {
            x.append(xyExtractor.x(t)).append(",");
            y.append(xyExtractor.y(t)).append(",");
        }


        x.deleteCharAt(x.length()-1);
        y.deleteCharAt(y.length()-1);

        x.append("];");
        y.append("];");

        String tailPlot = "\nplot2d([discrete,x,y])$\n";
        //String tailPlot = "\nplot2d([discrete,x,y],[style, [points, 1, 5, 1]])$\n";

        return x.toString() + "\n" + y.toString() + tailPlot;

    }






}
