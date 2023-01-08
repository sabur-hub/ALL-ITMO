package CompLab3.IntegrationMethods;

import CompLab3.DrawChart;
import CompLab3.Functions;
import CompLab3.OutputFunctions;

public class TrapezoidMethod {
    Functions functions = new Functions();
    DrawChart drawChart = new DrawChart();
    OutputFunctions outputFunctions = new OutputFunctions();

    public void startMethod(double a, double b, double e, int number) {
        if (a > b) {
            double tmp = a;
            a = b;
            b = tmp;
        }

        double I = functions.getI(a, b, number), step, save_down = a, y0, yn, sum, r = e + 1, answer = 0;
        long n = 2;
        //double R = fun.IfMax(a,b,number)*Math.pow((b-a),3)/12/Math.pow(n,2);

        while (r > e) {
            n *= 2;
            step = (b - a) / n;
            y0 = functions.f(a, number);
            a += step;
            yn = functions.f(b, number);
            sum = 0;

            for (int i = 1; i < n; i++) {
                sum += functions.f(a, number);
                a += step;
            }

            answer = step * ((y0 + yn) / 2 + sum);
            r = Math.abs(I - answer);
            a = save_down;
        }

        System.out.println("\nРешение методом трапеций:");

        if (Double.isNaN(answer) || Double.isNaN(I) || Double.isNaN(r) || Double.isNaN(Math.abs(100 * r / ((I + answer) / 2)))) {
            System.out.println("В выбранном интервале присутсвует разрыв первого рода!\n");
        } else {
            drawChart.draw(a, b, number);
            outputFunctions.outAnswer(e, answer, I, r, n);
        }
    }
}
