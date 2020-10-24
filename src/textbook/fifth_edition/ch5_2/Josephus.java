package textbook.fifth_edition.ch5_2;

/**
 * Josephus Problem
 * 1.The solution runs in O(nk) time. (There are other solutions are faster than this.)
 * 2.For Josephus Problem k=1
 */
public class Josephus {

    public static <E> E Josephus(Queue<E> queue, int k) {
        if(queue.isEmpty()) return null;
        while(queue.size() > 1) {
//            System.out.println("Queue: " + queue + " k = " + k);
            for(int i = 0; i < k; i++) {
                queue.enqueue(queue.dequeue());
            }
            E element = queue.dequeue();
//            System.out.println(element + " is killed.");
        }
        return queue.front();
    }

    public static <E> Queue<E> buildQueue(E[] array) {
        Queue<E> queue = new NodeQueue<>();
        for(int i = 0; i < array.length; i++) {
            queue.enqueue(array[i]);
        }
        return queue;
    }

    public static void main(String[] args) {
        for(int i = 0; i < 42; i++) {
            Integer[] array = new Integer[i+1];
            for(int j = 0; j < array.length; j++) {
                array[j] = j + 1;
            }
            System.out.println("N = " + (i + 1) +" Winner is: " + Josephus(buildQueue(array), 1));
        }

    }
}
