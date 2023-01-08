package task2;

import java.util.*;

public class ChainHashCollection<T extends Number> implements HashSort<T> {
    private final Integer maxValue = 1000;
    private final Vector<SortedMap<T, Integer>> collection;

    public ChainHashCollection(){
        this.collection = new Vector<>(32);
    }

    @Override
    public void sort(T[] arr) {
        if (arr == null) return;
        for (int i = 0; i < arr.length; i++) {
            collection.add(new TreeMap<>());
        }
        for (T t : arr) {
            if (maxValue <= (Integer) t || (Integer) t <= -maxValue) {
                System.err.println("Превышено максимальное значение. Введите числа меньше по модулю, чем "+maxValue);
                System.err.println("Вы ввели: " +t);
                continue;
            }
            int index = countHash(t);
            if (collection.get(index).containsKey(t)) {
                collection.get(index).put(t, collection.get(index).get(t) + 1);
            } else collection.get(index).put(t, 1);
        }
        int index = 0;
        for (SortedMap<T, Integer> map: collection) {
            for (T val: map.keySet()) {
                for (int i = 0; i < map.get(val); i++) {
                    arr[index++] = val;
                }
            }
        }
        collection.clear();
    }

    public void sortBC(T[] arr, BreadCrumbs bc) {
        if (arr == null) return;
        for (int i = 0; i <= arr.length; i++) {
            collection.add(new TreeMap<>());
        }
        for (T t : arr) {
            if (maxValue < Math.abs((Integer) t)) {
                System.err.println("Maximum value exceed");
                bc.appendCrumb(maxValue + 1);
                continue;
            }
            int index = countHash(t);
            if (collection.get(index).containsKey(t)) {
                collection.get(index).put(t, collection.get(index).get(t) + 1);
                bc.appendCrumb(index+1);
            } else {
                collection.get(index).put(t, 1);
                bc.appendCrumb(index);
            }
        }
        int index = 0;
        for (SortedMap<T, Integer> map: collection) {
            for (T val: map.keySet()) {
                for (int i = 0; i < map.get(val); i++) {
                    arr[index++] = val;
                    bc.appendCrumb((Integer)val);
                }
            }
        }
        collection.clear();
    }

    private Integer countHash(T value) {
        return  Math.abs((Integer) value) * collection.size() / maxValue;
    }
}