package textbook.sixth_edition.ch9_priorityQueue;

public class PriorityQueueTester {
    AbstractPriorityQueue<Integer,String> priorityQueue;

    public PriorityQueueTester(AbstractPriorityQueue<Integer,String> priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    public void runTest() {
        priorityQueue.insert(5, "A");
        priorityQueue.insert(9, "C");
        priorityQueue.insert(3, "B");
        priorityQueue.insert(7, "D");
        System.out.println("size() : " + priorityQueue.size());
        System.out.println("min() : " + priorityQueue.min().toString());
        System.out.println("removeMin() : " + priorityQueue.removeMin().toString());
        System.out.println("size() : " + priorityQueue.size());
        System.out.println("removeMin() : " + priorityQueue.removeMin().toString());
        System.out.println("size() : " + priorityQueue.size());
        System.out.println("removeMin() : " + priorityQueue.removeMin().toString());
        System.out.println("size() : " + priorityQueue.size());
        System.out.println("isEmpty() : " + priorityQueue.isEmpty());
        System.out.println("removeMin() : " + priorityQueue.removeMin().toString());
        System.out.println("isEmpty() : " + priorityQueue.isEmpty());
    }

    public static void main(String[] args) {
        PriorityQueueTester tester2 = new PriorityQueueTester(new UnsortedPriorityQueue<>());
        PriorityQueueTester tester1 = new PriorityQueueTester(new SortedPriorityQueue<>());
        PriorityQueueTester tester3 = new PriorityQueueTester(new HeapPriorityQueue<>());
        tester1.runTest();
        System.out.println("-----------------------");
        tester2.runTest();
        System.out.println("-----------------------");
        tester3.runTest();
    }
}
