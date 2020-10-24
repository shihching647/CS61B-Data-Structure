package textbook.fifth_edition.ch3_3;

public class DNode {
    private String element;
    private DNode next;
    private DNode prev;

    public DNode(String element, DNode prev, DNode next){
        this.element = element;
        this.prev = prev;
        this.next = next;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public DNode getNext() {
        return next;
    }

    public void setNext(DNode next) {
        this.next = next;
    }

    public DNode getPrev() {
        return prev;
    }

    public void setPrev(DNode prev) {
        this.prev = prev;
    }
}
