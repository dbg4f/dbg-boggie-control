package dbg.misc.calc;

import java.util.ArrayList;
import java.util.List;

public class Circle {

    public final double R;

    public final CartesianPoint center;

    public Circle(double r, CartesianPoint center) {
        R = r;
        this.center = center;
    }


    public List<CartesianPoint> getPoints(int count) {

        List<CartesianPoint> points = new ArrayList<>();

        double angleStep = 2.0*Math.PI / count;

        for (int i=0; i<count; i++) {
            double currentAngle = i * angleStep;
            CartesianPoint point = new CartesianPoint(R * Math.cos(currentAngle) + center.x, R * Math.sin(currentAngle) + center.y);
            points.add(point);
        }

        return points;
    }


}
