package textbook.sixth_edition.ch12_sorting_and_selection.ch12_2_quick_sort;

import textbook.sixth_edition.ch12_sorting_and_selection.MyCompatator;

import java.util.Arrays;
import java.util.Comparator;

public class QuickSortInPlace {
    static int count;
    public static void main(String[] args) {

        // Test case: test for the all same elements
        Integer[] a = new Integer[4096];
        for (int i = 0; i < a.length; i++) {
            a[i] = 0;
        }
        Comparator<Integer> comp = new MyCompatator();

        quickSortInPlace(a, comp, 0, a.length - 1);

//        quickSort(a, 0, a.length - 1);

        System.out.println(Arrays.toString(a));
    }

    //Textbook version
    public static <K> void quickSortInPlace(K[] array, Comparator<K> comp, int a, int b) {
        if (a >= b) return;
        int left = a;
        int right = b - 1;
        K pivot = array[b];  //可改成取(a~b)隨機的數字
        K temp;

        while (left <= right) {
            while (left <= right && comp.compare(array[left], pivot) < 0) { //把第二個條件改成 <= 的話會造成worst case(全部為一樣的元素)的時間複雜度 O(nlogn) -> O(n^2)
//                System.out.println(++count);
                left++;
            }
            while (left <= right && comp.compare(array[right], pivot) > 0) {  //把第二個條件改成 <= 的話會造成worst case(全部為一樣的元素)的時間複雜度 O(nlogn) -> O(n^2)
//                System.out.println(++count);
                right--;
            }
            if (left <= right) {
//                System.out.println(++count);
                //swap
                temp = array[left];
                array[left] = array[right];
                array[right] = temp;
                left++;
                right--;
            }
        }
        //swap the element which left index point to with the pivot
        temp = array[left];
        array[left] = array[b];
        array[b] = temp;
        //make recursive calls
        quickSortInPlace(array, comp, a, left - 1);
        quickSortInPlace(array, comp, left + 1, b);
    }

    // Lecture31 version
    public static void quickSort(Comparable[] array, int low, int high) {
        int n = array.length;
        if (n < 2) return;
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
            quickSort(array, low, i - 1);
            quickSort(array, i + 1, high);
        }
    }

}
