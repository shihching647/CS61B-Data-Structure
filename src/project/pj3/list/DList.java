/* DList.java */

package project.pj3.list;

import java.util.Arrays;

/**
 *  A DList is a mutable doubly-linked list ADT.  Its implementation is
 *  circularly-linked and employs a sentinel (dummy) node at the head
 *  of the list.
 *
 *  DO NOT CHANGE ANY METHOD PROTOTYPES IN THIS FILE.
 */

public class DList {

  /**
   *  head references the sentinel node.
   *  size is the number of items in the list.  (The sentinel node does not
   *       store an item.)
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  protected DListNode head;
  protected int size;

  /* DList invariants:
   *  1)  head != null.
   *  2)  For any DListNode x in a DList, x.next != null.
   *  3)  For any DListNode x in a DList, x.prev != null.
   *  4)  For any DListNode x in a DList, if x.next == y, then y.prev == x.
   *  5)  For any DListNode x in a DList, if x.prev == y, then y.next == x.
   *  6)  size is the number of DListNodes, NOT COUNTING the sentinel,
   *      that can be accessed from the sentinel (head) by a sequence of
   *      "next" references.
   */

  /**
   *  newNode() calls the DListNode constructor.  Use this class to allocate
   *  new DListNodes rather than calling the DListNode constructor directly.
   *  That way, only this method needs to be overridden if a subclass of DList
   *  wants to use a different kind of node.
   *  @param item the item to store in the node.
   *  @param prev the node previous to this node.
   *  @param next the node following this node.
   */
  protected DListNode newNode(Object item, DListNode prev, DListNode next) {
    return new DListNode(item, prev, next);
  }

  /**
   *  DList() constructor for an empty DList.
   */
  public DList() {
    //  Your solution here.
      DListNode temp = newNode(null, null, null);
      temp.prev = temp;
      temp.next = temp;
      head = temp;
      size = 0;
  }

  /**
   *  isEmpty() returns true if this DList is empty, false otherwise.
   *  @return true if this DList is empty, false otherwise.
   *  Performance:  runs in O(1) time.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   *  length() returns the length of this DList.
   *  @return the length of this DList.
   *  Performance:  runs in O(1) time.
   */
  public int length() {
    return size;
  }

  /**
   *  insertFront() inserts an item at the front of this DList.
   *  @param item is the item to be inserted.
   *  Performance:  runs in O(1) time.
   */
  public void insertFront(Object item) {
    // Your solution here.
      DListNode temp = head.next;
      DListNode newNode = newNode(item, head, temp);
      head.next = newNode;
      temp.prev = newNode;
      size++;
  }

  /**
   *  insertBack() inserts an item at the back of this DList.
   *  @param item is the item to be inserted.
   *  Performance:  runs in O(1) time.
   */
  public void insertBack(Object item) {
    // Your solution here.
      DListNode temp = head.prev;
      DListNode newNode = newNode(item, temp, head);
      head.prev = newNode;
      temp.next = newNode;
      size++;
  }

  /**
   *  front() returns the node at the front of this DList.  If the DList is
   *  empty, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @return the node at the front of this DList.
   *  Performance:  runs in O(1) time.
   */
  public DListNode front() {
    // Your solution here.
      if(size == 0) {
          return null;
      } else {
          return head.next;
      }
  }

  /**
   *  back() returns the node at the back of this DList.  If the DList is
   *  empty, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @return the node at the back of this DList.
   *  Performance:  runs in O(1) time.
   */
  public DListNode back() {
    // Your solution here.
      if(size == 0) {
          return null;
      } else {
          return head.prev;
      }
  }

  /**
   *  next() returns the node following "node" in this DList.  If "node" is
   *  null, or "node" is the last node in this DList, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @param node the node whose successor is sought.
   *  @return the node following "node".
   *  Performance:  runs in O(1) time.
   */
  public DListNode next(DListNode node) {
    // Your solution here.
      if(node == null) {
          return null;
      } else if(node == head) {
          return null;
      } else if(node == head.prev) {
          return null;
      } else {
          return node.next;
      }
  }

  /**
   *  prev() returns the node prior to "node" in this DList.  If "node" is
   *  null, or "node" is the first node in this DList, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @param node the node whose predecessor is sought.
   *  @return the node prior to "node".
   *  Performance:  runs in O(1) time.
   */
  public DListNode prev(DListNode node) {
    // Your solution here.
      if(node == null) {
          return null;
      } else if (node == head) {
          return null;
      } else if (node == head.next){
          return null;
      } else {
          return node.prev;
      }
  }

