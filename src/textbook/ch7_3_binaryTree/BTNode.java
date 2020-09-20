package textbook.ch7_3_binaryTree;

public class BTNode<E> implements BTPosition<E>{
    private E element;
    private BTPosition<E> left, right, parent;

    //Main constructor
    public BTNode(E element, BTPosition<E> left, BTPosition<E> right, BTPosition<E> parent) {
        this.element = element;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    public E element() {
        return element;
    }

    @Override
    public void setElement(E element) {
        this.element = element;
    }

    public BTPosition<E> getLeft() {
        return left;
    }

    @Override
    public void setLeft(BTPosition<E> left) {
        this.left = left;
    }

    public BTPosition<E> getRight() {
        return right;
    }

    @Override
    public void setRight(BTPosition<E> right) {
        this.right = right;
    }

    public BTPosition<E> getParent() {
        return parent;
    }

    @Override
    public void setParent(BTPosition<E> parent) {
        this.parent = parent;
    }
}
