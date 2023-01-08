import CompMath6.Computations.EulerMethod;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EulerMethodTest {
    EulerMethod eulerMethod = new EulerMethod();

    @Test
    public void testOne() {
        double a = 2, b = 3, h = 0.2, y0 = 2.5;
        int functionNumber = 2;
        double[][] rightResult = {{2, 2.5, 2.43}, {2.2, 2.98, 2.43},
                {2.4, 3.47, 2.41}, {2.6, 3.95, 2.4}, {2.8000000000000003, 4.43, 2.39}, {3, 4.91, 2.41}};

        double[][] result = eulerMethod.startCalculations(a, b, y0, h, functionNumber);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = (int)(result[i][j] * 100) * 0.01;
                //System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
        assertArrayEquals(rightResult, result);
    }
}
