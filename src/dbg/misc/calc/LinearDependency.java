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

        if (p1.y == p2.y && p1.x== p2.x) {
            throw new IllegalArgumentException("Cannot calc linear dependency based on one and the same point: " + p1 + " " + p2);
        }

        k = (p2.y - p1.y) / (p2.x - p1.x);
        b = p1.y - k * p1.x;
        normalForm = new NormalForm();
    }

    public double getY(double x) {
        return k * x + b;
    }

    public NormalForm toNormalForm() {
      return normalForm;
    }

    public Vector2D normalVector() {
      return new Vector2D(normalForm.A, normalForm.B);
    }

  /*
    public boolean isParallel(LinearDependency line2) {

    }

    public CartesianPoint intersect(LinearDependency line2) {



    }
    */
    @Override
    public String toString() {
        return "LinearDependency{" +
                "k=" + k +
                ", b=" + b +
                '}';
    }
}
