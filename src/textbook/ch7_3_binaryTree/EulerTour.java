package textbook.ch7_3_binaryTree;

import textbook.ch6_2_ch_6_3_nodeList.Position;

/**
 * Template for algorithms traversing a binary tree using an Euler tour.
 * The subclasses of this class will redefine some of the methods of this
 * class to create a specific traversal.
 * @param <E> type of binary tree
 * @param <R> type of evaluate value
 */
public abstract class EulerTour<E,R> {
    protected BinaryTree<E> tree;

    // Execution of the traversal. This abstract method must be specified in a concrete subclass.
    public abstract R execute(BinaryTree<E> tree);

    // Initialization of the traversal.
    protected void init(BinaryTree tree) {this.tree = tree;}

    // Template method.
    protected R eulerTour(Position<E> node) {
        TourResult<R> result = new TourResult();
        visitLeft(node, result);
        if(tree.hasLeft(node)) {
            result.left = eulerTour(tree.left(node));
        }
        visitBelow(node, result);
        if(tree.hasRight(node)) {
            result.right = eulerTour(tree.right(node));
        }
        visitRight(node, result);
        return result.out;
    }

    // Auxiliary methods that can be redefined by subclasses:
    // Method called for the visit on the left.
    protected void visitLeft(Position<E> node, TourResult<R> result) {}
    // Method called for the visit on from below.
    protected void visitBelow(Position<E> node, TourResult<R> result) {}
    // Method called for the visit on the right.
    protected void visitRight(Position<E> node, TourResult<R> result) {}
}
