package textbook.ch5_3;

public class DNode<E> {
    private E element;
    private DNode<E> next;
    private DNode<E> prev;

    public DNode(E element, DNode prev, DNode next){
        this.element = element;
        this.prev = prev;
        this.next = next;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public DNode<E> getNext() {
        return next;
    }

    public void setNext(DNode next) {
        this.next = next;
    }

    public DNode<E> getPrev() {
        return prev;
    }

    public void setPrev(DNode prev) {
        this.prev = prev;
    }
}
