package textbook.fifth_edition.ch3_4;

import textbook.fifth_edition.ch3_2.Node;

public class CircleList {
    protected Node cursor;
    protected int size;

    public CircleList() {
        cursor = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public Node getCursor() {
        return cursor;
    }

    /* Moves the cursor forward */
    public void advance() {
        cursor = cursor.getNext();
    }

    /** Adds a node after the cursor */
    public void add(Node node) {
        if(cursor == null) {    //List is empty,
            node.setNext(node); //下一個是自己
            cursor = node;
        }else {
            Node temp = cursor.getNext();
            cursor.setNext(node);
            node.setNext(temp);
        }
        size++;
    }

    /** Removes the node after the cursor */
    public Node remove() throws IllegalStateException{
        if(cursor == null) {
            throw new IllegalStateException("List is empty");
        }
        Node temp = cursor.getNext();
        if(temp == cursor) { //size = 1
            cursor = null;
        }else{
            cursor.setNext(temp.getNext());
            temp.setNext(null);
        }
        size--;
        return temp;
    }

    public String toString() {
        if(cursor == null) {
            return "[]";
        }

        String result = "[..." + cursor.getElement();
        Node oldNode = cursor;
        while(true) {
            advance();
            if(oldNode == cursor) break;
            result += ", " + cursor.getElement();
        }
        return result + "...]";

    }

    public static void main(String[] arg) {
        CircleList cList = new CircleList();
        for(int i = 0; i < 3; i++){
            cList.add(new Node(String.valueOf(i), null));
        }
        System.out.println(cList);
        cList.remove();
        System.out.println(cList);
        cList.remove();
        System.out.println(cList);
        cList.remove();
        System.out.println(cList);
        cList.remove();

    }
}
