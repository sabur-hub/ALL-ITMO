package task2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ChainHashSortTest {
    static ChainHashCollection<Integer> sorter;

    @BeforeAll
    static void init() {
        sorter = new ChainHashCollection<>();
    }

    @Test
    void sort() {
        BreadCrumbs bc = new BreadCrumbs();
        Integer[] arr = new Integer[]{436, 866, 876, 640, 756, 625, 815, 649, 235, 640};
        Integer[] sortedArr = new Integer[]{235, 436, 625, 640, 640, 649, 756, 815, 866, 876};
        String expected = "f4f9f9f7f8f6f8f7f2f8f235f436f625f640f640f649f756f815f866f876";
        sorter.sortBC(arr, bc);
        assertEquals(expected, bc.getTrace());
        assertArrayEquals(sortedArr, arr);
    }

    @Test
    void checkMaxVals() {
        Integer[] maxValsArr = new Integer[]{1000, 999, -1000, -999, Integer.MAX_VALUE, Integer.MIN_VALUE};
        Integer[] maxValsRes = new Integer[]{-999, 999, -1000, -999, Integer.MAX_VALUE, Integer.MIN_VALUE};
        sorter.sort(maxValsArr);
        assertEquals(Arrays.toString(maxValsRes), Arrays.toString(maxValsArr));
        assertArrayEquals(maxValsRes, maxValsArr);
    }

    @Test
    void checkNull() {
        Integer[] array = null;
        sorter.sort(array);
        assertArrayEquals(null, array);
    }

    @Test
    void checkRepeatitions() {
        Integer[] repeatedArray = new Integer[]{0, 0, 1, 0, 0, 0};
        Integer[] repeatedResult = new Integer[]{0, 0, 0, 0, 0, 1};
        sorter.sort(repeatedArray);
        assertArrayEquals(repeatedResult, repeatedArray);
    }

}