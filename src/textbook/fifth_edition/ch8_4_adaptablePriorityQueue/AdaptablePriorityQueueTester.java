package textbook.fifth_edition.ch8_4_adaptablePriorityQueue;

import textbook.fifth_edition.ch8_3_completeBinaryTree_and_Heap.PriorityQueueTester;

public class AdaptablePriorityQueueTester extends PriorityQueueTester {
    AdaptablePriorityQueue<Integer, String> adaptablePriorityQueue;

    public AdaptablePriorityQueueTester(AdaptablePriorityQueue<Integer, String> adaptablePriorityQueue) {
        super(adaptablePriorityQueue);
        this.adaptablePriorityQueue = adaptablePriorityQueue;
    }

    public void runAdaptablePriorityQueueTest() {
        adaptablePriorityQueue.insert(10, "J");
        adaptablePriorityQueue.insert(11, "K");
        System.out.println("replaceKey() on min(): " + adaptablePriorityQueue.replaceKey(adaptablePriorityQueue.min(), 100));
        System.out.println("replaceValue() on min(): " + adaptablePriorityQueue.replaceValue(adaptablePriorityQueue.min(), "Z"));
        System.out.println("remove() on min(): " + adaptablePriorityQueue.remove(adaptablePriorityQueue.min()));
        System.out.println("min(): " + adaptablePriorityQueue.min());
    }

    public static void main(String[] args) {
        AdaptablePriorityQueueTester tester = new AdaptablePriorityQueueTester(new SortedAdaptablePriorityQueue<>());
        tester.runPriorityQueueTest();
        tester.runAdaptablePriorityQueueTest();
    }
}
