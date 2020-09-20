package textbook.ch7_3_binaryTree;

import textbook.ch6_2_ch_6_3_nodeList.BoundaryViolationException;
import textbook.ch6_2_ch_6_3_nodeList.InvalidPositionException;
import textbook.ch6_2_ch_6_3_nodeList.Position;
import textbook.ch7_1_generalTree.Tree;

/**
 * An interface for a binary tree, which each node can have
 * zero, one ro two children.
 */
public interface BinaryTree<E> extends Tree<E> {
    // Returns the left child of a node.
    public Position<E> left(Position<E> node) throws InvalidPositionException, BoundaryViolationException;
    // Returns the right child of a node.
    public Position<E> right(Position<E> node) throws InvalidPositionException, BoundaryViolationException;
    // Returns whether a node has a left child.
    public boolean hasLeft(Position<E> node) throws InvalidPositionException;
    // Returns whether a node has a right child.
    public boolean hasRight(Position<E> node) throws InvalidPositionException;
}
