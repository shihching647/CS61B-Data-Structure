package textbook.sixth_edition.ch8_tree.ch_8_3_linked_binary_tree;

import textbook.sixth_edition.ch7_list.Position;
import textbook.sixth_edition.ch8_tree.ch_8_2_binary_tree.AbstractBinaryTree;
import textbook.sixth_edition.ch8_tree.ch_8_2_binary_tree.BinaryTree;

import java.util.Iterator;

public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> parent, left, right;

        public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
            element = e;
            parent = above;
            left = leftChild;
            right = rightChild;
        }

        public E getElement() { return element;}
        public Node<E> getParent() { return parent;}
        public Node<E> getLeft() { return left;}
        public Node<E> getRight() { return right;}

        public void setElement(E e) { element = e;}
        public void setParent(Node<E> parentNode) { parent = parentNode;}
        public void setLeft(Node<E> leftChild) { left = leftChild;}
        public void setRight(Node<E> rightChild) { right = rightChild;}
    }

    /** Factory function to create a new node storing element e. */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    // LinkedBinaryTree instance variables
    protected Node<E> root = null;
    private int size = 0;

    public LinkedBinaryTree() {}

    // nonpublic utility
    /** Validates the position and returns it as a node. */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if(!(p instanceof Node)) {
            throw new IllegalArgumentException("Not valid position type");
        }
        Node<E> node = (Node<E>) p;
        if(node.getParent() == node) {          // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        }
        return node;
    }

    public int size() { return size;}

    public Position<E> root() { return root;}

    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getParent();
    }

    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getLeft();
    }

    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getRight();
    }

    public Iterable<Position<E>> positions() {
        return null;
    }
    // update methods supported by this class
    public Position<E> addRoot(E e) throws IllegalStateException {
        if(!isEmpty())
            throw new IllegalStateException("Tree is no empty.");
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }

    public Position<E> addLeft(Position<E> p, E e) throws IllegalStateException{
        Node<E> parent = validate(p);
        if(parent.getLeft() != null)
            throw new IllegalStateException("p already has a left child.");
        Node<E> leftChild = createNode(e, parent, null, null);
        parent.setLeft(leftChild);
        size++;
        return leftChild;
    }

    public Position<E> addRight(Position<E> p, E e) throws IllegalStateException {
        Node<E> parent = validate(p);
        if(parent.getRight() != null)
            throw new IllegalStateException("p already has a right child.");
        Node<E> rightChild = createNode(e, parent, null, null);
        parent.setRight(rightChild);
        size++;
        return rightChild;
    }

    public E set(Position<E> p, E e) throws IllegalArgumentException{
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }

    /** Attaches trees t1 and t2 as left and right subtrees of external p. */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException{
        Node<E> node = validate(p);
        if(isInternal(p)) throw new IllegalArgumentException("p must be a leaf.");
        size = t1.size() + t2.size();
        if(!t1.isEmpty()) {
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if(!t2.isEmpty()) {
            t2.root.setParent(node);
            node.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }

    /** Removes the node at Position p and replaces it with its child, if any.*/
    public E remove(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        if(numChildren(p) == 2) {
            throw new IllegalArgumentException("p has two children");
        }
        Node<E> child = node.getLeft() != null ? node.getLeft() : node.getRight();
        if(child != null) {
            child.setParent(node.getParent()); // child’s grandparent becomes its parent
        }
        if(node == root) {
            root = child;
        } else {
            Node<E> parent = node.getParent();
            if(parent.getLeft() == node)
                parent.setLeft(child);
            else
                parent.setRight(child);
        }
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);  // our convention for defunct node
        E element = node.getElement();
        node.setElement(null);  // help garbage collection
        size--;
        return element;
    }
}
