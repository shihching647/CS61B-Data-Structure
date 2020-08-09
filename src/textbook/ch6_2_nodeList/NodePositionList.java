package textbook.ch6_2_nodeList;

public class NodePositionList<E> implements PositionList<E>{
    private int numOfElements;
    private DNode<E> header, trailer; //Special sentinels

    public NodePositionList() {
        numOfElements = 0;
        header = new DNode<E>(null, null, null);
        trailer = new DNode<E>(header, null, null);
        header.setNext(trailer);
    }

    @Override
    public int size() {
        return numOfElements;
    }

    @Override
    public boolean isEmpty() {
        return numOfElements == 0;
    }

    @Override
    public Position<E> first() throws EmptyListException{
        if(isEmpty())
            throw new EmptyListException("List is empty.");
        else
            return header.getNext();
    }

    @Override
    public Position<E> last() throws EmptyListException{
        if(isEmpty())
            throw new EmptyListException("List is empty.");
        else
            return trailer.getPrev();
    }

    @Override
    public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
        DNode<E> temp = checkPosition(p);
        if(temp.getPrev() == header) {
            throw new BoundaryViolationException("Cannot advance past the beginning of the list");
        }
        return temp.getPrev();
    }

    @Override
    public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
        DNode<E> temp = checkPosition(p);
        if(temp.getNext() == trailer) {
            throw new BoundaryViolationException("Cannot advance past the end of the list");
        }
        return temp.getNext();
    }

    @Override
    public void addFirst(E e) {
        //不可直接用addAfter(header, e), 因為addAfter會呼叫checkPosition方法
        DNode<E> temp = header.getNext();
        DNode<E> newNode = new DNode<>(header, temp, e);
        header.setNext(newNode);
        temp.setPrev(newNode);
        numOfElements++;
    }

    @Override
    public void addLast(E e) {
        DNode<E> temp = trailer.getPrev();
        DNode<E> newNode = new DNode<>(temp, trailer, e);
        temp.setNext(newNode);
        trailer.setPrev(newNode);
        numOfElements++;
    }

    @Override
    public void addBefore(Position<E> p, E e) throws InvalidPositionException {
        DNode<E> node = checkPosition(p);
        DNode<E> prevNode = node.getPrev();
        DNode<E> newNode = new DNode<>(prevNode, node, e);
        prevNode.setNext(newNode);
        node.setPrev(newNode);
        numOfElements++;
    }

    @Override
    public void addAfter(Position<E> p, E e) throws InvalidPositionException {
        DNode<E> node = checkPosition(p);
        DNode<E> nextNode = node.getNext();
        DNode<E> newNode = new DNode<>(node, nextNode, e);
        node.setNext(newNode);
        nextNode.setPrev(newNode);
        numOfElements++;
    }

    /*Replaces the element stored at the given node, returning the old element*/
    @Override
    public E set(Position<E> p, E e) throws InvalidPositionException {
        DNode<E> temp = checkPosition(p);
        E result = temp.element();
        temp.setElement(e);
        return result;
    }

    /*Removes a node from the list, returning the element stored there*/
    @Override
    public E remove(Position<E> p) throws InvalidPositionException {
        DNode<E> temp = checkPosition(p);
        E result = temp.element();
        DNode<E> prevNode = temp.getPrev();
        DNode<E> nextNode = temp.getNext();
        prevNode.setNext(nextNode);
        nextNode.setPrev(prevNode);
        numOfElements--;
        //unlink the position from the list and make it invalid
        temp.setNext(null);
        temp.setPrev(null);
        return result;
    }

    //Check if a position is valid for this list and converts it to
    //DNode if it's valid; O(1) time
    protected DNode<E> checkPosition(Position<E> p) throws InvalidPositionException{
        if(p == null) {
            throw new InvalidPositionException("Null position passed to NodeList");
        }
        if(p == header) {
            throw new InvalidPositionException("The header node is not a valid position");
        }
        if(p == trailer) {
            throw new InvalidPositionException("The trailer node is not a valid position");
        }
        try{
            DNode<E> temp = (DNode<E>) p;
            if(temp.getPrev() == null || temp.getNext() == null) { //因為remove的時候會把prev, next設成null
                throw new InvalidPositionException("Position dose not belong to a NodeList");
            }
            return  temp;
        } catch(ClassCastException e) {
            throw new InvalidPositionException("Position is of wrong type for this list");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        DNode<E> temp = header.getNext();
        if(size() > 0) sb.append(temp.element());
        if(size() > 1) {
            temp = temp.getNext();
            while(temp != trailer) {
                sb.append(", " + temp.element());
                temp = temp.getNext();
            }
        }
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        NodePositionList<Integer> list= new NodePositionList<>();
        list.addFirst(0);
        list.addLast(9999);
        System.out.println("list = " + list + "\tisEmpty() = " + list.isEmpty() + "\tsize = " + list.size());
        Position<Integer> firstNode = list.first();
        System.out.println(firstNode.element());
        list.set(firstNode, 1);
        System.out.println(firstNode.element());
        list.remove(list.last());
        System.out.println("list = " + list + "\tisEmpty() = " + list.isEmpty() + "\tsize = " + list.size());
        list.remove(list.last());
        System.out.println("list = " + list + "\tisEmpty() = " + list.isEmpty() + "\tsize = " + list.size());
        list.addFirst(2);
        list.addBefore(list.first(), 0);
        System.out.println("list = " + list + "\tisEmpty() = " + list.isEmpty() + "\tsize = " + list.size());
        list.addAfter(list.first(), 1);
        System.out.println("list = " + list + "\tisEmpty() = " + list.isEmpty() + "\tsize = " + list.size());
    }
}
