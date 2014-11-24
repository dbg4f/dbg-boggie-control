package dbg.misc.calc;

class CalibrationCase {

    final double near;
    final double far;
    final double x;
    final double y;

    double a;
    double b;

    CalibrationCase(double near, double far, double x, double y) {
        this.near = near;
        this.far = far;
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return "CalibrationCase{" +
                "near=" + near +
                ", far=" + far +
                ", x=" + x +
                ", y=" + y +
                ", a=" + a +
                ", b=" + b +
                '}';
    }
}
