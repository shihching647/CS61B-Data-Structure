package textbook.ch6_2_ch_6_3_nodeList;

public class DNode<E> implements Position<E>{
    private DNode<E> prev, next;
    private E element;

    public DNode(DNode<E> prev, DNode<E> next, E element) {
        this.prev = prev;
        this.next = next;
        this.element = element;
    }

    @Override
    public E element() throws InvalidPositionException{
        //when a node is linked out
        if(prev == null && next == null) {
            throw new InvalidPositionException("Position is not in a list.");
        }
        return element;
    }

    public DNode<E> getPrev() {
        return prev;
    }

    public void setPrev(DNode<E> prev) {
        this.prev = prev;
    }

    public DNode<E> getNext() {
        return next;
    }

    public void setNext(DNode<E> next) {
        this.next = next;
    }

    public void setElement(E element) {
        this.element = element;
    }
}
