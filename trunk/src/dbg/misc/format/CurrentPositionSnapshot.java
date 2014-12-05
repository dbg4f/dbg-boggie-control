package dbg.misc.format;

/**
 * @author bogdel
 */
public class CurrentPositionSnapshot {

  private long counter;
  private double near;
  private double far;
  private long t;

  public long getCounter() {
    return counter;
  }

  public void setCounter(long counter) {
    this.counter = counter;
  }

  public double getNear() {
    return near;
  }

  public void setNear(double near) {
    this.near = near;
  }

  public double getFar() {
    return far;
  }

  public void setFar(double far) {
    this.far = far;
  }

  public long getT() {
    return t;
  }

  public void setT(long t) {
    this.t = t;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CurrentPositionSnapshot{");
    sb.append("counter=").append(counter);
    sb.append(", near=").append(near);
    sb.append(", far=").append(far);
    sb.append(", t=").append(t);
    sb.append('}');
    return sb.toString();
  }
}
