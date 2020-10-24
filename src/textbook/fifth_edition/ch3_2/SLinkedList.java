package textbook.fifth_edition.ch3_2;

public class SLinkedList {
    private Node head;
    private Node tail;
    private long size;

    public SLinkedList(){
        this.tail = null;
        this.head = null;
        size = 0;
    }

    public void addFirst(Node node){
        node.setNext(head);
        head = node;
        size++;
        if(size == 1){
            tail = head;
        }
    }

    public void addLast(Node node){
        if(size == 0){
            head = tail = node;
            size++;
            return;
        }
        node.setNext(null);
        tail.setNext(node);
        tail = node;
        size++;
        if(size == 1){
            head = tail;
        }
    }

    public Node removeFirst(){
        if(head == null){
            throw new IllegalStateException("List is empty");
        }
        Node temp = head;
        head = head.getNext();
        temp.setNext(null);
        size--;
        if(size == 1){
            tail = head;
        }
        return temp;
    }

    public Node removeLast(){
        if(head == null){
            throw new IllegalStateException("List is empty");
        }
        if(size == 1){
            Node temp = head;
            head = tail = null;
            size--;
            return temp;
        }
        Node temp = head;
        while(temp.getNext() != tail){
            temp = temp.getNext();
        }
        tail = temp;
        tail.setNext(null);
        size--;
        return temp;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if(head != null){
            Node temp = head;
            sb.append(head.getElement());
            while(temp != tail){
                temp = temp.getNext();
                sb.append(", " + temp.getElement());
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args){
        SLinkedList sList = new SLinkedList();
        for(int i = 0; i < 1; i++){
            if(i < 5){
                sList.addFirst(new Node(String.valueOf(i), null));
            }else{
                sList.addLast(new Node(String.valueOf(i), null));
            }
        }
        System.out.println(sList);
        sList.removeFirst();
        System.out.println(sList);
        sList.removeFirst();
        System.out.println(sList);
        sList.removeLast();
        System.out.println(sList);
    }
}
