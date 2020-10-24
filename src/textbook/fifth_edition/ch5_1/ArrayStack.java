package textbook.fifth_edition.ch5_1;

public class ArrayStack<E> implements Stack<E>{
    public static final int CAPACITY = 1000; // default array capacity
    protected int capacity; // The actual capacity of the stack array
    protected E[] stack;    // Generic array used to implement the stack
    protected int top = -1; // index for the top of the stack

    public ArrayStack() {
        this(CAPACITY);
    }

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        stack = (E[]) new Object[CAPACITY];
    }
    @Override
    public int size() {
        return top + 1;
    }

    @Override
    public boolean isEmpty() {
        return top < 0;
    }

    @Override
    public void push(E element) throws FullStackException{
        if(size() == capacity) {
            throw new FullStackException("Stack is full");
        }
        stack[++top] = element;
    }

    @Override
    public E top() throws EmptyStackException {
        if(isEmpty()) {
            throw new EmptyStackException("Stack is empty.");
        }
        return stack[top];
    }

    @Override
    public E pop() throws EmptyStackException {
        if(isEmpty()) {
            throw new EmptyStackException("Stack is empty.");
        }
        E element = stack[top];
        stack[top--] = null;
        return element;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if(size() > 0 ) sb.append(stack[0]);
        if(size() > 1) {
            for(int i = 1; i < size(); i++) {
                sb.append(", " + stack[i]);
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
        ArrayStack<Integer> stack = new ArrayStack<>();
        stack.status("new ArrayStack<Integer> stack", null);
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
