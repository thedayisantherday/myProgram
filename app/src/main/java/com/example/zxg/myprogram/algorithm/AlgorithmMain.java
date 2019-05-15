package com.example.zxg.myprogram.algorithm;

public class AlgorithmMain {
    public static void main(String[] args) throws Exception {
        int[] arrays = new int[]{15, 3, 6, 49, 8, 38, 65, 2, 97, 76, 13, 27, 7, 14, 19, 1};
        QuickSort.quickSort(arrays, 0, arrays.length-1);
        int[] result = arrays;
    }
}
