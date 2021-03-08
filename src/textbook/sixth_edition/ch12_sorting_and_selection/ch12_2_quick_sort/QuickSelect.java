package textbook.sixth_edition.ch12_sorting_and_selection.ch12_2_quick_sort;

import java.util.Arrays;
import java.util.Random;

public class QuickSelect {

    public static void main(String[] args) {
        Random random = new Random();
        // Test case: test for the all same elements
        Integer[] a = new Integer[100];
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(100);
        }
        System.out.println(quickSelect(a, 0, a.length - 1, 1));
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
    }

    // Lecture31 version
    //使用quick select找出陣列中第k小的數
    public static Comparable quickSelect(Comparable[] array, int low, int high, int k) {
        if (k <= 0 || k > array.length) {
            throw new IllegalArgumentException("k值超過陣列範圍!");
        }
        int index = k - 1;
        int n = array.length;
        if (n < 2) return array[0];
        if (low < high) {
            int pivotIndex = low + (int) (Math.random() * (high - low));
            Comparable pivot = array[pivotIndex];
            array[pivotIndex] = array[high];
            array[high] = pivot;

            int i = low - 1;
            int j = high;
            do {
                do {i++;} while (array[i].compareTo(pivot) < 0);
                do {j--;} while (array[j].compareTo(pivot) > 0 && j > low);
                if (i < j) {
                    Comparable temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            } while (i < j);

            array[high] = array[i];
            array[i] = pivot;
            if (index < i) {
                return quickSelect(array, low, i - 1, k);
            } else if (index < i + 1) {
                return array[i];
            } else {
                return quickSelect(array, i + 1, high, k);
            }
        }
        return array[high]; //只會發生在最後一層, 長度為2的時候, 此時low = high
    }
}
