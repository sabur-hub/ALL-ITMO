package CompMath6.InputOutput;

import java.util.Scanner;

public class InputConsole {
    Scanner in = new Scanner(System.in);

    public int methodInput() {
        System.out.println("""
                Выберете действие:
                1. Решение модифицированным методом Эйлера
                2. Решение методом Адамса
                3. Выход из программы
                """);
        try {
            return Integer.parseInt(in.next().trim());
        } catch (NumberFormatException ignored) {
            System.out.println("Ошибка ввода!");
            return methodInput();
        }
    }

    public int functionSelection() {
        System.out.println("""
                Выберете функцию:
                1. 6x^2 + 5xy
                2. x + cos(y / sqrt(5))
                3. y + (1+x) * y^2
                """);
        try {
            int functionNumber = Integer.parseInt(in.next().trim());
            if (functionNumber > 0 && functionNumber <= 3) {
                return functionNumber;
            } else {
                System.out.println("Ошибка ввода!");
                return functionSelection();
            }
        } catch (NumberFormatException ignored) {
            System.out.println("Ошибка ввода!");
            return functionSelection();
        }
    }

    public double inputA() {
        System.out.print("Введите начало интервала a: ");
        try {
            return Double.parseDouble(in.next().trim());
        } catch (NumberFormatException ignored) {
            System.out.println("Ошибка ввода!");
            return inputA();
        }
    }

    public double inputB() {
        System.out.print("Введите конец интервала b: ");
        try {
            return Double.parseDouble(in.next().trim());
        } catch (NumberFormatException ignored) {
            System.out.println("Ошибка ввода!");
            return inputB();
        }
    }

    public double inputY() {
        System.out.print("Введите y0: ");
        try {
            return Double.parseDouble(in.next().trim());
        } catch (NumberFormatException ignored) {
            System.out.println("Ошибка ввода!");
            return inputY();
        }
    }

    public double inputH() {
        System.out.print("Введите шаг h: ");
        try {
            return Double.parseDouble(in.next().trim());
        } catch (NumberFormatException ignored) {
            System.out.println("Ошибка ввода!");
            return inputH();
        }
    }

    public int inputE() {
        System.out.print("Введите точность e: ");
        try {
            return Integer.parseInt(in.next().trim());
        } catch (NumberFormatException ignored) {
            System.out.println("Ошибка ввода!");
            return inputE();
        }
    }

    public double inputEps() {
        System.out.print("Введите точность вычислений eps: ");
        try {
            return Double.parseDouble(in.next().trim());
        } catch (NumberFormatException ignored) {
            System.out.println("Ошибка ввода!");
            return inputEps();
        }
    }
}
