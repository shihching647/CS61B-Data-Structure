package textbook.sixth_edition.ch9_priorityQueue;

import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.NodePositionList;
import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.Position;
import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.PositionList;

import java.util.Comparator;

/**
 * Implementing PriorityQueue with unsorted list
 * Time complexity:
 * size():      O(1)
 * isEmpty():   O(1)
 * insert():    O(1)
 * min():       O(n)
 * removeMin(): O(n)
 */
public class UnsortedPriorityQueue<K,V> extends AbstractPriorityQueue<K,V> {

    private PositionList<Entry<K,V>> list = new NodePositionList<>();

    public UnsortedPriorityQueue(Comparator<K> comparator) { super(comparator); }

    public UnsortedPriorityQueue() { super(); }

    public int size() {
        return list.size();
    }

    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K,V> entry = new PQEntry<>(key, value);
        list.addLast(entry);
        return entry;
    }

    public Entry<K,V> min() {
        if(list.isEmpty()) { return null; }
        return findMinPos().element();
    }

    public Entry<K,V> removeMin() {
        if(list.isEmpty()) { return null; }
        return list.remove(findMinPos());
    }

    /** Returns the Position of an entry having minimal key. */
    private Position<Entry<K,V>> findMinPos() {
        Position<Entry<K,V>> minPos = list.first();
        for(Position<Entry<K,V>> pos : list.positions()) {
            if(comparator.compare(pos.element().getKey(), minPos.element().getKey()) < 0) {
                minPos = pos;
            }
        }
        return minPos;
    }
 }
