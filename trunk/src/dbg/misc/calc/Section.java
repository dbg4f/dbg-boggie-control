package dbg.misc.calc;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author bogdel
 */
public class Section {

  public final CartesianPoint p1;
  public final CartesianPoint p2;

  public Section(CartesianPoint p1, CartesianPoint p2) {
    this.p1 = p1;
    this.p2 = p2;
  }

  public double length() {
    return p1.distanceTo(p2);
  }

  public Collection<CartesianPoint> split(int subsectionsCount) {

    ArrayList<CartesianPoint> subSections = new ArrayList<CartesianPoint>();

    for (int i=0; i<subsectionsCount; i++) {
      subSections.add(new CartesianPoint(rangeValue(p1.x, p2.x, i, subsectionsCount),  rangeValue(p1.y, p2.y, i, subsectionsCount)));
    }

    return subSections;
  }

  private double rangeValue(double start, double end, int index, int count) {
    return  start + ((end - start) * index) / count;
  }


  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Section{");
    sb.append("p1=").append(p1);
    sb.append(", p2=").append(p2);
    sb.append('}');
    return sb.toString();
  }
}
