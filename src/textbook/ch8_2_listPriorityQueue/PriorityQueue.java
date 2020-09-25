package textbook.ch8_2_listPriorityQueue;

import textbook.ch9_2_HashTableMap.Entry;

/** Interface for the priority queue ADT*/
public interface PriorityQueue<K,V> {
    /* Returns the number of items in the priority queue*/
    public int size();
    /* Returns whether the priority queue is empty*/
    public boolean isEmpty();
    /* Returns but does not remove an entry with minimum key.*/
    public Entry<K,V> min() throws EmptyPriorityQueueException;
    /* Insert a key-value pair and return the entry created*/
    public Entry<K,V> insert(K key, V value) throws InvalidKeyException;
    /* Removes and returns the entry with minimum key.*/
    public Entry<K,V> removeMin() throws EmptyPriorityQueueException;
}
