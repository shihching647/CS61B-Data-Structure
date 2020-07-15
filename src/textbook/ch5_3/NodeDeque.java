package textbook.ch5_3;

/**
 * 使用Doubly-linked Node 實現 Deque資料結構
 * 1. 所有方法時間複雜度均為O(1)
 *
 */
public class NodeDeque<E> implements Deque<E> {
    protected DNode<E> header;  //sentinel
    protected DNode<E> trailer; //sentinel
    protected int size;

    public NodeDeque() {
        header = new DNode<>(null, null, null); //此時trailer為null, 故不能指定到next
        trailer = new DNode<>(null, header, null);
        header.setNext(trailer);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E getFrist() throws EmptyDequeException {
        if(size == 0) throw new EmptyDequeException("The deque is empty.");
        return header.getNext().getElement();
    }

    @Override
    public E getLast() throws EmptyDequeException {
        if(size == 0) throw new EmptyDequeException("The deque is empty.");
        return trailer.getPrev().getElement();
    }

    @Override
    public void addFrist(E element) {
        DNode<E> node = new DNode<>(element, header, header.getNext());
        DNode<E> second = header.getNext();
        header.setNext(node);
        second.setPrev(node);
        size++;
    }

    @Override
    public void addLast(E element) {
        DNode<E> node = new DNode<>(element, trailer.getPrev(), trailer);
        DNode<E> secondLast = trailer.getPrev();
        trailer.setPrev(node);
        secondLast.setNext(node);
        size++;
    }

    @Override
    public E removeFirst() throws EmptyDequeException {
        if(size == 0) throw new EmptyDequeException("The deque is empty.");
        DNode<E> first = header.getNext();
        DNode<E> second = first.getNext();
        header.setNext(second);
        second.setPrev(header);
        size--;
        return first.getElement();
    }

    @Override
    public E removeLast() throws EmptyDequeException {
        if(size == 0) throw new EmptyDequeException("The deque is empty.");
        DNode<E> last = trailer.getPrev();
        DNode<E> secnodLast = last.getPrev();
        trailer.setPrev(secnodLast);
        secnodLast.setNext(trailer);
        size--;
        return last.getElement();
    }

    @Override
    public String toString() {
        String str = "[";
        if(size > 0) str += header.getNext().getElement().toString();
        if(size > 1) {
            DNode<E> curNode = header.getNext().getNext();
            for(int i = 1; i < size; i++) {
                str += (", " + curNode.getElement().toString());
                curNode = curNode.getNext();
            }
        }
        return str + "]";
    }

    public void status(String op, Object ob) {
        if(ob == null) {
            System.out.println(op + "\t" + ob + "\t" + toString());
        } else {
            System.out.println(op + "\t" + ob + "\t\t" + toString());
        }
    }

    public static void main(String[] args) {
        Object ob;
        NodeDeque<Integer> deque = new NodeDeque<>();
        System.out.println("Operation\t\tOutput\tfront <- Q <- rear");
        deque.addFrist(3);
        deque.status("addFrist(3)\t", null);
        deque.addFrist(5);
        deque.status("addFrist(5)\t", null);
        ob = deque.removeFirst();
        deque.status("removeFirst()", ob);
        deque.addLast(7);
        deque.status("addLast()\t", null);
        ob = deque.getFrist();
        deque.status("getFrist()\t", ob);
        ob = deque.removeFirst();
        deque.status("removeFirst()\t", ob);
        ob = deque.removeLast();
        deque.status("removeLast()\t", ob);
        ob = deque.isEmpty();
        deque.status("isEmpty()\t", ob);
        deque.addLast(7);
        deque.status("addLast()\t", null);
        deque.addLast(8);
        deque.status("addLast()\t", null);
        deque.addLast(9);
        deque.status("addLast()\t", null);
        ob = deque.getLast();
        deque.status("getLast()\t", ob);
        ob = deque.isEmpty();
        deque.status("isEmpty()\t", ob);
    }
}
