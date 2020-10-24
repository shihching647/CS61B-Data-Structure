package textbook.fifth_edition.ch6_5_favoriteList;
import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.*;

public class FavoriteList<E> {
    protected PositionList<Entry<E>> fList;

    public FavoriteList() {
        fList = new NodePositionList<>();
    }

    public int size() {
        return fList.size();
    }

    public boolean isEmpty() {
        return fList.isEmpty();
    }

    public void remove(E obj) {
        Position<Entry<E>> p = find(obj);
        if(p != null) {
            fList.remove(p);
        }
    }

    /*Increments the access count for the given element and inserts it if it's not already present; O(n) time*/
    public void access(E obj) {
        Position<Entry<E>> p = find(obj);
        if(p != null) {
            p.element().incrementCount();
        } else {
            fList.addLast(new Entry<>(obj));
            p = fList.last();
        }
        moveUp(p); //reset position of p
    }

    protected Position<Entry<E>> find(E obj) {
        for(Position<Entry<E>> p : fList.positions()) {
            if(value(p).equals(obj))
                return p;
        }
        return null;
    }

    /*Move up an entry to its correct position in list, O(n) time*/
    protected void moveUp(Position<Entry<E>> cur) {
        Entry<E> e = cur.element();
        int c = count(cur);
        while(cur != fList.first()) {
            Position<Entry<E>> prev = fList.prev(cur);
            if(c <= count(prev)) break;
            fList.set(cur, prev.element()); //前一個的element設到cur的位置
            cur = prev;
        }
        fList.set(cur, e); //cur的element的最後位置
    }

    /*Returns the k most accessed elements, for a given k; O(k) time*/
    public Iterable<E> top(int k) {
        if(k <= 0 || k > size()) {
            throw new IllegalArgumentException("Invalid argument!");
        }
        PositionList<E> topList = new NodePositionList<>();
        int i = 0;
        for(Entry<E> e : fList) {
            if(i++ >= k) break;
            topList.addLast(e.value());
        }
        return topList;
    }

    public String toString() {
        return fList.toString();
    }
    //Helper method that extracts the value of the entry at a given position
    protected E value(Position<Entry<E>> p) {
        return p.element().value();
    }
    //Helper method that extracts the count of the entry at a given position
    protected int count(Position<Entry<E>> p) {
        return p.element().count();
    }
    /*Inner class storing elements and their access counts*/
    protected static class Entry<E> {
        private E value;
        private int count;

        Entry(E value) {
            this.count = 1;
            this.value = value;
        }

        public E value() {
            return value;
        }

        public int count() {
            return count;
        }
        /*Increments the access count*/
        public int incrementCount() {
            return ++count;
        }

        public String toString() {
            return "[" + count + ", " + value + "]";
        }
    }
}
