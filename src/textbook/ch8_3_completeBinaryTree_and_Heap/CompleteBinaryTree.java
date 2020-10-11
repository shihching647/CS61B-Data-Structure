package textbook.ch8_3_completeBinaryTree_and_Heap;

import textbook.ch6_2_ch_6_3_nodeList.Position;
import textbook.ch7_1_generalTree.EmptyTreeException;
import textbook.ch7_3_binaryTree.BinaryTree;

public interface CompleteBinaryTree<E> extends BinaryTree<E> {
    public Position<E> add(E element);
    public E remove() throws EmptyTreeException;
}
