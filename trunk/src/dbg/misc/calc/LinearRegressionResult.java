package dbg.misc.calc;

public class LinearRegressionResult {

    public final double beta1;
    public final double beta0;

    public double R2;
    public double stdErrBeta1;
    public double stdErrBeta01;
    public double stdErrBeta02;

    public double SSTO;
    public double SSE;
    public double SSR;


    public LinearRegressionResult(double beta1, double beta0) {
        this.beta1 = beta1;
        this.beta0 = beta0;
    }

    public double y(double x) {
        return beta1 * x + beta0;
    }

    @Override
    public String toString() {
        return "LinearRegressionResult{" +
                "beta1=" + beta1 +
                ", beta0=" + beta0 +
                ", R2=" + R2 +
                ", stdErrBeta1=" + stdErrBeta1 +
                ", stdErrBeta01=" + stdErrBeta01 +
                ", stdErrBeta02=" + stdErrBeta02 +
                ", SSTO=" + SSTO +
                ", SSE=" + SSE +
                ", SSR=" + SSR +
                '}';
    }
}
