package textbook.sixth_edition.ch14_graph.ch14_7_minimun_spanning_trees;

import textbook.sixth_edition.ch7_list.Position;

/**
 * A Union-Find structure for maintaining disjoint sets.
 * When using the tree-based partition representation with both
 * union-by-size and path compression, performing a series of k makeCluster, union,
 * and find operations on an initially empty partition involving at most n elements
 * takes O(k log∗ n) time
 **/

public class Partition<E> {

    private class Locator<E> implements Position<E> {
        public int size;
        public E element;
        public Locator<E> parent;

        public Locator(E element) {
            this.element = element;
            size = 1;
            parent = this; // convention for a cluster leader
        }

        public E getElement() {
            return element;
        }

        private boolean validate(Partition<E> universe) {
            return Partition.this == universe;
        }
    }

    /** Makes a new cluster containing element e and returns its position. */
    public Position<E> makeCluster(E e) {
        return new Locator<>(e);
    }

    /**
     * Finds the cluster containing the element identified by Position p
     * and returns the Position of the cluster's leader.
     */
    public Position<E> find(Position<E> p) {
        Locator<E> loc = validate(p);
        if (loc.parent != loc) {
            loc.parent = (Locator<E>) find(loc.parent); // overwrite parent after recursion
        }
        return loc.parent;
    }

    /** Merges the clusters containing elements with positions p and q (if distinct). */
    public void union(Position<E> p, Position<E> q) {
        Locator<E> a = (Locator<E>) find(p);
        Locator<E> b = (Locator<E>) find(q);
        if (a != b) {
            if (a.size > b.size) {
                b.parent = a;
                a.size += b.size;
            } else {
                a.parent = b;
                b.size += a.size;
            }
        }
    }

    private Locator<E> validate(Position<E> pos) {
        if (!(pos instanceof Locator)) throw new IllegalArgumentException("Invalid position");
        Locator<E> loc = (Locator<E>) pos;
        if (!loc.validate(this))
            throw new IllegalArgumentException("Position does not belong to this structure");
        return loc;
    }
}