  /**
   *  insertAfter() inserts an item in this DList immediately following "node".
   *  If "node" is null, do nothing.
   *  @param item the item to be inserted.
   *  @param node the node to insert the item after.
   *  Performance:  runs in O(1) time.
   */
  public void insertAfter(Object item, DListNode node) {
    // Your solution here.
      if(node == null) {
          return;
      } else {
          DListNode temp = node.next;
          DListNode newNode = newNode(item, node, temp);
          node.next = newNode;
          temp.prev = newNode;
          size++;
      }
  }

  /**
   *  insertBefore() inserts an item in this DList immediately before "node".
   *  If "node" is null, do nothing.
   *  @param item the item to be inserted.
   *  @param node the node to insert the item before.
   *  Performance:  runs in O(1) time.
   */
  public void insertBefore(Object item, DListNode node) {
    // Your solution here.
      if(node == null) {
          return;
      } else {
          DListNode temp = node.prev;
          DListNode newNode = newNode(item, temp, node);
          temp.next = newNode;
          node.prev = newNode;
          size++;
      }
  }

  /**
   *  remove() removes "node" from this DList.  If "node" is null, do nothing.
   *  Performance:  runs in O(1) time.
   */
  public void remove(DListNode node) {
    // Your solution here.
      if(node == null) {
          return;
      } else if(node == head) {
          return;
      } else if(size == 0) {
          return;
      } else {
          DListNode prevNode = node.prev;
          DListNode nextNode = node.next;
          prevNode.next = nextNode;
          node.next = null;
          node.prev = null;
          nextNode.prev = prevNode;
          size--;
      }
  }

  /**
   *  toString() returns a String representation of this DList.
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of this DList.
   *  Performance:  runs in O(n) time, where n is the length of the list.
   */
  public String toString() {
    String result = "[  ";
    DListNode current = head.next;
    while (current != head) {
      result = result + current.item + "  ";
      current = current.next;
    }
    return result + "]";
  }

    /**
     *  getAllElements() returns all element in the list, if size = 0, return an array with length = 0
     *
     * @return a array of all element in the list
     * Performance:  runs in O(n) time, where n is the length of the list.
     */
  public Object[] getAllElements() {
      Object[] result = new Object[size];
      DListNode node = front();
      if (node != null) {
          for (int i = 0; i < result.length; i++) {
              result[i] = node.item;
              node = node.next;
          }
      }
      return result;
  }

    /**
     *  getAllNodes() returns all nodes in the list, if size = 0, return an array with length = 0
     *
     * @return a array of all nodes in the list
     * Performance:  runs in O(n) time, where n is the length of the list.
     */
    public DListNode[] getAllNodes() {
        DListNode[] result = new DListNode[size];
        DListNode node = front();
        if (node != null) {
            for (int i = 0; i < result.length; i++) {
                result[i] = node;
                node = node.next;
            }
        }
        return result;
    }

  public static void main(String[] args) {
      DList list = new DList();
      System.out.println(list + "size = " + list.size);
      System.out.println(list.front());
      System.out.println(list.back());
      list.insertFront(2);
      System.out.println(list + "size = " + list.size);
      list.insertFront(1);
      System.out.println(list + "size = " + list.size);
      list.insertBack(3);
      System.out.println(list + "size = " + list.size);
      System.out.println((int)list.front().item);
      System.out.println((int)list.back().item);
      System.out.println((int)list.prev(list.back()).item);
      System.out.println((int)list.next(list.front()).item);
      list.insertBefore(5, list.front());
      list.insertAfter(4, list.front());
      list.insertAfter(5, list.back());
      list.insertBefore(4, list.back());
      System.out.println(list + "size = " + list.size);
      System.out.println(Arrays.toString(list.getAllElements()));
      list.remove(list.front());
      list.remove(list.back());
      System.out.println(list + "size = " + list.size);
      list.remove(list.front());
      list.remove(list.front());
      list.remove(list.front());
      list.remove(list.front());
      list.remove(list.front());
      System.out.println(list + "size = " + list.size);
      list.remove(list.front());
      System.out.println(list + "size = " + list.size);

      DList list2 = new DList();
      list2.insertBefore(1, list.front());
      System.out.println(list2);
      System.out.println(Arrays.toString(list.getAllElements()));
  }

}
