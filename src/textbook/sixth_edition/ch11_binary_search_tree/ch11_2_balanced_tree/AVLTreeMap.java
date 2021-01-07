package textbook.sixth_edition.ch11_binary_search_tree.ch11_2_balanced_tree;

import textbook.sixth_edition.ch11_binary_search_tree.ch11_1_tree_map.TreeMap;
import textbook.sixth_edition.ch7_list.Position;
import textbook.sixth_edition.ch9_priorityQueue.Entry;

import java.util.Comparator;
import java.util.Random;

public class AVLTreeMap<K,V> extends TreeMap<K,V> {

    public AVLTreeMap(){ super(); }

    public AVLTreeMap(Comparator comp) { super(comp);}

    /** Returns the height of the given tree position. */
    protected int height(Position<Entry<K,V>> p) {
        return tree.getAux(p);
    }

    /** Recomputes the height of the given position based on its children's heights. */
    protected void recomputeHeight(Position<Entry<K,V>> p) {
        tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));
    }
    
    /** Returns whether a position has balance factor between −1 and 1 inclusive. */
    protected boolean isBalanced(Position<Entry<K,V>> p) {
        return Math.abs(height(left(p)) - height(right(p))) <= 1;
    }
    
    /**
     *  Utility used to rebalance after an insert or removal operation. This traverses the
     *  path upward from p, performing a trinode restructuring when imbalance is found,
     *  continuing until balance is restored.
     */
    protected void rebalance(Position<Entry<K,V>> p) {
        int oldHeight, newHeight;
        do {
            oldHeight = height(p);
            if(!isBalanced(p)) {  // imbalance detected
                // perform trinode restructuring, setting p to resulting root,
                // and recompute new local heights after the restructuring
                p = restructure(tallerChild(tallerChild(p)));
                recomputeHeight(left(p));
                recomputeHeight(right(p));
            }
            recomputeHeight(p);
            newHeight = height(p);
            p = parent(p);
        } while(oldHeight != newHeight && p != null);
    }


    protected Position<Entry<K,V>> tallerChild(Position<Entry<K,V>> p) {
        if(height(left(p)) > height(right(p))) return left(p);
        if(height(left(p)) < height(right(p))) return right(p);
        // equal height children; break tie while matching parent's orientation
        if(isRoot(p)) { //初步測試不可能會發生！！！
            System.out.println("********************發生了！！**************************");
            return left(p);
        }
        if(p == left(parent(p))) return left(p);
        else return right(p);
    }

    @Override
    protected void rebalanceInsert(Position<Entry<K,V>> p) {
        rebalance(p);
    }

    @Override
    protected void rebalanceDelete(Position<Entry<K,V>> p) {
        if(!isRoot(p))
            rebalance(p);
    }

    public static void main(String[] args) {
        //測試tallerChild方法, 是否可能發生p是root的情況
        Random random = new Random();
//        random.setSeed(1);
        for(int j = 0; j <= 10000; j++) {
            TreeMap<Integer,String> map = new SplayTreeMap<>();
            for(int i = 0; i < 10000; i++) {
                map.put(random.nextInt(100000), "TEST");
            }
            System.out.println(map.size());
        }
    }
}
