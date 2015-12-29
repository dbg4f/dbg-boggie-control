package dbg.misc.calc;

class LeverAngles {
    public final double left;
    public final double right;

    LeverAngles(double left, double right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "LeverAngles{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
