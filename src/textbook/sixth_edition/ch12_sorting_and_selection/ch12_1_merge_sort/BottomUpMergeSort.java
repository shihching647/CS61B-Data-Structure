package textbook.sixth_edition.ch12_sorting_and_selection.ch12_1_merge_sort;

import textbook.sixth_edition.ch12_sorting_and_selection.MyCompatator;

import java.util.Arrays;
import java.util.Comparator;

public class BottomUpMergeSort {
    public static void main(String[] args) {
        Integer[] a = {2, 4, 6, 6, 0, 9, -1, -9};
        mergeSortBottomUp(a, new MyCompatator());
        System.out.println(Arrays.toString(a));
    }

    public static <K> void mergeSortBottomUp(K[] orig, Comparator<K> comp) {
        int n = orig.length;
        K[] src = orig;
        K[] dest = (K[]) new Object[n];
        K[] temp;

        for (int i = 1; i < n; i *= 2) { //run O(long)
            for (int j = 0; j < n; j += 2 * i) { //run O(n)
                merge(src, dest, comp, j, i);
            }
            //swap
            temp = src;
            src = dest;
            dest = temp;
        }

        // additional copy to get result to original
        if (orig != src) {
            System.arraycopy(src, 0, orig, 0, n);
        }
    }

    /** Merges in[start..start+inc−1] and in[start+inc..start+2*inc−1] into out. */
    public static <K> void merge(K[] in, K[] out, Comparator<K> comp, int start, int increment) {
        int end1 = Math.min(start + increment, in.length);
        int end2 = Math.min(start + 2 * increment, in.length);
        int x = start; //index for start..start+increment−1
        int y = start + increment; //index for start+inc..start+2*increment−1
        int z = start; //index for out

        while (x < end1 && y < end2) {
            if (comp.compare(in[x], in[y]) < 0)
                out[z++] = in[x++];
            else
                out[z++] = in[y++];
        }

        // copy the rest of elements
        if (x < end1)
            System.arraycopy(in, x, out, z, end1 - x);
        else if (y < end2)
            System.arraycopy(in, y, out, z, end2 - y);
    }

}
