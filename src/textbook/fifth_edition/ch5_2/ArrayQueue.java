package textbook.fifth_edition.ch5_2;

/**
 * 使用array實現queue資料結構:
 * 1.所有方法時間複雜度均為O(1)
 * 2.使用frontIndex, rearIndex存頭尾的index, 若只存頭, 會造成dequeue()時間複雜度為O(n).
 * 3.queue array只能存入capacity - 1個元素(避免frontIndex==rearIndex分不出是空的或滿的)
 * 4."wrap" indices around the end of the array.(使超過的index可以重新回到一開始)
 * 5.The space usage is O(capacity). (一開始時就決定, 與存幾個元素無關)
 *
 */
public class ArrayQueue<E> implements Queue<E>{
    public static final int CAPACITY = 1000; // default array capacity
    protected int capacity; // The actual capacity of the queue array
    protected E[] queue;    // Generic array used to implement the queue
    protected int frontIndex; // Index for the front of queue
    protected int rearIndex;  // Index for the rear of queue

    public ArrayQueue() {
        this(CAPACITY);
    }

    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        queue = (E[]) new Object[capacity];
        frontIndex = rearIndex = 0;
    }

    @Override
    public int size() {
        return (capacity - frontIndex + rearIndex) % capacity;
    }

    @Override
    public boolean isEmpty() {
        return (frontIndex == rearIndex);
    }

    @Override
    public E front() throws EmptyQueueException {
        if(isEmpty()) throw new EmptyQueueException("The queue is empty.");
        return queue[frontIndex];
    }

    @Override
    public void enqueue(E element) throws FullQueueException{
        if(size() == capacity - 1) throw new FullQueueException("The queue is full.");
        queue[rearIndex] = element;
        rearIndex = (rearIndex + 1) % capacity;
    }

    @Override
    public E dequeue() throws EmptyQueueException {
        if(isEmpty()) throw new EmptyQueueException("The queue is empty.");
        E temp = queue[frontIndex];
        queue[frontIndex] = null;
        frontIndex = (frontIndex + 1) % capacity;
        return temp;
    }

    @Override
    public String toString() {
        int index = frontIndex;
        String str = "[";
        if(size() > 0) str += queue[index].toString();
        if(size() > 1) {
            index++;
            while (index != rearIndex) {
                str += (", " + queue[index].toString());
                index = (index + 1) % capacity;
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
        Object ob = new Object();
        ArrayQueue<Integer> queue = new ArrayQueue<>(3);
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
