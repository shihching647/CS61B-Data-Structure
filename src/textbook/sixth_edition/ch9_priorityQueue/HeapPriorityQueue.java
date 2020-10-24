package textbook.sixth_edition.ch9_priorityQueue;

import textbook.fifth_edition.ch6_1.ArrayIndexList;

import java.util.Comparator;

public class HeapPriorityQueue<K,V> extends AbstractPriorityQueue<K,V> {

    protected ArrayIndexList<Entry<K,V>> heap = new ArrayIndexList<>();

    public HeapPriorityQueue(Comparator<K> comparator) { super(comparator); }

    public HeapPriorityQueue() { super(); }
    /** Creates a priority queue initialized with the given key-value pairs. */
    public HeapPriorityQueue(K[] keys, V[] values) {
        super();
        for(int i = 0; i < Math.min(keys.length, values.length); i++) {
            heap.add(i, new PQEntry<>(keys[i], values[i]));
        }
        heapify();
    }

    /** Performs a bottom-up construction of the heap in linear time. */
    private void heapify() {
        int startIndex = parent(size() - 1);  // start at PARENT of last entry
        for(int i = startIndex; i >= 0; i--) {   // loop until processing the root
            downHeap(i);
        }
    }

    // protected utilities
    protected int parent(int j) { return (j - 1) / 2; }
    protected int left(int j) { return 2 * j + 1; }
    protected int right(int j) { return 2 * j + 2; }
    protected boolean hasLeft(int j) { return heap.size() > left(j);}
    protected boolean hasRight(int j) { return heap.size() > right(j);}

    protected void swap(int i, int j) {
        Entry<K,V> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    
    /** Moves the entry at index j higher, if necessary, to restore the heap property. */
    protected void upHeap(int j) {
        while (j > 0) {
            int p = parent(j);
            if(compare(heap.get(j), heap.get(p)) >= 0) {
                break;
            }
            swap(j, p);
            j = p;
        }
    }
    /** Moves the entry at index j lower, if necessary, to restore the heap property. */
    protected void downHeap(int j) {
        while (hasLeft(j)) {
            //Determine which child is the smallest
            int leftIndex = left(j);
            int smallChildIndex = leftIndex;
            if(hasRight(j)) {
                int rightIndex = right(j);
                if(compare(heap.get(rightIndex), heap.get(leftIndex)) < 0) {
                    smallChildIndex = rightIndex;
                }
            }
            //Determine whether changing with parent
            if(compare(heap.get(j), heap.get(smallChildIndex)) <= 0) break;
            swap(j, smallChildIndex);
            j = smallChildIndex;
        }
    }

    // public methods
    public int size() { return heap.size(); }

    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException{
        checkKey(key);
        PQEntry<K,V> entry = new PQEntry<>(key, value);
        heap.add(size(), entry);
        upHeap(size() - 1);
        return entry;
    }

    public Entry<K,V> min() {
        if(heap.isEmpty()) return null;
        return heap.get(0);
    }

    public Entry<K,V> removeMin() {
        if(heap.isEmpty()) return null;
        Entry<K,V> entry = min();
        swap(0, size() - 1);
        heap.remove(size() - 1);
        downHeap(0);
        return entry;
    }
}
