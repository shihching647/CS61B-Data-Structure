package textbook.fifth_edition.ch6_2_ch_6_3_nodeList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ElementIterator<E> implements Iterator<E> {
    protected PositionList<E> list;
    protected Position<E> cursor;

    public ElementIterator(PositionList<E> list) {
        this.list = list;
        cursor = (list.isEmpty()) ? null : list.first();
    }
    @Override
    public boolean hasNext() {
        return cursor != null;
    }

    @Override
    public E next() throws NoSuchElementException {
        if(cursor == null) {
            throw new NoSuchElementException("No next element");
        }
        E result = cursor.element();
        cursor = (cursor == list.last()) ? null : list.next(cursor);
        return result;
    }
}
