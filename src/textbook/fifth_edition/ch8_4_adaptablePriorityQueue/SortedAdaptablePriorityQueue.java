package textbook.fifth_edition.ch8_4_adaptablePriorityQueue;

import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.Position;
import textbook.fifth_edition.ch8_2_listPriorityQueue.InvalidKeyException;
import textbook.fifth_edition.ch9_2_HashTableMap.Entry;
import textbook.fifth_edition.ch8_2_listPriorityQueue.SortedListPriorityQueue;

import java.util.Comparator;

public class SortedAdaptablePriorityQueue<K,V> extends SortedListPriorityQueue<K,V> implements AdaptablePriorityQueue<K,V>{

    protected static class LocationAwareEntry<K,V> extends MyEntry<K,V> implements Entry<K,V> {
        private Position<Entry<K,V>> location;
        public LocationAwareEntry(K key, V value) {
            super(key, value);
        }
        public LocationAwareEntry(K key, V value, Position<Entry<K,V>> position) {
            super(key, value);
            location = position;
        }
        protected Position<Entry<K,V>> location() {
            return location;
        }
        protected Position<Entry<K,V>> setLocation(Position<Entry<K,V>> position) {
            Position<Entry<K,V>> oldLocation = location();
            location = position;
            return oldLocation;
        }
        protected K setKey(K key) {
            K oldKey = this.key;
            this.key = key;
            return oldKey;
        }
        protected V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    public SortedAdaptablePriorityQueue() {
        super();
    }

    public SortedAdaptablePriorityQueue(Comparator<K> comparator) {
        super(comparator);
    }

    public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
        checkKey(key);
        LocationAwareEntry<K,V> entry = new LocationAwareEntry<>(key, value);
        checkEntry(entry);
        insertEntry(entry);
        entry.setLocation(actionPos);
        return entry;
    }

    @Override
    public Entry<K, V> remove(Entry<K, V> entry)throws InvalidEntryException {
        checkEntry(entry);
        LocationAwareEntry<K,V> awareEntry = (LocationAwareEntry<K,V>) entry;
        Position<Entry<K,V>> position = awareEntry.location();
        entries.remove(position); //直接移除即可
        awareEntry.setLocation(null);
        return awareEntry;
    }

    //先移除entry, 再replace key, 再insert
    @Override
    public K replaceKey(Entry<K, V> entry, K key) throws InvalidKeyException {
        checkKey(key);
        checkEntry(entry);
        LocationAwareEntry<K,V> oldEntry = (LocationAwareEntry<K,V>) remove(entry);
        K oldKey = oldEntry.setKey(key);
        insertEntry(oldEntry);
        oldEntry.setLocation(actionPos);
        return oldKey;
    }

    @Override
    public V replaceValue(Entry<K, V> entry, V value) {
        checkEntry(entry);
        LocationAwareEntry<K,V> awareEntry = (LocationAwareEntry<K, V>) entry;
        return awareEntry.setValue(value);
    }

    protected void checkEntry(Entry<K,V> entry) throws InvalidEntryException {
        if (!(entry instanceof LocationAwareEntry)) {
            throw new InvalidEntryException("invalid entry");
        }
    }

}
