package CompMath4.Computations;

public class Functions {

    public double f(double x, double a, double b, double c, int functionNumber) {
        switch (functionNumber) {
            case (1) -> {
                return a * x + b;
            }
            case (2) -> {
                return a * Math.pow(x, b);
            }
            case (3) -> {
                return a * Math.pow(Math.E, b * x);
            }
            case (4) -> {
                if (x != 0)
                    return a * Math.log(x) + b;
                else
                    return b;
            }
            default -> {
                return a * Math.pow(x, 2) + b * x + c;
            }
        }
    }

    public double getA(double sum_x, double sum_xx, double sum_y, double sum_xy,
                       double sum_x_ln, double sum_y_ln, double sum_xx_ln, double sum_xy_ln,
                       double sum_y_ln_x, double sum_x_ln_y, int n, int functionNumber) {
        double v = n * sum_xx_ln - Math.pow(sum_x_ln, 2);

        switch (functionNumber) {
            case (1) -> {
                return (sum_xy * n - sum_x * sum_y) / (sum_xx * n - sum_x * sum_x);
            }
            case (2) -> {
                return Math.pow(Math.E, (sum_y_ln * sum_xx_ln - sum_xy_ln * sum_x_ln) / v);
            }
            case (3) -> {
                return Math.pow(Math.E, (sum_y_ln * sum_xx - sum_y_ln_x * sum_x) / (n * sum_xx - Math.pow(sum_x, 2)));
            }
            default -> {
                return (n * sum_x_ln_y - sum_y * sum_x_ln) / v;
            }
        }
    }

    public double getB(double sum_x, double sum_xx, double sum_y, double sum_xy,
                       double sum_x_ln, double sum_y_ln, double sum_xx_ln, double sum_xy_ln,
                       double sum_y_ln_x, double sum_x_ln_y, int n, int functionNumber) {
        double v = n * sum_xx_ln - Math.pow(sum_x_ln, 2);

        switch (functionNumber) {
            case (1) -> {
                return (sum_xy * sum_x - sum_xx * sum_y) / (Math.pow(sum_x, 2) - n * sum_xx);
            }
            case (2) -> {
                return (n * sum_xy_ln - sum_y_ln * sum_x_ln) / v;
            }
            case (3) -> {
                return (n * sum_y_ln_x - sum_y_ln * sum_x) / (n * sum_xx - Math.pow(sum_x, 2));
            }
            default -> {
                return (sum_y * sum_xx_ln - sum_x_ln_y * sum_x_ln) / v;
            }
        }
    }

    public double deviationMeasure(double[][] points, double a, double b, double c, int functionNumber) {
        double sum = 0;
        for (int i = 0; i < points[0].length; i++) {
            sum += Math.pow(f(points[0][i], a, b, c, functionNumber) - points[1][i], 2);
        }
        return sum;
    }

    public double squareDeviation(double[][] points, double a, double b, double c, int functionNumber) {
        return Math.sqrt(deviationMeasure(points, a, b, c, functionNumber) / points[0].length);
    }

    public double getR(double[][] points, double a, double b, double c, int functionNumber) {
        double deviation_measure_sqr = 0, Fi_sqr = 0, Fi = 0;
        for (int i = 0; i < points[0].length; i++) {
            deviation_measure_sqr += Math.pow(points[1][i] - f(points[0][i], a, b, c, functionNumber), 2);
            Fi_sqr += Math.pow(f(points[0][i], a, b, c, functionNumber), 2);
            Fi += f(points[0][i], a, b, c, functionNumber);
        }
        return 1 - ((deviation_measure_sqr) / (Fi_sqr - Math.pow(Fi, 2) / points[0].length));
    }

    public boolean checkMinus(double[][] points) {
        for (int i = 0; i < points[0].length; i++) {
            if (points[0][i] < 0 || points[1][i] < 0)
                return false;
        }
        return true;
    }

    public void selectedFunction(double[][] points, double[][] dataSetChart) {
        double min_q = squareDeviation(points, dataSetChart[0][0], dataSetChart[0][1], 0, 1);
        int num_selected_f = 1;

        for (int i = 1; i <= 5; i++) {
            if (min_q > squareDeviation(points, dataSetChart[i - 1][0], dataSetChart[i - 1][1], dataSetChart[i - 1][2], i)) {
                min_q = squareDeviation(points, dataSetChart[i - 1][0], dataSetChart[i - 1][1], dataSetChart[i - 1][2], i);
                num_selected_f = i;
            }
        }

        System.out.println("?????????????????? ???????????????????????????????? ?????????????? ???" + num_selected_f + " Fi = ax+b");
        System.out.println("a = " + dataSetChart[num_selected_f - 1][0]);
        System.out.println("b = " + dataSetChart[num_selected_f - 1][1]);
        System.out.println("S = " + deviationMeasure(points, dataSetChart[num_selected_f - 1][0],
                dataSetChart[num_selected_f - 1][1], dataSetChart[num_selected_f - 1][2], num_selected_f));
        System.out.println("q = " + min_q);
        System.out.println("R = " + getR(points, dataSetChart[num_selected_f - 1][0], dataSetChart[num_selected_f - 1][1],
                dataSetChart[num_selected_f - 1][2], num_selected_f));
    }
}