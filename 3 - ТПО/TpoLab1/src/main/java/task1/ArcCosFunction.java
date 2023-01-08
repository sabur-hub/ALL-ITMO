package task1;

public class ArcCosFunction {
    public static double arccos(double x) {
        if (Math.abs(x) > 1) {
            return Double.NaN;
        }
        double ans = x;
        double a = 1;
        double b = 2;
        double tmp = 1;
        double i = 2;
        while (tmp > 0.001) {
            tmp = Math.pow(x, 2 * i - 1) * a / (b * (2 * i - 1));
            ans += tmp;
            a *= 2L * i - 1;
            b *= 2L * i;
            i++;
        }
        return Math.PI / 2 - ans;
    }
}
