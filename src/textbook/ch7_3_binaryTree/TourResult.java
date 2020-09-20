package textbook.ch7_3_binaryTree;

/**
 * Class for storing intermediate results of the computation.
 */
public class TourResult<E> {
    protected E left;
    protected E right;
    protected E out;

    public TourResult() {
        left = null;
        right = null;
        out = null;
    }
}
