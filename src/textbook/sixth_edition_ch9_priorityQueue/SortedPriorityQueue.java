package textbook.sixth_edition_ch9_priorityQueue;

import textbook.ch6_2_ch_6_3_nodeList.NodePositionList;
import textbook.ch6_2_ch_6_3_nodeList.Position;
import textbook.ch6_2_ch_6_3_nodeList.PositionList;

import java.util.Comparator;

/**
 * Implementing PriorityQueue with sorted list
 * Time complexity:
 * size():      O(1)
 * isEmpty():   O(1)
 * insert():    O(n)
 * min():       O(1)
 * removeMin(): O(1)
 */

public class SortedPriorityQueue<K,V> extends AbstractPriorityQueue<K,V> {

    private PositionList<Entry<K,V>> list = new NodePositionList<>();

    public SortedPriorityQueue() { super(); }

    public SortedPriorityQueue(Comparator<K> comparator) { super(comparator); }

    public int size() { return list.size(); }
    /*
        Our implementation starts at the end of the list, walking backward until the new key is smaller
        than that of an existing entry. The insert method takes O(n) worst-case time.
     */
    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K,V> entry = new PQEntry<>(key, value);
        Position<Entry<K,V>> walk = list.last();
        // walk backward, looking for smaller key
        while(walk != null && compare(entry, walk.element()) < 0) {
            walk = list.prev(walk);
        }
        if(walk == null) {
            list.addFirst(entry); //new key is smallest
        } else {
            list.addAfter(walk, entry); //newest goes after walk
        }
        return entry;
    }

    public Entry<K,V> min() {
        if(list.isEmpty()) return null;
        return list.first().element();
    }

    public Entry<K,V> removeMin() {
        if (list.isEmpty()) return null;
        return list.remove(list.first());
    }

}
