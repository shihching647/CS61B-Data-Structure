package textbook.fifth_edition.ch7_1_generalTree;

import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.BoundaryViolationException;
import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.InvalidPositionException;
import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.Position;

import java.util.Iterator;

// 1.Each method that take a position as an argument may throw an InvalidPositionException
// 2.Method parent is called on a root will throw a BoundaryViolationException
// 3.Method root is called on an empty tree will throw a EmptyTreeException

public interface Tree<E> {
    public int size();
    public boolean isEmpty();
    /*Returns an iterator of elements stored in the tree.*/
    public Iterator<E> iterator();
    /*Returns an iterable collection of the node.s*/
    public Iterable<Position<E>> positions();
    /*Replaces the element stored at a given node.*/
    public E replace(Position<E> node, E e) throws InvalidPositionException;
    /*Returns the root of the tree.*/
    public Position<E> root() throws EmptyTreeException;
    /*Returns the parent of a given node*/
    public Position<E> parent(Position<E> node) throws InvalidPositionException, BoundaryViolationException;
    /*Returns an iterable collection of the children of a given node*/
    public Iterable<Position<E>> children(Position<E> node) throws InvalidPositionException;
    /*Returns whether a given node is internal*/
    public boolean isInternal(Position<E> node) throws InvalidPositionException;
    /*Returns whether a given node is external*/
    public boolean isExternal(Position<E> node) throws InvalidPositionException;
    /*Returns whether a given node is the root of the tree*/
    public boolean isRoot(Position<E> node) throws InvalidPositionException;
}
