package textbook.sixth_edition.ch8_tree.ch_8_2_binary_tree;

import textbook.sixth_edition.ch7_list.Position;
import textbook.sixth_edition.ch8_tree.AbstractTree;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {

    public Position<E> sibling(Position<E> p) {
        Position<E> parent = parent(p);
        if(parent == null) return null;
        if(p == left(parent))
            return right(p);
        else
            return left(p);
    }

    public int numChildren(Position<E> p) {
        int count = 0;
        if(left(p) != null)
            count++;
        if(right(p) != null)
            count++;
        return count;
    }

    public Iterable<Position<E>> children(Position<E> p) {
        List<Position<E>> snapshot = new ArrayList<Position<E>>(2);
        if(left(p) != null)
            snapshot.add(left(p));
        if(right(p) != null)
            snapshot.add(right(p));
        return snapshot;
    }

    //Tree Traversal Algorithm
    //The inorder traversal algorithm, because it explicitly relies on the notion of
    //a left and right child of a node, only applies to binary trees.

    /** Overrides positions to make inorder the default order for binary trees. */
    public Iterable<Position<E>> positions() {
        return inOrder();
    }
    /**Returns an iterable collection of positions of the tree, reported in inorder. */
    public Iterable<Position<E>> inOrder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if(!isEmpty())
            inOrderSubtree(root(), snapshot);
        return snapshot;
    }

    /** Adds positions of the subtree rooted at Position p to the given snapshot. */
    public void inOrderSubtree(Position<E> p, List<Position<E>> snapshot) {
        if(left(p) != null) {
            inOrderSubtree(left(p), snapshot);
        }
        snapshot.add(p);
        if(right(p) != null) {
            inOrderSubtree(right(p), snapshot);
        }
    }
}
