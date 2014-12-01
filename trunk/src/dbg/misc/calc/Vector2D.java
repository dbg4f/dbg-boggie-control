package dbg.misc.calc;

/**
 * @author bogdel
 */
public class Vector2D {

  public final double x;
  public final double y;

  public Vector2D(double y, double x) {
    this.y = y;
    this.x = x;
  }

  public CartesianPoint endPoint() {
    return new CartesianPoint(x, y);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Vector2D{");
    sb.append("x=").append(x);
    sb.append(", y=").append(y);
    sb.append('}');
    return sb.toString();
  }
}
