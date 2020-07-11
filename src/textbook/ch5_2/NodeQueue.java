package textbook.ch5_2;

/**
 * 使用Singly-linked list 實現Queue資料結構
 * 1. 所有方法時間複雜度均為O(1)
 * 2. 當size=0, head = tail = null; size = 1時, head = tail = node;
 */
public class NodeQueue<E> implements Queue<E> {
    protected Node<E> head;
    protected Node<E> tail;
    protected int size;

    public NodeQueue() {
        head = null;
        tail = null;
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
    public E front() throws EmptyQueueException {
        if (isEmpty()) throw new EmptyQueueException("The queue is empty.");
        return head.getElement();
    }

    @Override
    public void enqueue(E element) {
        Node<E> node = new Node<>(element, null);
        if (size == 0) {
            head = node;
        } else {
            tail.setNext(node);
        }
        tail = node;
        size++;
    }

    @Override
    public E dequeue() throws EmptyQueueException {
        if (size == 0) throw new EmptyQueueException("The queue is empty.");
        Node<E> temp = head;
        head = head.getNext(); //if size==1, head.getNext() == null;
        size--;
        if (size() == 0) tail = null;
        return temp.getElement();
    }

    @Override
    public String toString() {
        String str = "[";
        Node<E> curNode = head;
        if (size > 0) str += curNode.getElement().toString();
        if (size > 1) {
            curNode = curNode.getNext();
            for (int i = 1; i < size; i++) {
                str += (", " + curNode.getElement().toString());
                curNode = curNode.getNext();
            }
        }
        return str + "]";
    }

    public void status(String op, Object ob) {
        if (ob == null) {
            System.out.println(op + "\t" + ob + "\t" + toString());
        } else {
            System.out.println(op + "\t" + ob + "\t\t" + toString());
        }
    }

    public static void main(String[] args) {
        Object ob = new Object();
        NodeQueue<Integer> queue = new NodeQueue<>();
        System.out.println("Operation\tOutput\tfront <- Q <- rear");
        queue.enqueue(5);
        queue.status("enqueue(5)", null);
        queue.enqueue(3);
        queue.status("enqueue(3)", null);
        ob = queue.dequeue();
        queue.status("dequeue()", ob);
        queue.enqueue(7);
        queue.status("enqueue(7)", null);
        ob = queue.dequeue();
        queue.status("dequeue()", ob);
        ob = queue.front();
        queue.status("front()", ob);
        ob = queue.dequeue();
        queue.status("dequeue()", ob);
        ob = queue.isEmpty();
        queue.status("isEmpty()", ob);
        queue.enqueue(9);
        queue.status("enqueue(9)", null);
        ob = queue.size();
        queue.status("size()", ob);
        ob = queue.isEmpty();
        queue.status("isEmpty()", ob);
    }
}
