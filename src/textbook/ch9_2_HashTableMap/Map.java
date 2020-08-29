package textbook.ch9_2_HashTableMap;

import java.security.InvalidKeyException;

public interface Map<K,V> {
    public int size();
    public boolean isEmpty();
    /*If Map contains the entry(key, value), returns the value, else return null*/
    public V get(K key) throws InvalidKeyException;
    /*If Map doesn't contain the entry(key, value), then add entry(key,value) and returns the null.
     else replace the value with new one and return the old one.*/
    public V put(K key, V value) throws InvalidKeyException;
    /*Remove from Map the entry with key equals to key, and return its value, else return null*/
    public V remove(K key) throws InvalidKeyException;
    /*Return an iterable collection containing all the keys stored in Map*/
    public Iterable<K> keySet();
    /*Return an iterable collection containing all the values stored in Map*/
    public Iterable<V> values();
    /*Return an iterable collection containing all the entries stored in Map*/
    public Iterable<Entry<K, V>> entrySet();
}
