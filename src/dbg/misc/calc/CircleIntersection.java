package dbg.misc.calc;

import java.util.ArrayList;
import java.util.List;

public class CircleIntersection {

    private final Circle c1;
    private final Circle c2;

    private List<CartesianPoint> intersectionPoints = new ArrayList<>();

    public CircleIntersection(Circle c1, Circle c2) {
        this.c1 = c1;
        this.c2 = c2;
        intersect(c1.center.x, c1.center.y, c2.center.x, c2.center.y, c1.R, c2.R);
    }



    private void intersect(double a, double b, double c, double d, double r0, double r1) {

        double D = Math.sqrt((c - a)*(c - a) + (d - b)*(d - b));

        if (r0 + r1 > D && D > Math.abs(r0 - r1)) {


            double delta = (1.0/4.0) * Math.sqrt((D + r0 + r1)*(D + r0 - r1)*(D - r0 + r1)*(-D + r0 + r1));

            double xx1 = (a + c)/2.0 + (c - a)*(r0*r0 - r1*r1)/(2.0*D*D);
            double xx2 = 2.0*((b - d)/(D*D))*delta;

            double yy1 = (b + d)/2.0 + (d - b)*(r0*r0 - r1*r1)/(2.0*D*D);
            double yy2 = 2.0*((a - c)/(D*D))*delta;

            intersectionPoints.add(new CartesianPoint(xx1 + xx2, yy1 - yy2));
            intersectionPoints.add(new CartesianPoint(xx1 - xx2, yy1 + yy2));
        }



    }

    public CartesianPoint getLeft() {
        if (intersectionPoints.size() !=2) {
            throw  new IllegalStateException("No points of circle intersection");
        }

        CartesianPoint point1 = intersectionPoints.get(0);
        CartesianPoint point2 = intersectionPoints.get(1);
        return point1.x < point2.x ? point1 : point2;

    }


    public CartesianPoint getRight() {
        if (intersectionPoints.size() !=2) {
            throw  new IllegalStateException("No points of circle intersection");
        }

        CartesianPoint point1 = intersectionPoints.get(0);
        CartesianPoint point2 = intersectionPoints.get(1);
        return point1.x > point2.x ? point1 : point2;


    }

    public CartesianPoint getUpper() {
        if (intersectionPoints.size() !=2) {
            throw  new IllegalStateException("No points of circle intersection");
        }

        CartesianPoint point1 = intersectionPoints.get(0);
        CartesianPoint point2 = intersectionPoints.get(1);
        return point1.y > point2.y ? point1 : point2;

    }


    /*
    public static void main(String[] args) {
        CircleIntersection intersection = new CircleIntersection(new Circle(3, new CartesianPoint(1, 2)), new Circle(4, new CartesianPoint(3,-1)));

        System.out.println(intersection.intersectionPoints);
    }
    */


}
