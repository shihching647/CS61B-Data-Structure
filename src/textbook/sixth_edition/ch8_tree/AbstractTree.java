package textbook.sixth_edition.ch8_tree;

import javafx.geometry.Pos;
import textbook.fifth_edition.ch5_2.NodeQueue;
import textbook.fifth_edition.ch5_2.Queue;
import textbook.sixth_edition.ch7_list.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractTree<E> implements Tree<E> {

    public boolean isInternal(Position<E> p) throws IllegalArgumentException {
        return numChildren(p) > 0;
    }

    public boolean isExternal(Position<E> p) throws IllegalArgumentException {
        return numChildren(p) == 0;
    }

    public boolean isRoot(Position<E> p) throws IllegalArgumentException {
        return p == root();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    /** Returns the number of levels separating Position p from the root. */
    public int depth(Position<E> p) {
        if (isRoot(p)) {
            return 0;
        } else {
            return 1 + depth(p);
        }
    }

    /** Returns the height of the subtree rooted at Position p. */
    public int height(Position<E> p) {
        int h = 0;      // base case if p is external
        for(Position<E> c : children(p)) {
            h = Math.max(h, 1 + height(p));
        }
        return h;
    }

    /**Returns the height of the tree*/
    private int heightBad() {  // works, but quadratic worst-case time, Exercise C-8.31
        int h = 0;
        for(Position<E> p : positions()) {
            if(isExternal(p))   // only consider leaf positions
                h = Math.max(h, depth(p));
        }
        return h;
    }

    //---------------- nested ElementIterator class ----------------
    /* This class adapts the iteration produced by positions() to return elements. */
    private class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> posIterator = positions().iterator();
        public boolean hasNext() { return posIterator.hasNext();}
        public E next() { return posIterator.next().getElement();}
        public void remove() { posIterator.remove();}
    }

    public Iterator<E> iterator() { return new ElementIterator(); }

    public Iterable<Position<E>> positions() { return preOrder(); }

    //Tree Traversal Algorithm
    //The preorder, postorder, and breadth-first traversal algorithms are applicable to all trees.

    /** Returns an iterable collection of positions of the tree, reported in preorder. */
    public Iterable<Position<E>> preOrder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if(!isEmpty())
            preOrderSubtree(root(), snapshot);
        return snapshot;
    }

    /** Adds positions of the subtree rooted at Position p to the given snapshot. */
    private void preOrderSubtree(Position<E> p, List<Position<E>> snapshot) {
        snapshot.add(p);
        for(Position<E> child : children(p)) {
            preOrderSubtree(child, snapshot);
        }
    }

    /** Returns an iterable collection of positions of the tree, reported in postorder. */
    public Iterable<Position<E>> postOrder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if(!isEmpty())
            postOrderSubtree(root(), snapshot);
        return snapshot;
    }

    /** Adds positions of the subtree rooted at Position p to the given snapshot. */
    private void postOrderSubtree(Position<E> p, List<Position<E>> snapshot) {
        for(Position<E> child : children(p)) {
            postOrderSubtree(child, snapshot);
        }
        snapshot.add(p);
    }
    
    /** Returns an iterable collection of positions of the tree in breadth-first order. */
    public Iterable<Position<E>> breadthFirst() {
        List<Position<E>> snapshot = new ArrayList<>();
        if(!isEmpty()) {
            Queue<Position<E>> fringe = new NodeQueue<>();
            fringe.enqueue(root());
            while (!fringe.isEmpty()) {
                Position<E> p = fringe.dequeue();
                snapshot.add(p);
                for(Position<E> child : children(p))
                    fringe.enqueue(child);
            }
        }
        return snapshot;
    }
}
