package project.pj1;/* DListNode.java */

/**
 *  A DListNode is a node in a DList (doubly-linked list).
 */

public class DListNode {

  /**
   *  item references the item stored in the current node.
   *  prev references the previous node in the DList.
   *  next references the next node in the DList.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  public Object item;
  public DListNode prev;
  public DListNode next;

  /**
   *  DListNode() constructor.
   */
  DListNode() {
    item = null;
    prev = null;
    next = null;
  }

  DListNode(Object obj) {
    item = obj;
    prev = null;
    next = null;
  }

  public Object getItem() {
    return item;
  }

  public void setItem(Object item) {
    this.item = item;
  }

  public DListNode getPrev() {
    return prev;
  }

  public void setPrev(DListNode prev) {
    this.prev = prev;
  }

  public DListNode getNext() {
    return next;
  }

  public void setNext(DListNode next) {
    this.next = next;
  }
}
