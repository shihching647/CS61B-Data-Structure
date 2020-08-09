package textbook.ch6_2_nodeList;

public interface PositionList<E> {
    public int size();
    public boolean isEmpty();
    /*Returns the fist node in the list.*/
    public Position<E> first();
    public Position<E> last();
    /*Returns the node before a given node in the list.*/
    public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException;
    public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException;
    /*Insert an element at the front of the list.*/
    public void addFirst(E e);
    public void addLast(E e);
    /*Insert an element before a given node in the list.*/
    public void addBefore(Position<E> p, E e) throws InvalidPositionException;
    public void addAfter(Position<E> p, E e) throws InvalidPositionException;
    /*Replaces the element stored at the given node, returning the old element*/
    public E set(Position<E> p, E e) throws InvalidPositionException;
    /*Removes a node from the list, returning the element stored there*/
    public E remove(Position<E> p) throws InvalidPositionException;
}
