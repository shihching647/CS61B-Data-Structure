package homework.hw4.list;

public class LockDList extends DList{

    public void lockNode(DListNode node) {
        if(size == 0){
            return;
        } else {
            LockDListNode lockNode = new LockDListNode(node);
            lockNode.isLock = true;
            DListNode prevNode = node.prev;
            DListNode nextNode = node.next;
            node.prev = null;
            node.next = null;
            prevNode.next = lockNode;
            nextNode.prev = lockNode;
        }
    }

    @Override
    public void remove(DListNode node){
        if(node instanceof LockDListNode && ((LockDListNode) node).isLock){
            return;
        }
        super.remove(node);
    }

    public static void main(String[] args) {
        LockDList lockDList = new LockDList();
        System.out.println(lockDList);

        lockDList.insertFront(0);
        System.out.println(lockDList + " size = " + lockDList.size);

        lockDList.lockNode(lockDList.front());
        lockDList.remove(lockDList.front());
        System.out.println(lockDList + " size = " + lockDList.size);

        lockDList.insertBack(1);
        System.out.println(lockDList + " size = " + lockDList.size);

        lockDList.remove(lockDList.back());
        System.out.println(lockDList + " size = " + lockDList.size);

        lockDList.remove(lockDList.back());
        System.out.println(lockDList + " size = " + lockDList.size);
    }
}
