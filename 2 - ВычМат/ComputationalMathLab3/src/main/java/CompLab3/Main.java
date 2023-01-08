package CompLab3;

import CompLab3.IntegrationMethods.RectangleMethod;
import CompLab3.IntegrationMethods.SimpsonMethod;
import CompLab3.IntegrationMethods.TrapezoidMethod;

public class Main {
    public static void main(String[] args) {
        InputFunctions inputFunctions = new InputFunctions();
        SimpsonMethod simpsonMethod = new SimpsonMethod();
        TrapezoidMethod trapezoidMethod = new TrapezoidMethod();
        RectangleMethod rectangleMethod = new RectangleMethod();

        inputFunctions.Start();
        try {
            while (true) {
                int functionNumber = inputFunctions.inputFunction();
                if (functionNumber != 0) {
                    int methodNumber = inputFunctions.inputMethod();
                    switch (methodNumber) {
                        case (1) -> trapezoidMethod.startMethod(inputFunctions.inputA(), inputFunctions.inputB(), inputFunctions.inputE(), functionNumber);
                        case (2) -> simpsonMethod.startMethod(inputFunctions.inputA(), inputFunctions.inputB(), inputFunctions.inputE(), functionNumber);
                        case (3) -> {
                            int methodOfRectangles = inputFunctions.inputMethodOfRectangles();
                            double a = inputFunctions.inputA();
                            double b = inputFunctions.inputB();
                            double e = inputFunctions.inputE();

                            DrawChart draw = new DrawChart();
                            draw.draw(a, b, functionNumber);

                            switch (methodOfRectangles) {
                                case (1) -> rectangleMethod.startMethodRight(a, b, e, functionNumber);
                                case (2) -> rectangleMethod.startMethodLeft(a, b, e, functionNumber);
                                case (3) -> rectangleMethod.startMethodMid(a, b, e, functionNumber);
                                default -> {
                                    rectangleMethod.startMethodRight(a, b, e, functionNumber);
                                    rectangleMethod.startMethodLeft(a, b, e, functionNumber);
                                    rectangleMethod.startMethodMid(a, b, e, functionNumber);
                                }
                            }
                        }
                        default -> System.out.println("Недопустимый номер, повторите ввод...");
                    }
                } else {
                    System.out.println(">>> Программа завершила свою работу <<<");
                    break;
                }
            }
        } catch (java.util.NoSuchElementException ex) {
            System.out.println(">>> Программа завершила свою работу <<<");
        }
    }
}