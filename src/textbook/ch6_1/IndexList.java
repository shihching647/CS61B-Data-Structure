package textbook.ch6_1;

public interface IndexList<E> {
    /**Returns the number of elements in this list.*/
    public int size();
    /**Returns whether the list is empty.*/
    public boolean isEmpty();
    /**Returns the element at index i, without removing it.*/
    public E get(int i) throws IndexOutOfBoundsException;
    /**Inserts an element e to be at index i, shifting all elements after this.*/
    public void add(int i, E e) throws IndexOutOfBoundsException;
    /**Removes and returns the element at index i, , shifting all elements after this.*/
    public E remove(int i) throws IndexOutOfBoundsException;
    /**Replaces the element at index i with element e, returning the previous element at i.*/
    public E set(int i, E e) throws IndexOutOfBoundsException;
}
