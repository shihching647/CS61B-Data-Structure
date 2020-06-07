package project.pj1;/* DList.java */


/**
 *  A DList is a mutable doubly-linked list.  (No sentinel, not
 *  circularly linked.)
 */

public class DList {

  /**
   *  head references the first node.
   *  tail references the last node.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  protected DListNode head;
  protected DListNode tail;
  protected long size;

  /* DList invariants:
   *  1)  head.prev == null.
   *  2)  tail.next == null.
   *  3)  For any DListNode x in a DList, if x.next == y and x.next != null,
   *      then y.prev == x.
   *  4)  For any DListNode x in a DList, if x.prev == y and x.prev != null,
   *      then y.next == x.
   *  5)  The tail can be accessed from the head by a sequence of "next"
   *      references.
   *  6)  size is the number of DListNode1s that can be accessed from the
   *      head by a sequence of "next" references.
   */

  /**
   *  DList() constructor for an empty DList.
   */
  public DList() {
    head = null;
    tail = null;
    size = 0;
  }

  /**
   *  DList() constructor for a one-node DList.
   */
  public DList(Object obj) {
    head = new DListNode();
    tail = head;
    head.item = obj;
    size = 1;
  }

  /**
   *  DList() constructor for a two-node DList.
   */
  public DList(Object obj1, Object obj2) {
    head = new DListNode();
    head.item = obj1;
    tail = new DListNode();
    tail.item = obj2;
    head.next = tail;
    tail.prev = head;
    size = 2;
  }

    public DListNode getHead() {
        return head;
    }

    public void setHead(DListNode head) {
        this.head = head;
    }

    public DListNode getTail() {
        return tail;
    }

