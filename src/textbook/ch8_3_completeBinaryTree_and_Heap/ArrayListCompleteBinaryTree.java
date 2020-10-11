package textbook.ch8_3_completeBinaryTree_and_Heap;

import textbook.ch6_2_ch_6_3_nodeList.BoundaryViolationException;
import textbook.ch6_2_ch_6_3_nodeList.InvalidPositionException;
import textbook.ch6_2_ch_6_3_nodeList.Position;
import textbook.ch7_1_generalTree.EmptyTreeException;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListCompleteBinaryTree<E> implements CompleteBinaryTree<E> {
    // Nested class for a index list-based complete binary tree node.
    protected class BTPos<E> implements Position<E> {
        E element;  // element stored at this position
        int index;  // index of this position in the array
        public BTPos(E element, int index) {
            this.element = element;
            this.index = index;
        }
        @Override
        public E element() {
            return element;
        }
        public int index() {
            return index;
        }
        public E setElement(E element) {
            E temp = this.element;
            this.element = element;
            return temp;
        }
    }

    protected ArrayList<BTPos<E>> tree; //indexed list of tree positions

    public ArrayListCompleteBinaryTree() {
        tree = new ArrayList<BTPos<E>>();
        tree.add(0, null); // the location at rank 0 is deliberately empty
    }
    @Override
    public int size() {
        return tree.size() - 1;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    // Returns an iterator of the elements stored at all nodes in the tree.
    @Override
    public Iterator<E> iterator() {
        ArrayList<E> list = new ArrayList<>();
        Iterator<BTPos<E>> it = tree.iterator();
        it.next(); //skip the first element
        while(it.hasNext()) {
            list.add(it.next().element());
        }
        return list.iterator();
    }

    @Override
    public Iterable<Position<E>> positions() {
        ArrayList<Position<E>> list = new ArrayList<>();
        Iterator<BTPos<E>> it = tree.iterator();
        it.next(); //skip the first element
        while(it.hasNext()) {
            list.add(it.next());
        }
        return list;
    }

    @Override
    public E replace(Position<E> node, E e) throws InvalidPositionException {
        BTPos<E> nodeBT = checkPosition(node);
        return nodeBT.setElement(e);
    }

    @Override
    public Position<E> root() throws EmptyTreeException {
        if(isEmpty()) {
            throw new EmptyTreeException("The tree is empty");
        }
        return tree.get(1);
    }

    @Override
    public Position<E> parent(Position<E> node) throws InvalidPositionException, BoundaryViolationException {
        if(isRoot(node)) {
            throw new BoundaryViolationException("No parent");
        }
        BTPos<E> nodeBT = checkPosition(node);
        return tree.get(nodeBT.index() / 2);
    }

    @Override
    public Iterable<Position<E>> children(Position<E> node) throws InvalidPositionException {
        ArrayList<Position<E>> children = new ArrayList<>();
        if(hasLeft(node)) {
            children.add(left(node));
        }
        if(hasRight(node)) {
            children.add(right(node));
        }
        return children;
    }

    @Override
    public boolean isInternal(Position<E> node) throws InvalidPositionException {
        return hasLeft(node); // if node has a right child  it will have a left child
    }

    @Override
    public boolean isExternal(Position<E> node) throws InvalidPositionException {
        return !isInternal(node);
    }

    @Override
    public boolean isRoot(Position<E> node) throws InvalidPositionException {
        BTPos<E> nodeBT = checkPosition(node);
        return nodeBT.index() == 1;
    }

    // Add an element just after the last node (in a level numbering).
    @Override
    public Position<E> add(E element) {
        int i = size() + 1;
        BTPos<E> nodeBT = new BTPos<>(element, i);
        tree.add(i, nodeBT);
        return nodeBT;
    }

    //Remove and returns the element at the last node.
    @Override
    public E remove() throws EmptyTreeException{
        if(isEmpty()) {
            throw new EmptyTreeException("The tree is empty");
        }
        return tree.remove(size()).element();
    }

    @Override
    public Position<E> left(Position<E> node) throws InvalidPositionException, BoundaryViolationException {
        if(!hasLeft(node)) {
            throw new BoundaryViolationException("No left child");
        }
        BTPos<E> nodeBT = checkPosition(node);
        return tree.get(2 * nodeBT.index());
    }

    @Override
    public Position<E> right(Position<E> node) throws InvalidPositionException, BoundaryViolationException {
        if(!hasRight(node)) {
            throw new BoundaryViolationException("No right child");
        }
        BTPos<E> nodeBT = checkPosition(node);
        return tree.get(2 * nodeBT.index() + 1);
    }

    @Override
    public boolean hasLeft(Position<E> node) throws InvalidPositionException {
        BTPos<E> nodeBT = checkPosition(node);
        return 2 * nodeBT.index() <= size();
    }

    @Override
    public boolean hasRight(Position<E> node) throws InvalidPositionException {
        BTPos<E> nodeBT = checkPosition(node);
        return 2 * nodeBT.index() + 1 <= size();
    }

    // Determine whether the given position is a valid node.
    private BTPos<E> checkPosition(Position<E> node) throws InvalidPositionException{
        if(!(node instanceof BTPos)) {
            throw new InvalidPositionException("The position is invalid");
        }
        return ((BTPos<E>) node);
    }

    // preorder traversal
    public String preorderToString(Position<E> node) {
        StringBuilder sb = new StringBuilder();
        sb.append(node.element().toString());
        if(hasLeft(node)) {
            sb.append(", " + preorderToString(left(node)));
        }
        if(hasRight(node)) {
            sb.append(", " + preorderToString(right(node)));
        }
        return sb.toString();
    }

}
