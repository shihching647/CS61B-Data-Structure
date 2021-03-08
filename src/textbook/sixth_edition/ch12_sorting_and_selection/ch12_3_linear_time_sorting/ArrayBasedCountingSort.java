package textbook.sixth_edition.ch12_sorting_and_selection.ch12_3_linear_time_sorting;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
/**
 * counting sort (good for array)
 * Time complexity : O(q + n)
 * q is the range of input integer
 * n is the number of input integer
 */
public class ArrayBasedCountingSort {

    public static void main(String[] args) {
        Map.Entry<Integer,Character> entry1 = new AbstractMap.SimpleEntry<>(6, 'a');
        Map.Entry<Integer,Character> entry2 = new AbstractMap.SimpleEntry<>(7, 'b');
        Map.Entry<Integer,Character> entry3 = new AbstractMap.SimpleEntry<>(3, 'c');
        Map.Entry<Integer,Character> entry4 = new AbstractMap.SimpleEntry<>(0, 'd');
        Map.Entry<Integer,Character> entry5 = new AbstractMap.SimpleEntry<>(3, 'e');
        Map.Entry<Integer,Character> entry6 = new AbstractMap.SimpleEntry<>(1, 'f');
        Map.Entry<Integer,Character> entry7 = new AbstractMap.SimpleEntry<>(5, 'g');
        Map.Entry<Integer,Character> entry8 = new AbstractMap.SimpleEntry<>(0, 'h');
        Map.Entry<Integer,Character> entry9 = new AbstractMap.SimpleEntry<>(3, 'i');
        Map.Entry<Integer,Character> entry10 = new AbstractMap.SimpleEntry<>(7, 'j');
        Map.Entry<Integer,Character>[] array = new Map.Entry[] {entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9, entry10};
        System.out.println(Arrays.toString(countingSort(array, 0, 10)));
    }

    public static <V> Map.Entry<Integer,V>[] countingSort(Map.Entry<Integer,V>[] array, int min, int max) {
        int[] counts = new int[max - min + 1];

        for (int i = 0; i < array.length; i++) {
            counts[array[i].getKey()]++;
        }
        //此時counts[i]為每一個key的數量



        int total = 0;
        for (int i = 0; i < counts.length; i++) {
            int c = counts[i];
            counts[i] = total;
            total += c;
        }
        //此時counts[i]為每一個key放到result[]的開始位置



        Map.Entry<Integer,V>[] result = new Map.Entry[array.length];
        for (int i = 0; i < array.length; i++) {
            result[counts[array[i].getKey()]] = array[i];
            counts[array[i].getKey()]++;
        }
        return result;
    }
}
