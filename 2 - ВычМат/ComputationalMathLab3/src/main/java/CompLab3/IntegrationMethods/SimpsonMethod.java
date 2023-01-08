package CompLab3.IntegrationMethods;

import CompLab3.DrawChart;
import CompLab3.Functions;
import CompLab3.OutputFunctions;

public class SimpsonMethod {
    Functions functions = new Functions();
    DrawChart drawChart = new DrawChart();
    OutputFunctions outputFunctions = new OutputFunctions();

    public void startMethod(double a, double b, double e, int number) {
        if (a > b) {
            double tmp = a;
            a = b;
            b = tmp;
        }
        double aNew, answer, r, I, step, x0, xn, sum_1, sum_2;
        long n = 2;
        aNew = a;
        answer = 0;
        r = e + 1;
        I = functions.getI(a, b, number);

        while (r > e) {
            n *= 2;
            step = (b - a) / n;

            x0 = functions.f(a, number);
            aNew += step;
            xn = functions.f(b, number);
            sum_1 = 0;
            sum_2 = 0;

            for (int i = 1; i < n; i++) {
                if (i % 2 == 0)
                    sum_2 += functions.f(aNew, number);
                else
                    sum_1 += functions.f(aNew, number);
                aNew += step;
            }
            answer = step * (x0 + xn + 2 * sum_2 + 4 * sum_1) / 3;
            r = Math.abs(I - answer);
            aNew = a;
        }
        System.out.println("\nРешение методом Симпсона:");

        if (Double.isNaN(answer) || Double.isNaN(I) || Double.isNaN(r) || Double.isNaN(Math.abs(100 * r / ((I + answer) / 2)))) {
            System.out.println("В выбранном интервале присутсвует разрыв первого рода!\n");
        } else {
            drawChart.draw(a, b, number);
            outputFunctions.outAnswer(e, answer, I, r, n);
        }
    }
}
