package CompMath6.InputOutput;

public class OutputConsole {
    public void printInterval(double a, double b, double h) {
        System.out.println("\nРешение на интервале [ " + a + ": " + b + " ] с шагом h = " + h);
    }

    public void printTable(double[][] result, int e) {
        System.out.println("--------------------------------------");
        System.out.print("i\t\tXi\t\t\tYi\t\t\tFi\n");
        for (int i = 0; i < result.length; i++) {
            System.out.print(i + "\t\t" + (Math.round(result[i][0] * Math.pow(10, e)) / Math.pow(10, e)) + "\t\t"
                    + (Math.round(result[i][1] * Math.pow(10, e)) / Math.pow(10, e)) + "\t\t"
                    + (Math.round(result[i][2] * Math.pow(10, e)) / Math.pow(10, e)) + "\n");
        }
        System.out.println("--------------------------------------\n");
    }
}
