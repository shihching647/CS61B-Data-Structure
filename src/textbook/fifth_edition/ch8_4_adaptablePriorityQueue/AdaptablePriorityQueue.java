package textbook.fifth_edition.ch8_4_adaptablePriorityQueue;

import textbook.fifth_edition.ch8_2_listPriorityQueue.InvalidKeyException;
import textbook.fifth_edition.ch8_2_listPriorityQueue.PriorityQueue;
import textbook.fifth_edition.ch9_2_HashTableMap.Entry;

public interface AdaptablePriorityQueue<K,V> extends PriorityQueue<K,V> {
    public Entry<K,V> remove(Entry<K,V> entry);
    public K replaceKey(Entry<K,V> entry, K key)throws InvalidKeyException;
    public V replaceValue(Entry<K,V> entry, V value);
}
