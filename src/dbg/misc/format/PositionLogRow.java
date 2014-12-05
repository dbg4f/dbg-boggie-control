package dbg.misc.format;

/**
 * @author bogdel
 */
public class PositionLogRow {

  private double posNear;
  private double posFar;
  private double pwmNear;
  private double pwmFar;
  private long t;


  public double getPosNear() {
    return posNear;
  }

  public void setPosNear(double posNear) {
    this.posNear = posNear;
  }

  public double getPosFar() {
    return posFar;
  }

  public void setPosFar(double posFar) {
    this.posFar = posFar;
  }

  public double getPwmNear() {
    return pwmNear;
  }

  public void setPwmNear(double pwmNear) {
    this.pwmNear = pwmNear;
  }

  public double getPwmFar() {
    return pwmFar;
  }

  public void setPwmFar(double pwmFar) {
    this.pwmFar = pwmFar;
  }

  public long getT() {
    return t;
  }

  public void setT(long t) {
    this.t = t;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("PositionLogRow{");
    sb.append("posNear=").append(posNear);
    sb.append(", posFar=").append(posFar);
    sb.append(", pwmNear=").append(pwmNear);
    sb.append(", pwmFar=").append(pwmFar);
    sb.append(", t=").append(t);
    sb.append('}');
    return sb.toString();
  }
}
