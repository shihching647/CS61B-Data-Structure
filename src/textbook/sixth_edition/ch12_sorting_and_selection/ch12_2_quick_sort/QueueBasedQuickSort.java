package textbook.sixth_edition.ch12_sorting_and_selection.ch12_2_quick_sort;

import textbook.fifth_edition.ch5_2.ArrayQueue;
import textbook.fifth_edition.ch5_2.Queue;
import textbook.sixth_edition.ch12_sorting_and_selection.MyCompatator;

import java.util.Comparator;

/**
 * Implementation of the quick-sort algorithm
 * that works on any sequence type that operates as a queue.
 * worst case: run in O(n^2) (for queue is already sorted)
 * best case: run in O(nlogn)
 */
public class QueueBasedQuickSort {

    public static void main(String[] args) {
        Queue<Integer> a = new ArrayQueue<>();
        a.enqueue(0);
        a.enqueue(2);
        a.enqueue(-5);
        a.enqueue(3);
        a.enqueue(7);
        a.enqueue(-1);
        a.enqueue(-1);
        quickSort(a, new MyCompatator());
        System.out.println(a);
    }

    public static <K> void quickSort(Queue<K> queue, Comparator<K> comp) {
        int n = queue.size();
        if (n < 2) return;
        //divide
        K pivot = queue.front();            // using first as arbitrary pivot
        Queue<K> left = new ArrayQueue<>();
        Queue<K> middle = new ArrayQueue<>();
        Queue<K> right = new ArrayQueue<>();

        while (!queue.isEmpty()) {
            K element = queue.dequeue();
            int c = comp.compare(element, pivot);
            if (c > 0) {
                right.enqueue(element);
            } else if (c < 0) {
                left.enqueue(element);
            } else {
                middle.enqueue(element);
            }
        }
        //conquer
        quickSort(left, comp);
        quickSort(right, comp);

        //merge results
        while (!left.isEmpty())
            queue.enqueue(left.dequeue());
        while (!middle.isEmpty())
            queue.enqueue(middle.dequeue());
        while (!right.isEmpty())
            queue.enqueue(right.dequeue());

    }
}
