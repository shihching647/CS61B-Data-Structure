package textbook.sixth_edition.ch11_binary_search_tree.ch11_2_balanced_tree;

import textbook.sixth_edition.ch11_binary_search_tree.ch11_1_tree_map.TreeMap;
import textbook.sixth_edition.ch7_list.Position;
import textbook.sixth_edition.ch9_priorityQueue.Entry;

import java.util.Comparator;

public class RBTreeMap<K,V> extends TreeMap<K,V> {

    /** Constructs an empty map using the natural ordering of keys. */
    public RBTreeMap() { super();}
    /** Constructs an empty map using the given comparator to order keys. */
    public RBTreeMap(Comparator<K> comp) { super(comp);}

    // we use the inherited aux field with convention that 0=black and 1=red
    // (note that new leaves will be black by default, as aux=0)
    private boolean isBlack(Position<Entry<K,V>> p) { return tree.getAux(p) == 0;}
    private boolean isRed(Position<Entry<K,V>> p) { return tree.getAux(p) == 1;}
    private void makeBlack(Position<Entry<K,V>> p) { tree.setAux(p, 0);}
    private void makeRed(Position<Entry<K,V>> p) { tree.setAux(p, 1);}
    private void setColor(Position<Entry<K,V>> p, boolean toRed) {
        tree.setAux(p, toRed ? 1 : 0);
    }

    @Override
    protected void rebalanceInsert(Position<Entry<K,V>> p) {
        if(!isRoot(p)) {
            makeRed(p);
            resolveRed(p);
        }
    }

    private void resolveRed(Position<Entry<K,V>> p) {
        Position<Entry<K,V>> parent, uncle, middle, grand;
        parent = parent(p);
        if(isRed(parent)) {         // double-red problem exists
            uncle = sibling(p);
            if(isBlack(uncle)) {    // Case 1: misshapen 4-node, do trinode restructuring
                middle = restructure(p);
                makeBlack(middle);
                makeRed(left(middle));
                makeRed(right(middle));
            } else {                // Case 2: overfull 5-node, perform recoloring
                makeBlack(parent);
                makeBlack(uncle);
                grand = parent(parent);
                if(!isRoot(grand)) {
                    makeRed(grand);
                    resolveRed(grand); // grandparent becomes red, recur at red grandparent
                }
            }
        }
    }

    @Override
    protected void rebalanceDelete(Position<Entry<K,V>> p) {
        if(isRed(p)) { // deleted parent was black
            makeBlack(p); // so this restores black depth
        } else if(!isRoot(p)){
            Position<Entry<K,V>> sib = sibling(p);
            if(isInternal(sib) && (isBlack(sib) || isInternal(left(sib))))
                remedyDoubleBlack(p);
        }
    }

    /** Remedies a presumed double-black violation at the given (nonroot) position. */
    private void remedyDoubleBlack(Position<Entry<K,V>> p) {
        Position<Entry<K,V>> z = parent(p);
        Position<Entry<K,V>> y = sibling(p);
        if(isBlack(y)) {
            if(isRed(left(y)) || isRed(right(y))) {  // Case 1: trinode restructuring
                Position<Entry<K,V>> x = isRed(left(y)) ? left(y) : right(y);
                Position<Entry<K,V>> middle = restructure(x);
                setColor(middle, isRed(z));         // root of restructured subtree gets z's old color
                makeBlack(left(middle));
                makeBlack(right(middle));
            } else {                                // Case 2: recoloring
                makeRed(y);
                if(isRed(z)) {
                    makeBlack(z);                   // problem is resolved
                } else if(!isRoot(z)){
                    remedyDoubleBlack(z);           // propagate the problem
                }
            }
        } else {                                    // Case 3: reorient 3-node
            rotate(y);
            makeBlack(y);
            makeRed(z);
            remedyDoubleBlack(p);                   // restart the process at p
        }
    }

    public static void main(String[] args) {
        RBTreeMap<Integer,String> map = new RBTreeMap<>();
        map.put(4, "4");
        map.put(7, "7");
        map.put(12, "12");
        map.put(15, "15");
        map.put(3, "3");
        map.put(5, "5");
        map.put(14, "14");
        map.put(18, "18");
        map.put(16, "16");
        map.put(17, "17");
        System.out.println(map);
    }
}
