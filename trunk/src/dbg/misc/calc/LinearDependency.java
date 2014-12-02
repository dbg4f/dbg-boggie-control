package dbg.misc.calc;

public class LinearDependency {

    class NormalForm {

      // Ax + By + C = 0

      public final double A,B,C;

      NormalForm() {
        A = k;
        B = -1;
        C = b;
      }

    }

    public final double k;
    public final double b;
    private final NormalForm normalForm;

    public LinearDependency(double k, double b) {
        this.k = k;
        this.b = b;
        normalForm = new NormalForm();
    }

    public LinearDependency (CartesianPoint p1, CartesianPoint p2) {

        if (Util.isAlmostEqual(p1.y, p2.y) && Util.isAlmostEqual(p1.x, p2.x)) {
            throw new IllegalArgumentException("Cannot calc linear dependency based on one and the same point: " + p1 + " " + p2);
        }

        k = (p2.y - p1.y) / (p2.x - p1.x);
        b = p1.y - k * p1.x;
        normalForm = new NormalForm();
    }

    public double getY(double x) {
        return k * x + b;
    }

    public double getX(double y) {
        return (y - b) / k;
    }

    public double getR(double phi) {
        // in polar coordinates
        return b / (Math.sin(phi) - k*Math.cos(phi));
    }

    public NormalForm toNormalForm() {
      return normalForm;
    }

    public Vector2D normalVector() {
      return new Vector2D(normalForm.A, normalForm.B);
    }


    public boolean isParallel(LinearDependency line2) {
        return Util.isAlmostEqual(k, line2.k);
    }

    public CartesianPoint intersect(LinearDependency line2) {

        if (isParallel(line2)) {
            throw new IllegalArgumentException("Lines are almost parallel: " + this + " " + line2 + " cannot find intersection");
        }

        double xi = (line2.b - b) / (k - line2.k);

        double yi = getY(xi);

        return new CartesianPoint(xi, yi);

    }

    @Override
    public String toString() {
        return "LinearDependency{" +
                "k=" + k +
                ", b=" + b +
                '}';
    }
}
