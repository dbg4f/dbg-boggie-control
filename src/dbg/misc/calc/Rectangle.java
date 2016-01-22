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

    public CartesianPoint[] getCorners() {
        RectCorners rectCorners = new RectCorners().invoke();

        return new CartesianPoint[] {
                rectCorners.getLowerLeft(),
                rectCorners.getUpperLeft(),
                rectCorners.getUpperRight(),
                rectCorners.getLowerRight()
        };

    }

    public Section[] getSections() {
        CartesianPoint[] corners = getCorners();
        return new Section[] {
                new Section(corners[0], corners[1]),
                new Section(corners[1], corners[2]),
                new Section(corners[2], corners[3]),
                new Section(corners[3], corners[0])
        };
    }

    public CoordinateRelation getRelation(double target, double min, double max) {
        if (target < min) {
            return CoordinateRelation.BEFORE;
        }
        else if (target > max) {
            return CoordinateRelation.AFTER;
        }
        else {
            return CoordinateRelation.MATCHES;
        }
    }

    public CoordinateRelation[] pointRelation(CartesianPoint point) {

        RectCorners rectCorners = new RectCorners().invoke();

        return new CoordinateRelation[]{
                getRelation(point.x, rectCorners.getLowerLeft().x, rectCorners.getLowerRight().x),
                getRelation(point.y, rectCorners.getLowerLeft().y, rectCorners.getUpperLeft().y)
        };
    }

    public Section getSection(RectangleSide side) {

        RectCorners rectCorners = new RectCorners().invoke();

        CartesianPoint upperLeft = rectCorners.getUpperLeft();
        CartesianPoint upperRight = rectCorners.getUpperRight();
        CartesianPoint lowerLeft = rectCorners.getLowerLeft();
        CartesianPoint lowerRight = rectCorners.getLowerRight();

        if (side == RectangleSide.UPPER) {
            return new Section(upperLeft, upperRight);
        }
        else if (side == RectangleSide.LOWER) {
            return new Section(lowerLeft, lowerRight);
        }
        else if (side == RectangleSide.LEFT) {
            return new Section(lowerLeft, upperLeft);
        }
        else if (side == RectangleSide.RIGHT) {
            return new Section(lowerRight, upperRight);
        }
        else {
            throw new IllegalArgumentException("Rectangle side " + side + " not supported");
        }

    }


    @Override
    public String toString() {
        return "Rectangle{" +
                "corner1=" + corner1 +
                ", corner2=" + corner2 +
                '}';
    }

    private class RectCorners {
        private CartesianPoint lowerLeft;
        private CartesianPoint upperRight;
        private CartesianPoint upperLeft;
        private CartesianPoint lowerRight;

        public CartesianPoint getLowerLeft() {
            return lowerLeft;
        }

        public CartesianPoint getUpperRight() {
            return upperRight;
        }

        public CartesianPoint getUpperLeft() {
            return upperLeft;
        }

        public CartesianPoint getLowerRight() {
            return lowerRight;
        }

        public RectCorners invoke() {
            double x1 = corner1.x;
            double x2 = corner2.x;

            double y1 = corner1.y;
            double y2 = corner2.y;


            double minX = x1 < x2 ? x1 : x2;
            double maxX = x1 > x2 ? x1 : x2;

            double minY = y1 < y2 ? y1 : y2;
            double maxY = y1 > y2 ? y1 : y2;

            lowerLeft = new CartesianPoint(minX, minY);
            upperRight = new CartesianPoint(maxX, maxY);

            upperLeft = new CartesianPoint(minX, maxY);
            lowerRight = new CartesianPoint(maxX, minY);
            return this;
        }
    }
}
