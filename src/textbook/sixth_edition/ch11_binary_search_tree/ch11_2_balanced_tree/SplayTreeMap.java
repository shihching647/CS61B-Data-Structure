package textbook.sixth_edition.ch11_binary_search_tree.ch11_2_balanced_tree;

import textbook.sixth_edition.ch11_binary_search_tree.ch11_1_tree_map.TreeMap;
import textbook.sixth_edition.ch7_list.Position;
import textbook.sixth_edition.ch9_priorityQueue.Entry;

import java.util.Comparator;

public class SplayTreeMap<K,V> extends TreeMap<K,V> {

    public SplayTreeMap() { super(); }

    public SplayTreeMap(Comparator comp) { super(comp); }

    /** Utility used to rebalance after a map operation. */
    protected void splay(Position<Entry<K,V>> p) {
        while (!isRoot(p)) {
            Position<Entry<K,V>> parent = parent(p);
            Position<Entry<K,V>> grand = parent(parent);
            if(grand == null) { //zig
                rotate(p);
            } else {
                if( (p == left(parent)) == (parent == left(grand))) { //zig-zig
                    rotate(parent);
                    rotate(p);
                } else {
                    rotate(p);
                    rotate(p);
                }
            }
        }
    }

    @Override
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
        splay(p);
    }

    @Override
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
        if(!isRoot(p))
            splay(p);
    }

    @Override
    protected void rebalanceAccess(Position<Entry<K, V>> p) {
        if(isExternal(p))
            splay(parent(p));
        if(p != null)
            splay(p);
    }
}
