package textbook.sixth_edition.ch10_map;

import textbook.sixth_edition.ch9_priorityQueue.Entry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class SortedTableMap<K,V> extends AbstractSortedMap<K,V> {

    private ArrayList<MapEntry<K,V>> table = new ArrayList<>();
    public SortedTableMap() { super();}
    public SortedTableMap(Comparator<K> comparator) { super(comparator);}

    public int size() { return table.size();}

    public V get(K key) {
        int index = findIndex(key);
        if(index == size() || compare(key, table.get(index)) != 0) return null; // no match
        return table.get(index).getValue();
    }

    public V put(K key, V value) {
        int index = findIndex(key);
        if(index < size() && compare(key, table.get(index)) == 0)  // match exists
            return table.get(index).setValue(value);
        table.add(index, new MapEntry<>(key, value));
        return null;
    }

    public V remove(K key) {
        int index = findIndex(key);
        if(index < size() && compare(key, table.get(index)) == 0)
            return table.remove(index).getValue();
        return null;
    }

    /** Returns the smallest index for range table[low..high] inclusive storing an entry with
    a key greater than or equal to k (or else index high+1, by convention). */
    private int findIndex(K key, int low, int high) {
        if(high < low) return high + 1;
        int mid =  (high + low) / 2;
        int comp = compare(table.get(mid), key);
        if(comp == 0) {
            return mid;
        } else if(comp > 0) {
            return findIndex(key, low, mid - 1);
        } else {
            return findIndex(key, mid + 1, high);
        }
    }
    /** Version of findIndex that searches the entire table */
    private int findIndex(K key) { return findIndex(key, 0, table.size() - 1);}

    /** Returns the entry having the least key (or null if map is empty). */
    public Entry<K,V> firstEntry() {
        return safeEntry(0);
    }
    /** Returns the entry having the greatest key (or null if map is empty). */
    public Entry<K,V> lastEntry() {
        return safeEntry(size() - 1);
    }
    /** Returns the entry with least key greater than or equal to given key (if any). */
    public Entry<K,V> ceilingEntry(K key) {
        return safeEntry(findIndex(key));
    }
    /** Returns the entry with greatest key less than or equal to given key (if any). */
    public Entry<K,V> floorEntry(K key) {
        int index = findIndex(key);
        if(index == size() || !key.equals(table.get(index).getKey())) //no match
            index--;
        return safeEntry(index);
    }
    /** Returns the entry with greatest key strictly less than given key (if any). */
    public Entry<K,V> lowerEntry(K key) {
        return safeEntry(findIndex(key) - 1);
    }
    /** Returns the entry with least key strictly greater than given key (if any). */
    public Entry<K,V> higherEntry(K key) {
        int index = findIndex(key);
        if(index < size() && compare(key, table.get(index)) == 0)
            index++;
        return safeEntry(index);
    }

    /** Utility returns the entry at index , or else null if index is out of bounds. */
    private Entry<K,V> safeEntry(int index) {
        if(index >= 0 && index < size()) return table.get(index);
        return null;
    }

    public Iterable<Entry<K,V>> entrySet() {
        return snapshot(0, null);
    }

    public Iterable<Entry<K,V>> subMap(K fromKey, K toKey) {
        return snapshot(findIndex(fromKey), toKey);
    }

    // support for snapshot iterators for entrySet() and subMap() follow
    private Iterable<Entry<K,V>> snapshot(int startIndex, K stop) {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        int i = startIndex;
        while (i < size() && (stop == null || compare(stop, table.get(i)) > 0)) {
            buffer.add(table.get(i));
            i++;
        }
        return buffer;
    }
}
