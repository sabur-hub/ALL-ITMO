package CompLab3.IntegrationMethods;

import CompLab3.Functions;
import CompLab3.OutputFunctions;

public class RectangleMethod {
    Functions functions = new Functions();
    OutputFunctions outputFunctions = new OutputFunctions();

    public void startMethodMid(double a, double b, double e, int number) {
        if (a > b) {
            double tmp = a;
            a = b;
            b = tmp;
        }
        double aNew = a, step, sum = 0, r = e + 1, I = functions.getI(a, b, number);
        long n = 2;

        while (r > e) {
            n *= 2;
            step = (b - aNew) / n;
            sum = 0;
            for (int i = 0; i < n; i++) {
                sum += functions.f(aNew + step / 2, number);
                aNew += step;
            }
            sum = sum * step;
            r = Math.abs(I - sum);
            aNew = a;
        }

        System.out.println("\nРешение методом средних прямоугольников:");

        if (Double.isNaN(sum) || Double.isNaN(I) || Double.isNaN(r) || Double.isNaN(Math.abs(100 * r / ((I + sum) / 2)))) {
            System.out.println("В выбранном интервале присутсвует разрыв первого рода!\n");
        } else {
            outputFunctions.outAnswer(e, sum, I, r, n);
        }
    }

    public void startMethodLeft(double a, double b, double e, int number) {
        if (a > b) {
            double tmp = a;
            a = b;
            b = tmp;
        }
        double aNew = a, step, sum = 0, r = e + 1, I = functions.getI(a, b, number);
        long n = 2;

        while (r > e) {
            n *= 2;
            step = (b - aNew) / n;
            sum = 0;
            for (int i = 0; i < n; i++) {
                sum += functions.f(aNew, number);
                aNew += step;
            }
            sum = sum * step;
            r = Math.abs(I - sum);
            aNew = a;
        }

        System.out.println("\nРешение методом левых прямоугольников:");

        if (Double.isNaN(sum) || Double.isNaN(I) || Double.isNaN(r) || Double.isNaN(Math.abs(100 * r / ((I + sum) / 2)))) {
            System.out.println("В выбранном интервале присутсвует разрыв первого рода!\n");
        } else {
            outputFunctions.outAnswer(e, sum, I, r, n);
        }
    }

    public void startMethodRight(double a, double b, double e, int number) {
        if (a > b) {
            double tmp = a;
            a = b;
            b = tmp;
        }
        double aNew = a, step, sum = 0, r = e + 1, I = functions.getI(a, b, number);
        long n = 2;

        while (r > e) {
            n *= 2;
            step = (b - aNew) / n;
            aNew += step;
            sum = 0;
            for (int i = 0; i < n; i++) {
                sum += functions.f(aNew, number);
                aNew += step;
            }
            sum = sum * step;
            r = Math.abs(I - sum);
            aNew = a;
        }

        System.out.println("\nРешение методом правых прямоугольников:");

        if (Double.isNaN(sum) || Double.isNaN(I) || Double.isNaN(r) || Double.isNaN(Math.abs(100 * r / ((I + sum) / 2)))) {
            System.out.println("В выбранном интервале присутсвует разрыв первого рода!\n");
        } else {
            outputFunctions.outAnswer(e, sum, I, r, n);
        }
    }
}
