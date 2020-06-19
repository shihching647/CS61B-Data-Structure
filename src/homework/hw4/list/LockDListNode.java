package homework.hw4.list;

public class LockDListNode extends DListNode{

    protected boolean isLock;

     /**
     *  LockDListNode() constructor.
     *  @param i the item to store in the node.
     *  @param p the node previous to this node.
     *  @param n the node following this node.
     */
     LockDListNode(Object i, DListNode p, DListNode n) {
        super(i, p, n);
        isLock = false;
    }

    LockDListNode(DListNode node) {
        super(node.item, node.prev, node.next);
        isLock = false;
    }
}
