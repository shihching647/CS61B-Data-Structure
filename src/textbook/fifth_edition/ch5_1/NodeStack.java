package textbook.fifth_edition.ch5_1;

/**
 * 使用Singly-linked list 實現Stack資料結構
 */
public class NodeStack<E> implements Stack<E>{
    private Node<E> top;
    private int size;

    public NodeStack() {
        top = null;
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
    public void push(E element) {
        top = new Node<E>(element, top);
        size++;
    }

    @Override
    public E top() throws EmptyStackException {
        if(isEmpty()) {
            throw new EmptyStackException("Stack is empty.");
        }
        return top.getElement();
    }

    @Override
    public E pop() throws EmptyStackException {
        if(isEmpty()) {
            throw new EmptyStackException("Stack is empty.");
        }
        Node<E> temp = top;
        temp.setNext(null);
        top = top.getNext();
        size--;
        return temp.getElement();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if(size() > 0) sb.append(top.getElement().toString());
        if(size() > 1) {
            while(true) {
                top = top.getNext();
                if(top == null) break;
                sb.append(", " + top.getElement().toString());
            }
        }
        return sb.append("]").toString();
    }

    public void status(String op, Object element) {
        System.out.print("-----> " + op);
        System.out.println(", returns " + element); //element is what was returned
        System.out.print("result: size = " + size() + ", isEmpty = " + isEmpty());
        System.out.println(", stack: " + this);
    }

    public static void main(String[] args) {
        Object o;
        NodeStack<Integer> stack = new NodeStack<>();
        stack.status("new NodeStack<Integer> stack", null);
        stack.push(7);
        stack.status("stack.push(7)", null);
        o = stack.pop();
        stack.status("stack.pop(7)", o);
        stack.push(9);
        stack.status("stack.push(9)", null);
        o = stack.top();
        stack.status("stack.top()", o);
    }
}
