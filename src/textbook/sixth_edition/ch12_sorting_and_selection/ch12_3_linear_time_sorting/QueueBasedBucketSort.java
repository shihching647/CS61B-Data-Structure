package textbook.sixth_edition.ch12_sorting_and_selection.ch12_3_linear_time_sorting;

import textbook.fifth_edition.ch5_2.ArrayQueue;
import textbook.fifth_edition.ch5_2.Queue;

/**
 * bucket sort (good for linked list or queue)
 * Time complexity : O(q + n)
 * q is the range of input integer
 * n is the number of input integer
 */
public class QueueBasedBucketSort {

    public static void main(String[] args) {
        Queue<Integer> a = new ArrayQueue<>();
        a.enqueue(0);
        a.enqueue(1);
        a.enqueue(2);
        a.enqueue(3);
        a.enqueue(7);
        a.enqueue(3);
        a.enqueue(3);
        a.enqueue(10);
        a.enqueue(0);
        a.enqueue(8);
        bucketSort(a, 0, 10);
        System.out.println(a);
    }

    public static void bucketSort(Queue<Integer> queue, int min, int max) {
        Queue<Integer>[] bucket = new Queue[max - min + 1];
        //take O(q) time
        for (int i = 0; i < bucket.length; i++)
            bucket[i] = new ArrayQueue<>();
        //take O(n) time
        while (!queue.isEmpty()) {
            int num = queue.dequeue();
            bucket[num].enqueue(num);
        }
        //take O(n) time
        for (int i = 0; i < bucket.length; i++) {
            Queue<Integer> q = bucket[i];
            while (!q.isEmpty())
                queue.enqueue(q.dequeue());
        }
    }
}
