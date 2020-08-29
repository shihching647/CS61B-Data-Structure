package textbook.ch9_2_HashTableMap;

/*Interface for a key-value pair entry*/
public interface Entry<K,V> {
    /*Returns the key stored in the entry.*/
    public K getKey();
    /*Returns the value stored in the entry*/
    public V getValue();
}
