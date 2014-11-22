package dbg.misc.calc;

public class LinearRegression {


    public static void main(String[] args) {

        System.out.println("args = " + calc(new double[] {1, 2, 3}, new double[] {1, 2, 3}));

    }

    public static LinearRegressionResult calc(double[] x, double[] y) {

        int n = 0;

        // first pass: read in data, compute xbar and ybar
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;

        if (x.length != y.length) {
            throw new IllegalArgumentException("X Y arrays size mismatch: " + x.length + " " + y.length);
        }

        for (int i=0; i<x.length; i++) {
            sumx  += x[n];
            sumx2 += x[n] * x[n];
            sumy  += y[n];
            n++;
        }

        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        double beta1 = xybar / xxbar;
        double beta0 = ybar - beta1 * xbar;

        LinearRegressionResult regressionResult = new LinearRegressionResult(beta1, beta0);

        // analyze results
        int df = n - 2;
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares

        for (int i = 0; i < n; i++) {
            double fit = beta1*x[i] + beta0;
            rss += (fit - y[i]) * (fit - y[i]);
            ssr += (fit - ybar) * (fit - ybar);
        }

        double R2    = ssr / yybar;
        double svar  = rss / df;
        double svar1 = svar / xxbar;
        double svar0 = svar/n + xbar*xbar*svar1;
        regressionResult.R2 = R2;

        regressionResult.stdErrBeta1 = Math.sqrt(svar1);
        regressionResult.stdErrBeta01 = Math.sqrt(svar0);
        regressionResult.stdErrBeta02 = Math.sqrt(svar * sumx2 / (n * xxbar));

        regressionResult.SSTO = yybar;
        regressionResult.SSE  = rss;
        regressionResult.SSR  = ssr;

        return regressionResult;

    }
}