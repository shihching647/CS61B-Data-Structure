package textbook.sixth_edition.ch10_map;

import textbook.sixth_edition.ch9_priorityQueue.Entry;

import java.util.ArrayList;

public class ChainHashMap<K,V> extends AbstractHashMap<K,V> {
    // a fixed capacity array of UnsortedTableMap that serve as buckets
    private UnsortedTableMap<K,V>[] table;

    public ChainHashMap() { super();}
    public ChainHashMap(int capacity) { super(capacity);}
    public ChainHashMap(int capacity, int prime) {super(capacity, prime);}

    /** Creates an empty table having length equal to current capacity. */
    protected void createTable() {
        table = (UnsortedTableMap<K, V>[]) new UnsortedTableMap[capacity];
    }

    /** Returns value associated with key k in bucket with hash value h, or else null. */
    protected V bucketGet(int h, K key) {
        if(table[h] == null) return null;
        return table[h].get(key);
    }

    /** Associates key k with value v in bucket with hash value h; returns old value. */
    protected V bucketPut(int h, K key, V value) {
        int oldSize = size();
        UnsortedTableMap<K,V> bucket = table[h];
        if(bucket == null) {
            bucket = table[h] = new UnsortedTableMap<>();
        }
        V result = bucket.put(key, value);
        n += (bucket.size() - oldSize);  // size may have increased
        return result;
    }

    /** Removes entry having key k from bucket with hash value h (if any). */
    protected V bucketRemove(int h, K key) {
        int oldSize = size();
        UnsortedTableMap<K,V> bucket = table[h];
        if(bucket == null) return null;
        V result = bucket.remove(key);
        n -= (oldSize - bucket.size());  // size may have decreased
        return result;
    }

    /** Returns an iterable collection of all key-value entries of the map. */
    public Iterable<Entry<K,V>> entrySet() {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        for(int i = 0; i < table.length; i++) {
            if(table[i] != null) {
                for(Entry<K,V> entry : table[i].entrySet()) {
                    buffer.add(entry);
                }
            }
        }
        return buffer;
    }
}
