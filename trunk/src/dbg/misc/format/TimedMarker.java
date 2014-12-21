package dbg.misc.format;

/**
 * @author bogdel
 */
public class TimedMarker {


  public long t;
  public double value;
  public int typeCode;


  public long getT() {
    return t;
  }

  public void setT(long t) {
    this.t = t;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public int getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(int typeCode) {
    this.typeCode = typeCode;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TimedMarker{");
    sb.append("t=").append(t);
    sb.append(", value=").append(value);
    sb.append(", typeCode=").append(typeCode);
    sb.append('}');
    return sb.toString();
  }
}
