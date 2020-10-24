package textbook.fifth_edition.ch7_3_binaryTree;

import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.Position;

public interface BTPosition<E> extends Position<E> {
    // Set the element.
    public void setElement(E element);
    // Setter and getter of the left child.
    public BTPosition<E> getLeft();
    public void setLeft(BTPosition<E> node);
    // Setter and getter of the right child.
    public BTPosition<E> getRight();
    public void setRight(BTPosition<E> node);
    // Setter and getter of the parent.
    public BTPosition<E> getParent();
    public void setParent(BTPosition<E> node);

}
