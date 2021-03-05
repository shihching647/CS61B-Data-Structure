package textbook.sixth_edition.ch12_sorting_and_selection.ch12_1_merge_sort;

import textbook.fifth_edition.ch5_2.ArrayQueue;
import textbook.fifth_edition.ch5_2.Queue;
import textbook.sixth_edition.ch12_sorting_and_selection.MyCompatator;

import java.util.Comparator;

public class QueueBasedMergeSort {

    public static void main(String[] args) {
        Queue<Integer> a = new ArrayQueue<>();
        a.enqueue(0);
        a.enqueue(2);
        a.enqueue(-5);
        a.enqueue(3);
        a.enqueue(7);
        a.enqueue(-1);
        a.enqueue(-1);
        mergeSort(a, new MyCompatator());
        System.out.println(a);
    }

    public static <K> void mergeSort(Queue<K> queue, Comparator<K> comp) {
        int n = queue.size();
        if (n < 2) return;
        //divide
        Queue<K> queue1 = new ArrayQueue<>();
        Queue<K> queue2 = new ArrayQueue<>();
        while (queue1.size() < n / 2) {
            queue1.enqueue(queue.dequeue());
        }
        while (!queue.isEmpty()) {
            queue2.enqueue(queue.dequeue());
        }
        //conquer
        mergeSort(queue1, comp);
        mergeSort(queue2,comp);
        //merge results
        merge(queue1, queue2, queue, comp);
    }

    public static <K> void merge(Queue<K> queue1, Queue<K> queue2, Queue<K> queue, Comparator<K> comp) {

        while(!queue1.isEmpty() && !queue2.isEmpty()) {
            if (comp.compare(queue1.front(), queue2.front()) < 0) {
                queue.enqueue(queue1.dequeue());
            } else
                queue.enqueue(queue2.dequeue());
        }

        while (!queue1.isEmpty())
            queue.enqueue(queue1.dequeue());  // move any elements that remain in queue1
        while (!queue2.isEmpty())
            queue.enqueue(queue2.dequeue());  // move any elements that remain in queue2

    }
}
