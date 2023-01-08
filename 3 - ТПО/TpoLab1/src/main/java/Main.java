import task2.BreadCrunch;
import task2.ChainHashCollection;
import task2.HashSort;

import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        Integer[] arr = new Integer[]{436, 866, 876, 640, 756, 625, 337, 815, 649, 235};
        ChainHashCollection<Integer> sorter = new ChainHashCollection<>();
        BreadCrunch bc = new BreadCrunch();
        sorter.sortBC(arr, bc);
        System.out.println(bc.getTrace());
        System.out.println(Arrays.toString(arr));
    }
}
