package homework.hw8;/* ListSorts.java */

import homework.hw8.list.*;

public class ListSorts {

  private final static int SORTSIZE = 10000000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
    LinkedQueue queue = new LinkedQueue();
    try {
      while (!q.isEmpty()) {
        LinkedQueue eachQueue = new LinkedQueue();
        eachQueue.enqueue(q.dequeue());
        queue.enqueue(eachQueue);
      }
    } catch (QueueEmptyException ex) {
      ex.printStackTrace();
    }
    return queue;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
    LinkedQueue result = new LinkedQueue();
    try {
      while (!q1.isEmpty() && !q2.isEmpty()) {
        Comparable e1 = (Comparable) q1.front();
        Comparable e2 = (Comparable) q2.front();
        if (e1.compareTo(e2) < 0) {
          result.enqueue(q1.dequeue());
        } else {
          result.enqueue(q2.dequeue());
        }
      }
      while (!q1.isEmpty()) {
        result.enqueue(q1.dequeue());
      }
      while (!q2.isEmpty()) {
        result.enqueue(q2.dequeue());
      }
    } catch (QueueEmptyException ex) {
      ex.printStackTrace();
    }
    return result;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    // Your solution here.
    try {
      while (!qIn.isEmpty()) {
        Comparable e = (Comparable) qIn.dequeue();
        if (e.compareTo(pivot) < 0) {
          qSmall.enqueue(e);
        } else if (e.compareTo(pivot) > 0) {
          qLarge.enqueue(e);
        } else {
          qEquals.enqueue(e);
        }
      }
    } catch (QueueEmptyException ex) {
      ex.printStackTrace();
    }
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    // Your solution here.
    if (q.isEmpty() || q.size() == 1)
      return; //already sorted
    try {
      LinkedQueue queueOfQueue = makeQueueOfQueues(q); //O(n)
      while (queueOfQueue.size() != 1) { //O(nlogn)
        LinkedQueue q1 = (LinkedQueue) queueOfQueue.dequeue();
        LinkedQueue q2 = (LinkedQueue) queueOfQueue.dequeue();
        queueOfQueue.enqueue(mergeSortedQueues(q1, q2));
      }
      q.append((LinkedQueue) queueOfQueue.dequeue()); //O(1)
    } catch (QueueEmptyException ex) {
      ex.printStackTrace();
    }
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    // Your solution here.
    if (q.isEmpty() || q.size() == 1)
      return; //already sorted

    int pivotIndex = (int)(Math.random() * q.size()) + 1;
    Comparable pivot = (Comparable) q.nth(pivotIndex);
    LinkedQueue qSmall = new LinkedQueue();
    LinkedQueue qEquals = new LinkedQueue();
    LinkedQueue qLarge = new LinkedQueue();
    partition(q, pivot, qSmall, qEquals, qLarge); //O(n)
    //recursively call quickSort
    quickSort(qSmall);
    quickSort(qLarge);
    //merge results
    q.append(qSmall);
    q.append(qEquals);
    q.append(qLarge);
  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {

    System.out.println("--Test for merge sort--");
    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    System.out.println("--Test for quick sort--");
    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());

    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

  }

}
