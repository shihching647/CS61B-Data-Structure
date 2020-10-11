package textbook.ch8_3_completeBinaryTree_and_Heap;

import textbook.ch6_2_ch_6_3_nodeList.Position;
import textbook.ch8_2_listPriorityQueue.DefaultComparator;
import textbook.ch8_2_listPriorityQueue.EmptyPriorityQueueException;
import textbook.ch8_2_listPriorityQueue.InvalidKeyException;
import textbook.ch8_2_listPriorityQueue.PriorityQueue;
import textbook.ch9_2_HashTableMap.Entry;

import java.util.Comparator;

/**
 * Realization of priority queue by means of a heap. A complete
 * binary tree realized by  means of an array list is used to
 * represent the heap.
 */
public class HeapPriorityQueue<K,V> implements PriorityQueue<K,V> {
    //Inner class for heap entries.
    protected class MyEntry<K,V> implements Entry<K,V> {
        protected K key;
        protected V value;
        public MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public K getKey() {
            return key;
        }
        @Override
        public V getValue() {
            return value;
        }
        public String toString() {
            return "(" + key + "," + value + ")";
        }
    }
    protected CompleteBinaryTree<Entry<K,V>> heap; //underlying heap
    protected Comparator<K> comparator; //comparator for the keys

    public HeapPriorityQueue() {
        heap = new ArrayListCompleteBinaryTree<>();
        comparator = new DefaultComparator<>();
    }

    public HeapPriorityQueue(Comparator<K> comparator) {
        heap = new ArrayListCompleteBinaryTree<>();
        this.comparator = comparator;
    }
    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public Entry<K, V> min() throws EmptyPriorityQueueException {
        if(isEmpty()) {
            throw new EmptyPriorityQueueException("Priority queue is empty.");
        }
        return heap.root().element();
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
        checkKey(key);
        Entry<K,V> entry = new MyEntry<>(key, value);
        upHeap(heap.add(entry));
        return entry;
    }

    @Override
    public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
        if(isEmpty()) {
            throw new EmptyPriorityQueueException("Priority queue is empty.");
        }
        Entry<K,V> min = heap.root().element();
        if(size() == 1) {
            heap.remove();
        } else {
            heap.replace(heap.root(), heap.remove());
            downHeap(heap.root());
        }
        return min;
    }

    // Determine whether the key is valid.
    private void checkKey(K key) throws InvalidKeyException{
        try {
            comparator.compare(key, key);
        } catch(Exception e) {
            throw new InvalidKeyException("Invalid key");
        }
    }

    // Performs up-heap bubbling
    private void upHeap(Position<Entry<K,V>> node) {
        Position<Entry<K,V>> endNode;
        while(!heap.isRoot(node)) {
            endNode = heap.parent(node);
            if(comparator.compare(node.element().getKey(), endNode.element().getKey()) > 0) //child's key > parent's key -> break
                break;
            swap(node, endNode);
            node = endNode;
        }
    }

    // Performs down-heap bubbling
    private void downHeap(Position<Entry<K,V>> node) {
        while(!heap.isExternal(node)) {
            Position<Entry<K,V>> endNode; //the position of the smaller child
            //決定要跟哪個child交換
            if(!heap.hasRight(node)) { //只有left child
                endNode = heap.left(node);
            } else if(comparator.compare(heap.left(node).element().getKey(), heap.right(node).element().getKey()) > 0) { //left child > right child
                endNode = heap.right(node);
            } else {
                endNode = heap.left(node);
            }
            //檢查是否交換
            if(comparator.compare(node.element().getKey(), endNode.element().getKey()) > 0) {
                swap(node, endNode);
                node = endNode;
            } else {
                break;
            }
        }
    }

    // Swaps the entries of the two given positions.
    private void swap(Position<Entry<K,V>> node1, Position<Entry<K,V>> node2) {
        Entry<K,V> temp = node2.element();
        heap.replace(node2, node1.element());
        heap.replace(node1, temp);
    }
}
