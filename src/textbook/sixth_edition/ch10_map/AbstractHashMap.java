package textbook.sixth_edition.ch10_map;

import textbook.sixth_edition.ch9_priorityQueue.Entry;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractHashMap<K,V> extends AbstractMap<K,V>{
    protected int n;            // number of entries in the dictionary
    protected int capacity;     // length of the table
    private int prime;          // prime factor
    private long scale, shift;  // the shift and scaling factors

    //Constructors
    public AbstractHashMap(int capacity, int prime) {
        this.prime = prime;
        this.capacity = capacity;
        Random rand = new Random();
        scale = rand.nextInt(prime - 1) + 1;
        shift = rand.nextInt(prime);
        createTable();
    }

    public AbstractHashMap(int capacity) {this(capacity, 109345121);} //default prime
    public AbstractHashMap() {this(17);}                            //default capacity

    // public methods
    public int size() { return n;}
    public V get(K key) { return bucketGet(hashValue(key), key);}
    public V put(K key, V value) {
        V result = bucketPut(hashValue(key), key, value);
        if(n > capacity / 2)                    // keep load factor <= 0.5
            resize(2 * capacity - 1);  // (or find a nearby prime)
        return result;
    }
    public V remove(K key) { return bucketRemove(hashValue(key), key);}

    // private utilities
    private int hashValue(K key) {
        return (int)(Math.abs(key.hashCode() * scale + shift) % prime % capacity);
    }
    private void resize(int capacity) {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        for(Entry entry : entrySet()) {
            buffer.add(entry);
        }
        this.capacity = capacity;
        createTable();
        n = 0;
        for(Entry<K,V> entry : buffer) {
            put(entry.getKey(), entry.getValue());
        }
    }

    // protected abstract methods to be implemented by subclasses
    protected abstract void createTable();
    protected abstract V bucketGet(int h, K key);
    protected abstract V bucketPut(int h, K key, V value);
    protected abstract V bucketRemove(int h, K key);

}
