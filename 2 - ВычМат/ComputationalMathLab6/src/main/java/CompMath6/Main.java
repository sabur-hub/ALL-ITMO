package CompMath6;

import CompMath6.Computations.AdamsMethod;
import CompMath6.Computations.EulerMethod;
import CompMath6.InputOutput.InputConsole;
import CompMath6.InputOutput.OutputConsole;

public class Main {
    public static void main(String[] args) {
        InputConsole inputConsole = new InputConsole();
        OutputConsole outputConsole = new OutputConsole();
        EulerMethod eulerMethod = new EulerMethod();
        AdamsMethod adamsMethod = new AdamsMethod();
        DrawChart drawChart = new DrawChart();
        int inputType;
        do {
            inputType = inputConsole.methodInput();
            switch (inputType) {
                case (1) -> {
                    int functionNumber = inputConsole.functionSelection();
                    double a = inputConsole.inputA();
                    double b = inputConsole.inputB();
                    double h = inputConsole.inputH();
                    double y0 = inputConsole.inputY();
                    int e = inputConsole.inputE();
                    double[][] result = eulerMethod.startCalculations(a, b, y0, h, functionNumber);
                    outputConsole.printInterval(a, b, h);
                    outputConsole.printTable(result, e);
                    drawChart.draw(result, "Модифицированный метод Эйлера");
                }
                case (2) -> {
                    int functionNumber = inputConsole.functionSelection();
                    double a = inputConsole.inputA();
                    double b = inputConsole.inputB();
                    double h = inputConsole.inputH();
                    double y0 = inputConsole.inputY();
                    double eps = inputConsole.inputEps();
                    int e = inputConsole.inputE();
                    double[][] result = adamsMethod.startCalculations(a, b, y0, h, eps, functionNumber);
                    outputConsole.printInterval(a, b, h);
                    outputConsole.printTable(result, e);
                    drawChart.draw(result, "Метод Адамса");
                }
                case (3) -> System.out.println(">>> Завершение работы <<<");
            }

        } while (inputType != 3);
    }
}
