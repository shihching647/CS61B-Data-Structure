/* SList.java */

/**
 * 本檔案為覆寫equals方法
 **/

package handout.lecture11;

import laboratory.lab3.TestHelper;

public class SList {

    private SListNode head, tail;
    private int size;

    /**
     * SList() constructs an empty list.
     **/

    public SList() {
        size = 0;
        head = null;
        tail = null;
    }

    /**
     * isEmpty() indicates whether the list is empty.
     *
     * @return true if the list is empty, false otherwise.
     **/

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * length() returns the length of this list.
     *
     * @return the length of this list.
     **/

    public int length() {
        return size;
    }

    /**
     * insertFront() inserts item "obj" at the beginning of this list.
     *
     * @param obj the item to be inserted.
     **/

    public void insertFront(Object obj) {
//	    head = new SListNode(obj, head);
//	    size++;
        //當為空list時, 要把head跟tail指到SListNode上
        if (head == null) {
            head = new SListNode(obj);
            tail = head;
        } else {
            head = new SListNode(obj, head);
        }
        size++;
    }

    /**
     * insertEnd() inserts item "obj" at the end of this list.
     *
     * @param obj the item to be inserted.
     **/

    public void insertEnd(Object obj) {
        //當為空list時, 要把head跟tail指到SListNode上
        if (head == null) {
            tail = new SListNode(obj);
            head = tail;
        } else {
            tail.next = new SListNode(obj);
            tail = tail.next;
        }
        size++;
    }

    /**
     * toString() converts the list to a String.
     *
     * @return a String representation of the list.
     **/

    public String toString() {
        int i;
        Object obj;
        String result = "[  ";

        SListNode cur = head;

        while (cur != null) {
            obj = cur.item;
            result = result + obj.toString() + "  ";
            cur = cur.next;
        }
        result = result + "]";
        return result;
    }

    public boolean equals(Object obj) {
        if(!(obj instanceof SList)){
            return false;
        }
        SList other = (SList) obj;
        //比較size
        if(this.size != other.size) {
            return false;
        }
        //比較每個元素是否相等
        SListNode n1 = this.head;
        SListNode n2 = other.head;
        while(n1 != null) {
            if(!n1.item.equals(n2.item)) {
                return false;
            }
            n1 = n1.next;
            n2 = n2.next;
        }
        return true;
    }


    /**
     * main() runs test cases on the SList class.  Prints summary
     * information on basic operations and halts with an error (and a stack
     * trace) if any of the tests fail.
     **/

    public static void main(String[] args) {
        // Test equals
        SList list1 = new SList();
        SList list2 = new SList();
        System.out.println(list1 + " = " + list2 + " is " + list1.equals(list2));

        SList list3 = new SList();
        list3.insertEnd(0);
        SList list4 = new SList();
        list4.insertFront(0);
        System.out.println(list3 + " = " + list4 + " is " + list3.equals(list4));
        System.out.println(list1 + " = " + list3 + " is " + list1.equals(list3));

    }
}

