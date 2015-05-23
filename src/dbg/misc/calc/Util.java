package dbg.misc.calc;

public class Util {

    public static double GENERAL_CALC_PRECISION = 0.0001;

    public static boolean isAlmostEqual(double v1, double v2) {
        return Math.abs(v1 - v2) < GENERAL_CALC_PRECISION;
    }


    public static long now() {
        return System.currentTimeMillis();
    }
}
