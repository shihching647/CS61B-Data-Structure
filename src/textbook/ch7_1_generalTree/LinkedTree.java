package textbook.ch7_1_generalTree;

import textbook.ch6_2_ch_6_3_nodeList.BoundaryViolationException;
import textbook.ch6_2_ch_6_3_nodeList.InvalidPositionException;
import textbook.ch6_2_ch_6_3_nodeList.Position;

import java.util.Iterator;

//TODO 用linked結構實作Tree + example驗證

public class LinkedTree<E> implements Tree<E> {

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Iterable<Position<E>> positions() {
        return null;
    }

    @Override
    public E replace(Position<E> node, E e) throws InvalidPositionException {
        return null;
    }

    @Override
    public Position<E> root() throws EmptyTreeException {
        return null;
    }

    @Override
    public Position<E> parent(Position<E> node) throws InvalidPositionException, BoundaryViolationException {
        return null;
    }

    @Override
    public Iterable<Position<E>> children(Position<E> node) throws InvalidPositionException {
        return null;
    }

    @Override
    public boolean isInternal(Position<E> node) throws InvalidPositionException {
        return false;
    }

    @Override
    public boolean isExternal(Position<E> node) throws InvalidPositionException {
        return false;
    }

    @Override
    public boolean isRoot(Position<E> node) throws InvalidPositionException {
        return false;
    }
}
