package textbook.sixth_edition.ch10_map;

import textbook.sixth_edition.ch9_priorityQueue.Entry;

import java.util.ArrayList;

public class ProbeHashMap<K,V> extends AbstractHashMap<K,V> {

    private MapEntry<K,V>[] table;
    private MapEntry<K,V> DEFUNCT = new MapEntry<>(null, null);

    public ProbeHashMap() { super();}
    public ProbeHashMap(int capacity) { super(capacity);}
    public ProbeHashMap(int capacity, int prime) { super(capacity, prime);}

    protected void createTable() {
        table = (MapEntry<K,V>[]) new MapEntry[capacity];
    }

    protected V bucketGet(int h, K key) {
        int index = findSlot(h, key);
        if(index < 0) return null;
        return table[index].getValue();
    }

    protected V bucketPut(int h, K key, V value) {
        int index = findSlot(h, key);
        if(index > 0) return table[h].setValue(value);
        table[-index - 1] = new MapEntry<>(key, value);
        n++;
        return null;
    }

    protected V bucketRemove(int h, K key) {
        int index = findSlot(h, key);
        if(index < 0) return null;
        V result = table[index].getValue();
        table[index] = DEFUNCT;
        n--;
        return result;
    }

    public Iterable<Entry<K,V>> entrySet() {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        for(int i = 0; i < capacity; i++) {
            if(!isAvailable(i))
                buffer.add(table[i]);
        }
        return buffer;
    }

    private int findSlot(int h, K key) {
        int avail = -1;
        int j = h;
        do {
            if(isAvailable(j)) {
                if(avail == -1) avail = j;
                if(table[j] == null) break;
            } else if(table[j].getKey().equals(key)){
                return j;
            }
            j = (j + 1) % capacity;
        } while( j != h);
        return -(avail + 1);
    }

    private boolean isAvailable(int index) {
        return table[index] == null || table[index] == DEFUNCT;
    }

}
