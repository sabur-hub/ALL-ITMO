package CompLab3;

public class OutputFunctions {
    public void outAnswer(double e, double answer, double I, double r, long n){
        System.out.println("\n------------------------------------------");
        System.out.println("Достигнута заданная точность e = " + e);
        System.out.printf("Calculated I = %.5f\n", answer);
        System.out.printf("Standard I = %.5f\n", I);
        System.out.printf("Абсолютная погрешность |R| = %.5f\n", r);
        System.out.printf("Относительная погрешность E = %.5f", Math.abs(100*r/((I+answer)/2)));
        System.out.println("%");
        System.out.println("n = " + n);
        System.out.println("------------------------------------------\n");
    }
}