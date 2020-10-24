package textbook.fifth_edition.ch8_2_listPriorityQueue;

import java.util.Comparator;

// Default comparator is used for comparing two keys with natural ordering
public class DefaultComparator<E> implements Comparator<E> {
    public int compare(E a, E b) throws ClassCastException{
        return ((Comparable<E>) a).compareTo(b);
    }
}
