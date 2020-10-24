package textbook.fifth_edition.ch7_3_binaryTree;

import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.*;
import textbook.fifth_edition.ch7_1_generalTree.EmptyTreeException;
import textbook.fifth_edition.ch7_1_generalTree.Tree;

import java.util.Iterator;

public class LinkedBinaryTree<E> implements BinaryTree<E> {
    protected BTPosition<E> root;
    protected int size;

    // Creates an empty binary tree
    public LinkedBinaryTree() {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /* Returns an iterator of the elements stored at the nodes */
    @Override
    public Iterator<E> iterator() {
        PositionList<E> list = new NodePositionList<>();
        for(Position<E> position: positions()) {
            list.addLast(position.element());
        }
        return list.iterator();
    }

    /*Returns an iterable collection of the tree nodes*/
    @Override
    public Iterable<Position<E>> positions() {
        PositionList<Position<E>> positions = new NodePositionList<>();
        if(size() != 0) {
            preorderPositions(root(), positions);
        }
        return positions;
    }

    // Replaces the element at a node
    @Override
    public E replace(Position<E> node, E e) throws InvalidPositionException {
        BTPosition<E> nodeBT = checkPosition(node);
        E temp = nodeBT.element();
        nodeBT.setElement(e);
        return temp;
    }

    @Override
    public Position<E> root() throws EmptyTreeException {
        if(root == null) {
            throw new EmptyTreeException("The tree is empty.");
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> node) throws InvalidPositionException, BoundaryViolationException {
        BTPosition<E> nodeBT = checkPosition(node);
        Position<E> parentPos = nodeBT.getParent();
        if(parentPos == null) {
            throw new BoundaryViolationException("No parent");
        }
        return parentPos;
    }

    @Override
    public Iterable<Position<E>> children(Position<E> node) throws InvalidPositionException {
        PositionList<Position<E>> children = new NodePositionList<>();
        if(hasLeft(node)) {
            children.addLast(left(node));
        }
        if(hasRight(node)) {
            children.addLast(right(node));
        }
        return children;
    }

    @Override
    public boolean isInternal(Position<E> node) throws InvalidPositionException {
        checkPosition(node);
        return (hasLeft(node) || hasRight(node));
    }

    @Override
    public boolean isExternal(Position<E> node) throws InvalidPositionException {
        checkPosition(node);
        return (!hasLeft(node) && !hasRight(node));
    }

    @Override
    public boolean isRoot(Position<E> node) throws InvalidPositionException {
        checkPosition(node);
        return node == root();
    }

    @Override
    public Position<E> left(Position<E> node) throws InvalidPositionException, BoundaryViolationException {
        BTPosition<E> nodeBT = checkPosition(node);
        Position<E> leftPos = nodeBT.getLeft();
        if(leftPos == null) {
            throw new BoundaryViolationException("No left child");
        }
        return leftPos;
    }

    @Override
    public Position<E> right(Position<E> node) throws InvalidPositionException, BoundaryViolationException {
        BTPosition<E> nodeBT = checkPosition(node);
        Position<E> rightPos = nodeBT.getRight();
        if(rightPos == null) {
            throw new BoundaryViolationException("No right child");
        }
        return rightPos;
    }

    @Override
    public boolean hasLeft(Position<E> node) throws InvalidPositionException {
        BTPosition<E> nodeBT = checkPosition(node);
        return (nodeBT.getLeft() != null);
    }

    @Override
    public boolean hasRight(Position<E> node) throws InvalidPositionException {
        BTPosition<E> nodeBT = checkPosition(node);
        return (nodeBT.getRight() != null);
    }

    //Additional accessor method

    //Return the sibling of a node
    public Position<E> sibling(Position<E> node) throws InvalidPositionException, BoundaryViolationException{
        BTPosition<E> nodeBT = checkPosition(node);
        BTPosition<E> parent = nodeBT.getParent();
        if(parent != null) {
            BTPosition<E> sibling;
            BTPosition<E> leftChild = nodeBT.getLeft();
            if(leftChild == node) {
                sibling = nodeBT.getRight();
            } else {
                sibling = nodeBT.getLeft();
            }
            if(sibling != null) {
                return sibling;
            }
        }
        throw new BoundaryViolationException("No sibling");
    }

    //Additional update method
    //Adds a root node to an empty tree
    public Position<E> addRoot(E element) throws NonEmptyTreeException{
        if(!isEmpty())
            throw new NonEmptyTreeException("Tree already has a root");
        root = createNode(element, null, null, null);
        size = 1;
        return root;
    }

    //Insert a left child at a given node
    public Position<E> insertLeft(Position<E> node, E element) throws InvalidPositionException{
        BTPosition<E> nodeBT = checkPosition(node);
        BTPosition<E> leftChild = nodeBT.getLeft();
        if(leftChild != null)
                throw new InvalidPositionException("Node already has a left child");
        leftChild = createNode(element, nodeBT, null, null);
        nodeBT.setLeft(leftChild);
        size++;
        return leftChild;
    }

    //Insert a right child at a given node
    public Position<E> insertRight(Position<E> node, E element) throws InvalidPositionException{
        BTPosition<E> nodeBT = checkPosition(node);
        BTPosition<E> rightChild = nodeBT.getRight();
        if(rightChild != null)
            throw new InvalidPositionException("Node already has a right child");
        rightChild = createNode(element, nodeBT, null, null);
        nodeBT.setRight(rightChild);
        size++;
        return rightChild;
    }

    //Removes a node with zero or one child.
    public E remove(Position<E> node) throws InvalidPositionException{
        BTPosition<E> nodeBT = checkPosition(node);
        BTPosition<E> leftChild = nodeBT.getLeft();
        BTPosition<E> rightChild = nodeBT.getRight();
        if(leftChild != null && rightChild != null)
            throw new InvalidPositionException("Cannot remove node with two children.");
        BTPosition<E> child; //the only child of node, if any
        if(leftChild != null) {
            child = leftChild;
        } else if(rightChild != null) {
            child = rightChild;
        } else {
            child = null;
        }
        //the node is root
        if(nodeBT == root) {
            if(child != null) {
                child.setParent(null);
            }
            root = child;
        } else { //the node is not root
            BTPosition<E> parent = nodeBT.getParent();
            if(child != null) {
                child.setParent(parent);
            }
            //判斷為left or right
            if(parent.getLeft() == nodeBT) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
        size--;
        return nodeBT.element();
    }

    // Attaches two trees to be subtrees of an external node.
    public void attach(Position<E> node, BinaryTree<E> leftTree, BinaryTree<E> rightTree) throws InvalidPositionException{
        BTPosition<E> nodeBT = checkPosition(node);
        if(isInternal(node)) {
            throw new InvalidPositionException("Cannt attach from internal node");
        }
        if(!leftTree.isEmpty()) {
            BTPosition<E> leftRoot = checkPosition(leftTree.root());
            nodeBT.setLeft(leftRoot);
            leftRoot.setParent(nodeBT);
        }
        if(!rightTree.isEmpty()) {
            BTPosition<E> rightRoot = checkPosition(rightTree.root());
            nodeBT.setRight(rightRoot);
            rightRoot.setParent(nodeBT);
        }
        size = size + leftTree.size() + rightTree.size();
    }

    //toString()
    //preOrder
    public static <E> String toStringPreorder(Tree<E> tree, Position<E> node) {
        String str = node.element().toString();
        for(Position<E> child : tree.children(node)) {
            str += toStringPreorder(tree, child);
        }
        return str;
    }

    //postOrder
    public static <E> String toStringPostorder(Tree<E> tree, Position<E> node) {
        String str = "";
        for(Position<E> child : tree.children(node)) {
            str += toStringPostorder(tree, child);
        }
        str += node.element().toString();
        return str;
    }

    //InOrder(for BinaryTree)
    public static <E> String toStringInorder(BinaryTree<E> binaryTree, Position<E> node) {
        String str = "";
        if(binaryTree.hasLeft(node)) {
            str += toStringInorder(binaryTree, binaryTree.left(node));
        }
        str += node.element().toString();
        if(binaryTree.hasRight(node)) {
            str += toStringInorder(binaryTree, binaryTree.right(node));
        }
        return str;
    }

    // If node is a good binary tree node, cast to BTPosition, else throw exception.
    private BTPosition<E> checkPosition(Position<E> node) throws InvalidPositionException{
        if(!(node instanceof BTPosition)) {
            throw new InvalidPositionException("The position is invalid");
        }
        return (BTPosition<E>) node;
    }

    // Creates a list storing the nodes in the subtree of a node, ordered according to the
    // preorder travesal of the subtree.
    private PositionList<Position<E>> preorderPositions(Position<E> node, PositionList<Position<E>> pos) throws InvalidPositionException{
        pos.addLast(node);
        for(Position<E> childNode: children(node)) {
            preorderPositions(childNode, pos);
        }
        return pos;
    }

    //Creates a new binary tree node
    private BTPosition<E> createNode(E element, BTPosition<E> parent, BTPosition<E> left, BTPosition<E> right) {
        return new BTNode<>(element, parent, left, right);
    }
}
