package textbook.fifth_edition.ch8_2_listPriorityQueue;

import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.NodePositionList;
import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.Position;
import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.PositionList;
import textbook.fifth_edition.ch9_2_HashTableMap.Entry;

import java.util.Comparator;

public class SortedListPriorityQueue<K,V> implements PriorityQueue<K,V> {
    protected PositionList<Entry<K,V>> entries;
    protected Comparator<K> comparator;
    protected Position<Entry<K,V>> actionPos;  //variable used by subclass

    protected static class MyEntry<K,V> implements Entry<K,V> {
        protected K key;
        protected V value;
        public MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public String toString() {
            return "(" + key + "," + value + ")";
        }
    }

    public SortedListPriorityQueue() {
        entries = new NodePositionList<>();
        comparator = new DefaultComparator<>();
    }

    public SortedListPriorityQueue(Comparator<K> comparator) {
        entries = new NodePositionList<>();
        this.comparator = comparator;
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public Entry<K, V> min() throws EmptyPriorityQueueException {
        if(isEmpty()) {
            throw new EmptyPriorityQueueException("The priority queue is empty.");
        }
        return entries.first().element();
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
        checkKey(key);
        Entry<K,V> entry = new MyEntry<>(key, value);
        insertEntry(entry);
        return entry;
    }

    protected void checkKey(K key) throws InvalidKeyException {
        if(key == null) {
            throw new InvalidKeyException("The key is null.");
        } else if(!(key instanceof Comparable)) {
            throw new InvalidKeyException("The key can't be compared.");
        }
    }

    protected void insertEntry(Entry<K,V> entry) {
        if(isEmpty()) { //空的直接加入第一個
            entries.addLast(entry);
            actionPos = entries.first();
        } else if(comparator.compare(entry.getKey(), entries.last().element().getKey()) > 0) {  //比最後一個還大, 直接加入最後一個
            entries.addLast(entry);
            actionPos = entries.last();
        } else {
            Position<Entry<K,V>> current = entries.first();
            while(comparator.compare(entry.getKey(), current.element().getKey()) > 0) {
                current = entries.next(current);
            }
            entries.addBefore(current, entry);
            actionPos = entries.prev(current);
        }
    }

    @Override
    public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
        if(isEmpty()) {
            throw new EmptyPriorityQueueException("The priority queue is empty.");
        }
        return entries.remove(entries.first());
    }
}
