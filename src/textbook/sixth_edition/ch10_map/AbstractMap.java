package textbook.sixth_edition.ch10_map;

import textbook.sixth_edition.ch9_priorityQueue.Entry;

import java.util.Iterator;

public abstract class AbstractMap<K,V> implements Map<K,V> {
    public boolean isEmpty() { return size() == 0;}

    protected static class MapEntry<K,V> implements Entry<K,V> {
        private K key;
        private V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() { return key; }

        public void setKey(K key) { this.key = key; }

        @Override
        public V getValue() { return value; }

        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public String toString() {
            return "(" + key + "," + value + ")";
        }
    }

    public Iterable<K> keySet() {
        return new KeyIterable();
    }

    // Support for public keySet method...
    private class KeyIterable implements Iterable<K>{
        public Iterator<K> iterator() {
            return new KeyIterator();
        }
    }
    private class KeyIterator implements Iterator<K> {
        private Iterator<Entry<K,V>> entries = entrySet().iterator();// reuse entrySet
        @Override
        public boolean hasNext() {
            return entries.hasNext();
        }

        @Override
        public K next() {
            return entries.next().getKey();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterable<V> values() {
        return new ValueIterable();
    }

    // Support for public values method...
    private class ValueIterable implements Iterable<V> {
        public Iterator<V> iterator() {
            return new ValueIterator();
        }
    }
    private class ValueIterator implements Iterator<V> {
        private Iterator<Entry<K,V>> entries = entrySet().iterator();// reuse entrySet
        public boolean hasNext() {
            return entries.hasNext();
        }
        public V next() {
            return entries.next().getValue();
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator<Entry<K,V>> iterator = entrySet().iterator();
        while(iterator.hasNext()) {
            sb.append(iterator.next());
            if(iterator.hasNext())
                sb.append(", ");
        }
        return sb.append("]").toString();
    }
}
