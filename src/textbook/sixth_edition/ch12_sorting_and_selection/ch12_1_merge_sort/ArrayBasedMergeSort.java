package textbook.sixth_edition.ch12_sorting_and_selection.ch12_1_merge_sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Merge sort for array:
 * Time complexity : O(nlogn)
 */
public class ArrayBasedMergeSort {

    public static void main(String[] args) {
        Integer[] a = {2, 4, 6, 6, 0, 9, -1, -9};
        mergeSort(a, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 == o2) return 0;
                else if (o1 > o2) return 1;
                else return -1;
            }
        });
        System.out.println(Arrays.toString(a));
    }

    public static <K> void mergeSort(K[] array, Comparator<K> comp) {
        int n = array.length;
        if (n < 2) return;
        //divide
        int mid = n / 2;
        K[] array1 = Arrays.copyOfRange(array, 0, mid);
        K[] array2 = Arrays.copyOfRange(array, mid, n);
        //conquer
        mergeSort(array1, comp);
        mergeSort(array2, comp);
        //merge results
        merge(array1, array2, array, comp);
    }

    public static <K> K[] merge(K[] array1, K[] array2, K[] array, Comparator<K> comp) {
        int i = 0, j = 0; // i is the index of array1, j is the index of array2
        while (i + j < array.length) {
            //1. j已經是最大值了 2. i不是最大值且array1[i] < array2[j]
            if (j == array2.length || (i < array1.length && comp.compare(array1[i], array2[j]) < 0))
                array[i + j] = array1[i++]; // copy ith element of array1 and increment i
            else
                array[i + j] = array2[j++]; // copy Jth element of array2 and increment i
        }
        return array;
    }
}
