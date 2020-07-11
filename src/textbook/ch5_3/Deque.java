package textbook.ch5_3;

/**
 * Interface for a deque: a collection of objects that are inserted
 * and removed at both ends; a subset of java.util.LinkedList methods.
 */

public interface Deque<E> {
    /* Returns the number of elements in the deque*/
    public int size();

    /* Returns true if the deque is empty; false otherwise.*/
    public boolean isEmpty();

    /* Returns the first element; an exception is thrown if deque is empty.*/
    public E getFrist() throws EmptyDequeException;

    /* Returns the last element; an exception is thrown if deque is empty.*/
    public E getLast()throws EmptyDequeException;

    /* Inserts an element to be the first in the deque.*/
    public void addFrist(E element);

    /* Inserts an element to be the last in the deque.*/
    public void addLast(E element);

    /* Removes the first element; an exception is thrown if deque is empty.*/
    public E removeFirst() throws EmptyDequeException;

    /* Removes the last element; an exception is thrown if deque is empty.*/
    public E removeLast() throws EmptyDequeException;
}
