package org.lia;

public class SecCalc {
    public static double Calc(double x, double eps, int lim) {
        double cos_sum = 1.0;
        double term = 1.0;
        double xx = x * x;

        for (int k = 1; k <= lim; k++) {
            term *= -xx / ((2.0 * k - 1.0) * (2.0 * k));
            cos_sum += term;
            if (Math.abs(term) < eps) {
                break;
            }
        }

        if (cos_sum == 0.0) {
            throw new ArithmeticException("illegal arg: cos(x)=0");
        }

        return 1.0 / cos_sum;
    }

    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        }
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
