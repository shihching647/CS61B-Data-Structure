package textbook.ch9_2_HashTableMap;

import textbook.ch6_2_ch_6_3_nodeList.NodePositionList;
import textbook.ch6_2_ch_6_3_nodeList.PositionList;

import java.security.InvalidKeyException;
import java.util.Random;
/*
    1.Use built-in hashCode methos and the MAD to compute a key's hash code.
    2.Use a sentinel AVAILABLE as a marker for deactivated entries
    3.Use open addressing method (linear probing) to deal with collision
    4.Use rehash method to keep load factor below 0.5
 */
public class HashTableMap<K,V> implements Map<K,V> {
    public static class HashEntry<K,V> implements Entry<K, V> {
        protected K key;
        protected V value;
        public HashEntry(K key, V value) {
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

        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
        @Override
        public boolean equals(Object o) {
            HashEntry<K,V> entry;
            try{
                entry = (HashEntry<K,V>) o;
            } catch (ClassCastException e) {
                return false;
            }
            return (this.key == entry.getKey() && this.value == entry.value);
        }
    }

    protected HashEntry<K,V> AVAILABLE = new HashEntry<>(null, null); //sentinel, a marker for deactivated entry
    protected int n = 0;
    protected int prime, capacity; //prime factor and capacity of backet array
    protected Entry<K,V> [] bucket; //bucket array
    protected long scale, shift; //the shift and scale factors

    public HashTableMap() {
        this(10);
    }

    public HashTableMap(int capacity) {
        this(109345121, capacity);
    }

    public HashTableMap(int prime, int capacity) {
        this.prime = prime;
        this.capacity = capacity;
        bucket = (Entry<K,V>[]) new Entry[capacity]; //safe cast
        Random rand = new Random();
        rand.setSeed(1);
        scale = rand.nextInt(prime - 1) + 1;
        shift = rand.nextInt(prime);
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public boolean isEmpty() {
        return n == 0;
    }

    @Override
    public V get(K key) throws InvalidKeyException{
        int i = findEntry(key);
        if(i < 0) return null;
        return bucket[i].getValue();
    }

    @Override
    public V put(K key, V value) throws InvalidKeyException{
        int i = findEntry(key);
        if(i >= 0) {
            return ((HashEntry<K,V>) bucket[i]).setValue(value); //回傳舊的值
        }
        if(n >= capacity / 2) {
            rehash(); //使load factor <= 0.5
            i = findEntry(key); //因為重新hash過, 所以要重找一次位置
        }
        bucket[-1 - i] = new HashEntry<K,V>(key, value);
        n++;
        return null;
    }

    @Override
    public V remove(K key) throws InvalidKeyException{
        int i = findEntry(key);
        if(i < 0) return null;
        V oldValue = bucket[i].getValue();
        bucket[i] = AVAILABLE;
        n--;
        return oldValue;
    }

    @Override
    public Iterable<K> keySet() {
        PositionList<K> keys = new NodePositionList<>();
        for(int i = 0; i < capacity; i++) {
            if(bucket[i] != null && bucket[i] != AVAILABLE)
                keys.addLast(bucket[i].getKey());
        }
        return keys;
    }

    @Override
    public Iterable<V> values() {
        PositionList<V> values = new NodePositionList<>();
        for(int i = 0; i < capacity; i++) {
            if(bucket[i] != null && bucket[i] != AVAILABLE)
                values.addLast(bucket[i].getValue());
        }
        return values;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        PositionList<Entry<K,V>> entrySet = new NodePositionList<>();
        for(int i = 0; i < capacity; i++) {
            if(bucket[i] != null && bucket[i] != AVAILABLE)
                entrySet.addLast(bucket[i]);
        }
        return entrySet;
    }

    //Determines whether a key is valid
    protected void checkKey(K k) throws InvalidKeyException {
        if(k == null) throw new InvalidKeyException("Invalid key: null");
    }

    //Hash function applying MAD method to default hash code
    public int hashValue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    //Helper search method - returns index of found key or -(a + 1)
    //where a is the index of the first empty or available slot found
    protected int findEntry(K key) throws InvalidKeyException{
        checkKey(key);
        int available = -1;
        int i = hashValue(key);
        int j = i;
        do {
            Entry<K, V> entry = bucket[j];
            //找不到key
            if(entry == null) {
                if(available < 0) //為了找到第一個空的位置
                    available = j;
                break;
            }
            //找到key
            if(key.equals(entry.getKey())) {
                return j;
            }
            //該位置已被deactivated
            if(entry == AVAILABLE) {
                if(available < 0) //為了找到第一個空的位置
                    available = j;
            }
            j = (j + 1) % capacity;
        } while (i != j);
        return -(available + 1);
    }

    protected void rehash() throws InvalidKeyException{
        capacity = capacity * 2;
        Entry<K,V>[] old = bucket;
        bucket = (Entry<K,V>[]) new Entry[capacity]; //new bucket
        Random rand = new Random();
        rand.setSeed(1);
        scale = rand.nextInt(prime - 1); //new hash scaling factor
        shift = rand.nextInt(prime);            //new hash shifting factor
        for(int i = 0; i < old.length; i++) {
            Entry<K,V> entry = old[i];
            if(entry != null && entry != AVAILABLE) {
                int j = -1 - findEntry(entry.getKey());
                bucket[j] = entry;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for(int i = 0; i < capacity; i++) {
            Entry<K,V> entry = bucket[i];
            if(entry == null || entry == AVAILABLE) {
                sb.append("null");
            } else {
                sb.append("(" + entry.getKey() + "," + entry.getValue() + ")");
            }
            if(i != capacity - 1)
                sb.append(", ");
        }
        return sb.append("]").toString();
    }
}
