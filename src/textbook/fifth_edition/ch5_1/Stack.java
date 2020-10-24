package textbook.fifth_edition.ch5_1;

/**
 * Interface for a stack: a collection of objects that are inserted
 * and removed according to the last-in first-out principle. This
 * interface includes the main methods of java.util.Stack
 * @see EmptyStackException
 */

public interface Stack<E> {
    /**
     * Return the number of elements in the stack.
     * @return number of elements in the stack.
     */
    public int size();

    /**
     * Return whether the stack is empty.
     * @return true if the stack is empty, false otherwise.
     */
    public boolean isEmpty();

    /**
     * Insert an element at the top of the stack.
     * @param element element to be inserted.
     */
    public void push(E element);

    /**
     * Inspect the element at the top of the stack.
     * @return top element in the stack.
     * @throws EmptyStackException if the stack is empty.
     */
    public E top() throws EmptyStackException;

    /**
     * Remove the top element form the stack.
     * @return element removed.
     * @throws EmptyStackException if the stack is empty.
     */
    public E pop() throws EmptyStackException;

}
