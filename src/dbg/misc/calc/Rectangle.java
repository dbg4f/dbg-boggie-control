package dbg.misc.calc;

public class Rectangle {

    public final CartesianPoint corner1;
    public final CartesianPoint corner2;

    public Rectangle(CartesianPoint corner1, CartesianPoint corner2) {
        this.corner1 = corner1;
        this.corner2 = corner2;
    }

    public Rectangle(double x1, double y1, double x2, double y2) {
        this(new CartesianPoint(x1, y1), new CartesianPoint(x2, y2));
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "corner1=" + corner1 +
                ", corner2=" + corner2 +
                '}';
    }
}
