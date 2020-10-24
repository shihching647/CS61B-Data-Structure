package textbook.fifth_edition.ch8_3_completeBinaryTree_and_Heap;

import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.Position;
import textbook.fifth_edition.ch7_1_generalTree.EmptyTreeException;
import textbook.fifth_edition.ch7_3_binaryTree.BinaryTree;

public interface CompleteBinaryTree<E> extends BinaryTree<E> {
    public Position<E> add(E element);
    public E remove() throws EmptyTreeException;
}
