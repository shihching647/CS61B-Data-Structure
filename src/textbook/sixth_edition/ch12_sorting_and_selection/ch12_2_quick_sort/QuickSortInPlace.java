package textbook.sixth_edition.ch12_sorting_and_selection.ch12_2_quick_sort;

import java.util.Arrays;
import java.util.Comparator;

public class QuickSortInPlace {
    static int count1;
    static int count2;
    public static void main(String[] args) {
        Integer[] a = new Integer[1024];
        for (int i = 0; i < a.length; a[i] = 0, i++);
//        quickSortInPlace(a, new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                if (o1 < o2) return -1;
//                else if (o1 > o2) return 1;
//                else return 0;
//            }
//        }, 0, a.length - 1);
        quickSort(a, 0, a.length - 1);
        quickSort(a, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 < o2) return -1;
                else if (o1 > o2) return 1;
                else return 0;
            }
        });
        System.out.println(Arrays.toString(a));
    }

//    public static <K> void quickSortInPlace(K[] array, Comparator<K> comp, int a, int b) {
//        System.out.println(++count1);
//        if (a >= b) return;
//        int left = a;
//        int right = b - 1;
//        K pivot = array[b];
//        K temp;
//
//        while (left <= right) {
//            while (left <= right && comp.compare(array[left], pivot) < 0) left++;
//            while (left <= right && comp.compare(array[right], pivot) > 0) right--;
//            if (left <= right) {
//                //swap
//                temp = array[left];
//                array[left] = array[right];
//                array[right] = temp;
//                left++;
//                right--;
//            }
//        }
//        //swap the element which left index point to with the pivot
//        temp = array[left];
//        array[left] = array[b];
//        array[b] = temp;
//        //make recursive calls
//        quickSortInPlace(array, comp, a, left - 1);
//        quickSortInPlace(array, comp, left + 1, b);
//    }

    public static void quickSort(Comparable[] array, int low, int high) {
        System.out.println(++count1);
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

    public static <E> void quickSort (E[] s, Comparator< E> c) {
        if (s.length < 2) return;
        quickSortStep(s, c, 0, s.length-1);
    }

    private static <E> void quickSortStep ( E [] s, Comparator<E> c, int leftBound, int rightBound ) {
        System.out.println(++count2);
        if (leftBound >= rightBound) return;
        E temp;
        E pivot = s[rightBound];
        int leftlnd = leftBound;
        int rightlnd = rightBound - 1;
        while (leftlnd <= rightlnd){
            while ((leftlnd <=rightlnd) && (c.compare(s[leftlnd],pivot) <= 0) )
                leftlnd++;
            while ((rightlnd >= leftlnd) && (c.compare(s[rightlnd], pivot) >= 0))
                rightlnd --;
            if (leftlnd<rightlnd) {
                temp = s[rightlnd];
                s[rightlnd] = s[leftlnd];
                s[leftlnd] = temp;
            }
        }
        temp = s[rightBound];
        s[rightBound] = s[leftlnd];
        s[leftlnd] = temp;

        quickSortStep(s, c, leftBound, leftlnd - 1);
        quickSortStep (s, c, leftlnd + 1, rightBound);
    }
}