    public void setTail(DListNode tail) {
        this.tail = tail;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    /**
   *  insertFront() inserts an item at the front of a DList.
   */
  public void insertFront(Object obj) {
      if(size == 0) {
          head = new DListNode(obj);
          tail = head;
      } else if(size == 1) {
          head = new DListNode(obj);
          head.next = tail;
          tail.prev = head;
      } else {
          DListNode oldHead = head;
          DListNode newHead = new DListNode(obj);
          newHead.next = oldHead;
          oldHead.prev = newHead;
          head = newHead;
      }
      size++;
  }

  /**
   *  removeFront() removes the first item (and node) from a DList.  If the
   *  list is empty, do nothing.
   */
  public void removeFront() {
      if(size == 0) {
          head = tail = null;
          return;
      } else if(size <= 1){
          head = tail = null;
      } else if(size == 2){
          DListNode oldHead = head;
          oldHead.next = null;
          head = tail;
          head.prev = null;
      } else {
          DListNode oldHead = head;
          DListNode newHead = head.next;
          oldHead.next = null;
          newHead.prev = null;
          head = newHead;
      }
      size--;
  }
    /**
     *  insertEnd() inserts an item at the end of a DList.
     */
  public void insertEnd(Object obj) {
      if(size == 0) {
          tail = new DListNode(obj);
          head = tail;
      } else if(size == 1){
          tail = new DListNode(obj);
          head.next = tail;
          tail.prev = head;
      } else {
          DListNode temp = tail;
          tail = new DListNode(obj);
          temp.next = tail;
          tail.prev = temp;
      }
      size++;
  }

  /**
   *  toString() returns a String representation of this DList.
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of this DList.
   */
  public String toString() {
    String result = "[  ";
    DListNode current = head;
    while (current != null) {
      result = result + current.item + "  ";
      current = current.next;
    }
    return result + "]";
  }

  public static void main(String[] args) {
    // DO NOT CHANGE THE FOLLOWING CODE.

    DList l = new DList();
      System.out.println("### TESTING insertEnd ###\nEmpty list is " + l);
      l.insertEnd(9);
      System.out.println("\nInserting 9 at end.\nList with 9 is " + l);
      if (l.head == null) {
          System.out.println("head is null.");
      } else {
          if (!l.head.item.equals(9)) {
              System.out.println("head.item is wrong.");
          }
          if (l.head.prev != null) {
              System.out.println("head.prev is wrong.");
          }
      }
      if (l.tail == null) {
          System.out.println("tail is null.");
      } else {
          if (!l.tail.item.equals(9)) {
              System.out.println("tail.item is wrong.");
          }
          if (l.tail.next != null) {
              System.out.println("tail.next is wrong.");
          }
      }
      if (l.size != 1) {
          System.out.println("size is wrong.");
      }

      l.insertEnd(8);
      System.out.println("\nInserting 8 at end.\nList with 9 and 8 is " + l);
      if (l.head == null) {
          System.out.println("head is null.");
      } else {
          if (!l.head.item.equals(9)) {
              System.out.println("head.item is wrong.");
          }
          if (l.head.prev != null) {
              System.out.println("head.prev is wrong.");
          }
          if (l.head.next != l.tail) {
              System.out.println("head.next is wrong.");
          }
      }
      if (l.tail == null) {
          System.out.println("tail is null.");
      } else {
          if (!l.tail.item.equals(8)) {
              System.out.println("tail.item is wrong.");
          }
          if (l.tail.next != null) {
              System.out.println("tail.next is wrong.");
          }
          if (l.tail.prev != l.head) {
              System.out.println("tail.prev is wrong.");
          }
      }
      if (l.size != 2) {
          System.out.println("size is wrong.");
      }

      l.insertEnd(7);
      System.out.println("\nInserting 7 at end.\nList with9, 8 and 7 is " + l);
      if (l.head == null) {
          System.out.println("head is null.");
      } else {
          if (!l.head.item.equals(9)) {
              System.out.println("head.item is wrong.");
          }
          if (l.head.prev != null) {
              System.out.println("head.prev is wrong.");
          }
          if (!l.head.next.item.equals(8)) {
              System.out.println("head.next is wrong.");
          }
      }
      if (l.tail == null) {
          System.out.println("tail is null.");
      } else {
          if (!l.tail.item.equals(7)) {
              System.out.println("tail.item is wrong.");
          }
          if (l.tail.next != null) {
              System.out.println("tail.next is wrong.");
          }
          if (!l.tail.prev.item.equals(8)) {
              System.out.println("tail.prev is wrong.");
          }
      }
      if (l.size != 3) {
          System.out.println("size is wrong.");
      }

    l = new DList();
    System.out.println("\n\n### TESTING insertFront ###\nEmpty list is " + l);
    l.insertFront(9);
    System.out.println("\nInserting 9 at front.\nList with 9 is " + l);
    if (l.head == null) {
      System.out.println("head is null.");
    } else {
      if (!l.head.item.equals(9)) {
        System.out.println("head.item is wrong.");
      }
      if (l.head.prev != null) {
        System.out.println("head.prev is wrong.");
      }
    }
    if (l.tail == null) {
      System.out.println("tail is null.");
    } else {
      if (!l.tail.item.equals(9)) {
        System.out.println("tail.item is wrong.");
      }
      if (l.tail.next != null) {
        System.out.println("tail.next is wrong.");
      }
    }
    if (l.size != 1) {
      System.out.println("size is wrong.");
    }

    l.insertFront(8);
    System.out.println("\nInserting 8 at front.\nList with 8 and 9 is " + l);
    if (l.head == null) {
      System.out.println("head is null.");
    } else {
      if (!l.head.item.equals(8)) {
        System.out.println("head.item is wrong.");
      }
      if (l.head.prev != null) {
        System.out.println("head.prev is wrong.");
      }
      if (l.head.next != l.tail) {
        System.out.println("head.next is wrong.");
      }
    }
    if (l.tail == null) {
      System.out.println("tail is null.");
    } else {
      if (!l.tail.item.equals(9)) {
        System.out.println("tail.item is wrong.");
      }
      if (l.tail.next != null) {
        System.out.println("tail.next is wrong.");
      }
      if (l.tail.prev != l.head) {
        System.out.println("tail.prev is wrong.");
      }
    }
    if (l.size != 2) {
      System.out.println("size is wrong.");
    }

      l.insertFront(7);
      System.out.println("\nInserting 7 at front.\nList with7, 8 and 9 is " + l);
      if (l.head == null) {
          System.out.println("head is null.");
      } else {
          if (!l.head.item.equals(7)) {
              System.out.println("head.item is wrong.");
          }
          if (l.head.prev != null) {
              System.out.println("head.prev is wrong.");
          }
          if (!l.head.next.item.equals(8)) {
              System.out.println("head.next is wrong.");
          }
      }
      if (l.tail == null) {
          System.out.println("tail is null.");
      } else {
          if (!l.tail.item.equals(9)) {
              System.out.println("tail.item is wrong.");
          }
          if (l.tail.next != null) {
              System.out.println("tail.next is wrong.");
          }
          if (!l.tail.prev.item.equals(8)) {
              System.out.println("tail.prev is wrong.");
          }
      }
      if (l.size != 3) {
          System.out.println("size is wrong.");
      }



    l = new DList(1, 2);
      l.insertFront(0);

      System.out.println("\n\n### TESTING removeFront ###\nList with 0, 1 and 2 is "
              + l);
      l.removeFront();
      System.out.println("\nRemoving front node.\nList with 1 2 is " + l);
      if (!l.head.item.equals(1)) {
          System.out.println("head.item is wrong.");
      }
      if (l.head.prev != null) {
          System.out.println("head.prev is wrong.");
      }
      if (!l.tail.item.equals(2)) {
          System.out.println("tail.item is wrong.");
      }
      if (l.tail.next != null) {
          System.out.println("tail.next is wrong.");
      }
      if (l.size != 2) {
          System.out.println("size is wrong.");
      }

    l.removeFront();
    System.out.println("\nRemoving front node.\nList with 2 is " + l);
    if (!l.head.item.equals(2)) {
      System.out.println("head.item is wrong.");
    }
    if (l.head.prev != null) {
      System.out.println("head.prev is wrong.");
    }
    if (!l.tail.item.equals(2)) {
      System.out.println("tail.item is wrong.");
    }
    if (l.tail.next != null) {
      System.out.println("tail.next is wrong.");
    }
    if (l.size != 1) {
      System.out.println("size is wrong.");
    }

    l.removeFront();
    System.out.println("\nRemoving front node.\nEmpty list is " + l);
    if (l.head != null) {
      System.out.println("head is wrong.");
    }
    if (l.tail != null) {
      System.out.println("tail is wrong.");
    }
    if (l.size != 0) {
      System.out.println("size is wrong.");
    }

    l.removeFront();
    System.out.println("\nRemoving front node.\nEmpty list is " + l);
    if (l.head != null) {
      System.out.println("head is wrong.");
    }
    if (l.tail != null) {
      System.out.println("tail is wrong.");
    }
    if (l.size != 0) {
      System.out.println("size is wrong.");
    }
  }

}
