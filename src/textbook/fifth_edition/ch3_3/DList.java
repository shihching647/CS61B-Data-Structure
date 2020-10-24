package textbook.fifth_edition.ch3_3;

public class DList{
    private DNode header;
    private DNode trailer;
    private int size;

    public DList() {
        size = 0;
        header = new DNode(null, null, null);
        trailer = new DNode(null, header, null);
        header.setNext(trailer);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public DNode getFirst() throws IllegalStateException {
        if(isEmpty()) throw new IllegalStateException("List is empty");
        return header.getNext();
    }

    public DNode getLast() throws IllegalStateException {
        if(isEmpty()) throw new IllegalStateException("List is empty");
        return trailer.getPrev();
    }

    public DNode getPrev(DNode node) throws IllegalArgumentException {
        if(node == header) throw new IllegalArgumentException("Cannot move back past the header of the list");
        return node.getPrev();
    }

    public DNode getNext(DNode node) throws IllegalArgumentException {
        if(node == trailer) throw new IllegalArgumentException("Cannot move forward past the trailer of the list");
        return node.getNext();
    }

    public void addBefore(DNode positionNode, DNode addedNode) throws IllegalArgumentException {
        DNode temp = positionNode.getPrev(); //may throw an IllegalArgumentException
        addedNode.setNext(positionNode);
        addedNode.setPrev(temp);
        temp.setNext(addedNode);
        positionNode.setPrev(addedNode);
        size++;
    }

    public void addAfter(DNode positionNode, DNode addedNode) throws IllegalArgumentException {
        DNode temp = positionNode.getNext();
        addedNode.setPrev(positionNode);
        addedNode.setNext(temp);
        positionNode.setNext(addedNode);
        temp.setPrev(addedNode);
        size++;
    }

    public void addFirst(DNode node) {
        addAfter(header, node);
    }

    public void addLast(DNode node) {
        addBefore(trailer, node);
    }

    public void remove(DNode node) {
        DNode prev = node.getPrev(); //may throw an IllegalArgumentException
        DNode next = node.getNext(); //may throw an IllegalArgumentException
        prev.setNext(next);
        node.setPrev(null);
        node.setNext(null);
        next.setPrev(prev);
        size--;
    }

    /** Returns whether a given node has a previous node */
    public boolean hasPrev(DNode node) {
        return (node != header);
    }

    /** Returns whether a given node has a next node */
    public boolean hasNext(DNode node) {
        return (node != trailer);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        DNode cur = header.getNext();
        while(cur != trailer) {
            sb.append(cur.getElement());
            cur = cur.getNext();
            if(cur != trailer) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        DList list = new DList();
        DNode head = new DNode("0", null, null);
        list.addFirst(head);
        System.out.println("List is empty? " + list.isEmpty());
        System.out.println(list + " with size = " + list.size());

        DNode positionNode = head;
        for (int i = 1; i < 10; i++) {
            DNode addedNode = new DNode(String.valueOf(i), null, null);
            list.addAfter(positionNode, addedNode); //也可直接list.addLast()
            positionNode = addedNode;
        }

        System.out.println(list);
        System.out.println("List第一個元素: " + list.getFirst().getElement());
        System.out.println("List最後一個元素: " + list.getLast().getElement());


        System.out.println("最後一個node有沒有nextNode? " + list.hasNext(list.getLast()));
        System.out.println("最後一個node有沒有prevNode? " + list.hasPrev(list.getLast()));
        list.remove(list.getFirst());
        list.remove(list.getLast());
        System.out.println(list);

        DNode newNode = new DNode(String.valueOf("100"), null, null);
        list.addLast(newNode);
        System.out.println(list);
        System.out.println(list.getPrev(newNode).getElement());

        DNode newNode2 = new DNode(String.valueOf("1000"), null, null);
        list.addFirst(newNode2);
        System.out.println(list);
        System.out.println(list.getNext(newNode2).getElement());
    }

}
