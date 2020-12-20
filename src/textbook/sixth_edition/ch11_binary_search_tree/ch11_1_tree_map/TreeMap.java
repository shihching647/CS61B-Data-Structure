package textbook.sixth_edition.ch11_binary_search_tree.ch11_1_tree_map;

import textbook.sixth_edition.ch10_map.AbstractSortedMap;
import textbook.sixth_edition.ch7_list.Position;
import textbook.sixth_edition.ch8_tree.ch_8_3_linked_binary_tree.LinkedBinaryTree;
import textbook.sixth_edition.ch9_priorityQueue.Entry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TreeMap<K,V> extends AbstractSortedMap<K,V> {
    
    /** A specialized version of LinkedBinaryTree with support for balancing. */
    protected static class BalanceableBinaryTree<K,V> extends LinkedBinaryTree<Entry<K,V>> {

        //-------------- nested BSTNode class --------------
        // this extends the inherited LinkedBinaryTree.Node class
        protected static class BSTNode<E> extends Node<E> {
             int aux = 0;
             BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
                 super(e, parent, leftChild, rightChild);
             }
             public int getAux() { return aux;}
             public void setAux(int aux) { this.aux = aux;}
        }//--------- end of nested BSTNode class ---------

        // positional-based methods related to aux fiel
        public int getAux(Position<Entry<K,V>> p) {
            return ((BSTNode<Entry<K,V>>) p).getAux();
        }
        public void setAux(Position<Entry<K,V>> p, int value) {
            ((BSTNode<Entry<K,V>>) p).setAux(value);
        }

        // Override node factory function to produce a BSTNode (rather than a Node)
        @Override
        protected Node<Entry<K,V>> createNode(Entry<K,V> e, Node<Entry<K,V>> parent, Node<Entry<K,V>> left, Node<Entry<K,V>> right) {
            return new BSTNode<Entry<K,V>>(e, parent, left, right);
        }

        /** Rotates Position p above its parent. */
        public void rotate(Position<Entry<K,V>> p) {
            Node<Entry<K,V>> x = validate(p);
            Node<Entry<K,V>> y = x.getParent();
            Node<Entry<K,V>> z = y.getParent();

            if(z == null) {
                x = root;
                x.setParent(null);
            } else {
                relink(z, x, y == z.getLeft());
            }

            if(x == y.getLeft()) {
                relink(y, x.getRight(), true);
                relink(x, y, false);
            } else {
                relink(y, x.getLeft(), false);
                relink(x, y, true);
            }
        }

        /** Performs a trinode restructuring of Position x with its parent/grandparent. */
        public Position<Entry<K,V>> restructure(Position<Entry<K,V>> x) {
            Position<Entry<K,V>> y = parent(x);
            Position<Entry<K,V>> z = parent(y);
            if( (x == right(y) == (y == right(z)) )) {  // matching alignments
                rotate(y);
                return y;                               // y is new subtree root
            } else {                                    // opposite alignments
                rotate(x);
                rotate(x);
                return x;                               // x is new subtree root
            }
        }

        /** Relinks a parent node with its oriented child node. */
        private void relink(Node<Entry<K,V>> parent, Node<Entry<K,V>> child, boolean makeLeftChild) {
            child.setParent(parent);
            if(makeLeftChild) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
    }

    //--------------------rebalancing algorithms-------------------
    // A call to rebalanceInsert(p) is made from within the put method, after a new node is added to the tree at position p
    protected void rebalanceInsert(Position<Entry<K,V>> p) { }

    // A call to rebalanceDelete(p) is made from within the remove method, after a node is deleted from the tree:
    // position p identifies the child of the removed node that was promoted in its place.
    protected void rebalanceDelete(Position<Entry<K,V>> p) { }

    // A call to rebalanceAccess(p) is made by any call to get, put, or remove that does not result in a structural change.
    // Position p, which could be internal or external, represents the deepest node of the tree that was accessed during the operation.
    // This hook is specifically used by the splay tree structure (see Section 11.4) to restructure a tree
    // so that more frequently accessed nodes are brought closer to the root.
    protected void rebalanceAccess(Position<Entry<K,V>> p) { }
    //-------------------- end of rebalancing algorithms-------------------


    protected BalanceableBinaryTree<K,V> tree = new BalanceableBinaryTree<>();

    public TreeMap() {
        super();
        tree.addRoot(null);
    }

    public TreeMap(Comparator<K> comp) {
        super(comp);
        tree.addRoot(null);
    }

    public int size() {
        return (tree.size() - 1) / 2; // only internal nodes have entries
    }

    /* A series of protected methods that provide notational shorthands to wrap
     * operations on the underlying linked binary tree. For example, we support the
     * protected syntax root() as shorthand for tree.root() with the following utility:
     */
    protected Position<Entry<K,V>> root() { return tree.root(); }
    protected boolean isExternal(Position<Entry<K,V>> p) { return tree.isExternal(p);}
    protected boolean isInternal(Position<Entry<K,V>> p) { return tree.isInternal(p);}
    protected boolean isRoot(Position<Entry<K,V>> p) { return tree.isRoot(p);}
    protected Position<Entry<K,V>> left(Position<Entry<K,V>> p) { return tree.left(p);}
    protected Position<Entry<K,V>> right(Position<Entry<K,V>> p) { return tree.right(p);}
    protected Position<Entry<K,V>> parent(Position<Entry<K,V>> p) { return tree.parent(p);}
    protected Position<Entry<K,V>> sibling(Position<Entry<K,V>> p) { return tree.sibling(p);}
    protected Entry<K,V> set(Position<Entry<K,V>> p, Entry<K,V> entry) { return tree.set(p, entry);}
    protected Entry<K,V> remove(Position<Entry<K,V>> p) { return tree.remove(p); }

    /** Utility used when inserting a new entry at a leaf of the tree */
    private void expandExternal(Position<Entry<K,V>> p, Entry<K,V> entry) {
        tree.set(p, entry);
        tree.addLeft(p, null);
        tree.addRight(p, null);
    }

    /** Returns the position in p's subtree having given key (or else the terminal leaf).*/
    private Position<Entry<K,V>> treeSearch(Position<Entry<K,V>> p, K key) {
        if(isExternal(p)) {
            return p;       // key not found; return the final leaf
        }
        int comp = compare(key, p.getElement());
        if(comp == 0) {
            return p;
        } else if(comp < 0) {
            return treeSearch(left(p), key);
        } else {
            return treeSearch(right(p), key);
        }
    }

    public V get(K key) throws IllegalArgumentException{
        checkKey(key);     //may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        rebalanceAccess(p);         // hook for balanced tree subclasses
        if(isExternal(p)) return null;
        return p.getElement().getValue();
    }

    public V put(K key, V value) throws IllegalArgumentException{
        checkKey(key);      //may throw IllegalArgumentException
        Entry<K,V> newEntry = new MapEntry<>(key, value);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isExternal(p)) {     //key is new
            expandExternal(p, newEntry);
            rebalanceInsert(p);
            return null;
        } else {                // replacing existing key
            V old = p.getElement().getValue();
            set(p, newEntry);
            rebalanceAccess(p);  //no insertion, using rebalanceAccess
            return old;
        }
    }

    /** Removes the entry having key k (if any) and returns its associated value. */
    public V remove(K key) throws IllegalArgumentException{
        checkKey(key);      //may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isExternal(p)) {
            rebalanceAccess(p);
            return null;
        } else {
            V old = p.getElement().getValue();
            if(isInternal(left(p)) && isInternal(right(p))) {
                Position<Entry<K,V>> replacement = treeMax(left(p));
                set(p, replacement.getElement());
                p = replacement;
            }// now p has at most one child that is an internal node
            Position<Entry<K,V>> leaf = isExternal(left(p)) ? right(p) : left(p);
            Position<Entry<K,V>> sib = sibling(leaf);
            remove(leaf); //Have to remove leaf, make sure node p only have one child.
            remove(p);
            rebalanceDelete(sib);   //sib is promoted in p's place
            return old;
        }
    }

    /** Returns the position with the maximum key in subtree rooted at Position p. */
    protected Position<Entry<K,V>> treeMax(Position<Entry<K,V>> p) {
        Position<Entry<K,V>> walk = p;
        while(isInternal(walk)) {
            walk = right(walk);
        }
        return parent(walk);
    }

    protected Position<Entry<K,V>> treeMin(Position<Entry<K,V>> p) {
        Position<Entry<K,V>> walk = p;
        if(isInternal(walk)) {
            walk = left(walk);
        }
        return parent(walk);
    }

    public Entry<K,V> lastEntry() {
        if(isEmpty()) return null;
        return treeMax(root()).getElement();
    }

    public Entry<K,V> firstEntry() {
        if(isEmpty()) return null;
        return treeMin(root()).getElement();
    }

    /** Returns the entry with greatest key less than or equal to given key (if any). */
    public Entry<K,V> floorEntry(K key) throws IllegalArgumentException{
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isInternal(p)) return p.getElement(); //exactly match
        while(!isRoot(p)) {
            if(p == right(parent(p)))  //p is the right child
                return parent(p).getElement(); //parent has next lesser key
            else
                p = parent(p);
        }
        return null;  // no such floor exists
    }

    public Entry<K,V> ceilingEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isInternal(p)) return p.getElement(); //exactly match
        while(!isRoot(p)) {
            if(p == left(parent(p)))   //p is the left child
                return parent(p).getElement();
            else
                p = parent(p);
        }
        return null;
    }
    
    /** Returns the entry with greatest key strictly less than given key (if any). */
    public Entry<K,V> lowerEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isInternal(p) && isInternal(left(p)))
            return treeMax(left(p)).getElement(); // this is the predecessor to p
        // otherwise, we had failed search, or match with no left child
        while(!isRoot(p)) {
            if(p == right(parent(p)))
                return parent(p).getElement();
            else
                p = parent(p);
        }
        return null; // no such lesser key exists
    }

    public Entry<K,V> higherEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isInternal(p) && isInternal(right(p)))
            return treeMin(right(p)).getElement();
        while (!isRoot(p)) {
            if(p == left(parent(p)))
                return parent(p).getElement();
            else
                p = parent(p);
        }
        return null;
    }

    public Iterable<Entry<K,V>> entrySet() {
        List<Entry<K,V>> buffer = new ArrayList<>(size());
        for(Position<Entry<K,V>> p : tree.inOrder())
            if(isInternal(p)) {
                buffer.add(p.getElement());
            }
        return buffer;
    }

    public Iterable<Entry<K,V>> subMap(K fromKey, K toKey) {
        List<Entry<K,V>> buffer = new ArrayList<>(size());
        if(compare(fromKey, toKey) < 0)
            subMapRecurse(fromKey, toKey, root(), buffer);
        return buffer;
    }

    private void subMapRecurse(K fromKey, K toKey, Position<Entry<K,V>> p, List<Entry<K,V>> buffer) {
        if (isInternal(p)) {
            if(compare(p.getElement(), fromKey) < 0)
                // p's key is less than fromKey, so any relevant entries are to the right
                subMapRecurse(fromKey, toKey, right(p), buffer);
            else {
                subMapRecurse(fromKey, toKey, left(p), buffer); // first consider left subtree
                if(compare(p.getElement(), toKey) < 0) {        // p is within range
                    buffer.add(p.getElement());                 // so add it to buffer, and consider
                    subMapRecurse(fromKey, toKey, right(p), buffer);// right subtree as well
                }
            }
        }
    }
}
