package textbook.sixth_edition.ch10_map;

import textbook.fifth_edition.ch6_1.ArrayIndexList;
import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.NodePositionList;
import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.PositionList;
import textbook.sixth_edition.ch9_priorityQueue.Entry;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnsortedTableMap<K,V> extends AbstractMap<K,V> {

    private ArrayIndexList<MapEntry<K,V>> table = new ArrayIndexList<>();

    public UnsortedTableMap() { }

    private int findIndex(K key) {
        for(int i = 0; i < table.size(); i++) {
            Entry<K,V> entry = table.get(i);
            if(entry.getKey().equals(key)) return i;
        }
        return -1;
    }

    public int size() { return table.size(); }

    public V get(K key) {
        int index = findIndex(key);
        if(index == -1) return null;
        return table.get(index).getValue();
    }

    public V put(K key, V value) {
        int index = findIndex(key);
        if(index == -1) {
            table.add(size(), new MapEntry<>(key, value));
            return null;
        }
        else {
            return table.get(index).setValue(value);
        }
    }

    public V remove(K key) {
        int index = findIndex(key);
        if(index == -1) return null;
        else {
            V oldValue = table.get(index).getValue();
            if(index != size() - 1)
                table.set(index, table.get(size() - 1));    // relocate last entry to ’hole’ created by removal
            table.remove(size() - 1);                    // remove last entry of table
            return oldValue;
        }
    }

//    public Iterable<Entry<K,V>> entrySet() {
//        PositionList<Entry<K,V>> list = new NodePositionList<>();
//        for(int i = 0; i < table.size(); i++) {
//            list.addLast(table.get(i));
//        }
//        return list;
//    }

    public Iterable<Entry<K,V>> entrySet() {
        return new EntrySetIterable();
    }

    // Support for public entrySet method...
    private class EntrySetIterable implements Iterable<Entry<K,V>> {
        public Iterator<Entry<K,V>> iterator() {
            return new EntrySetIterator();
        }
    }
    private class EntrySetIterator implements Iterator<Entry<K,V>> {
        private int currentIndex;
        @Override
        public boolean hasNext() {
            return currentIndex < table.size();
        }

        @Override
        public Entry<K, V> next() {
            if(currentIndex == table.size()) throw new NoSuchElementException();
            return table.get(currentIndex++);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
