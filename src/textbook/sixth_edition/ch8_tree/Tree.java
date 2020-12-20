package textbook.sixth_edition.ch8_tree;

import textbook.sixth_edition.ch7_list.Position;

import java.util.Iterator;

public interface Tree<E> extends Iterable<E> {
    Position<E> root();
    Position<E> parent(Position<E> p) throws IllegalArgumentException;
    Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException;
    /*Returns the number of children of position p.*/
    int numChildren(Position<E> p) throws IllegalArgumentException;
    boolean isInternal(Position<E> p) throws IllegalArgumentException;
    boolean isExternal(Position<E> p) throws IllegalArgumentException;
    boolean isRoot(Position<E> p) throws IllegalArgumentException;
    int size();
    boolean isEmpty();
    /*Returns an iterable collection of all positions of the tree.*/
    Iterator<E> iterator();
    Iterable<Position<E>> positions();
}
