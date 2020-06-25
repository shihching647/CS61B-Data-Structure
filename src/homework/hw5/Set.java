package homework.hw5;/* Set.java */

import homework.hw5.list.*;

import java.util.Random;

/**
 *  A Set is a collection of Comparable elements stored in sorted order.
 *  Duplicate elements are not permitted in a Set.
 **/
public class Set {
  /* Fill in the data fields here. */
    List list;
    ListNode cur; //當使用contains()方法時, 用來記錄目前比到那個位置
    int count; //只是用來紀錄被呼叫幾次

  /**
   * Set ADT invariants:
   *  1)  The Set's elements must be precisely the elements of the List.
   *  2)  The List must always contain Comparable elements, and those elements 
   *      must always be sorted in ascending order.
   *  3)  No two elements in the List may be equal according to compareTo().
   **/

  /**
   *  Constructs an empty Set. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public Set() { 
    // Your solution here.
      list = new SList();
  }

  /**
   *  cardinality() returns the number of elements in this Set.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int cardinality() {
    // Replace the following line with your solution.
    return list.length();
  }

  /**
   *  insert() inserts a Comparable element into this Set.
   *
   *  Sets are maintained in sorted order.  The ordering is specified by the
   *  compareTo() method of the java.lang.Comparable interface.
   *
   *  Performance:  runs in O(this.cardinality()) time.
   **/
  public void insert(Comparable c) {
    // Your solution here.
      try {
          ListNode node = list.front();
          while (node.isValidNode()) {
              if (c.compareTo(node.item()) == 0) {
                  return;
              } else if (c.compareTo(node.item()) < 0) {
                  node.insertBefore(c);
                  return;
              }
              node = node.next();
          }
          list.insertBack(c);
      } catch (InvalidNodeException e) {
          e.printStackTrace();
      }
  }

  /**
   *  union() modifies this Set so that it contains all the elements it
   *  started with, plus all the elements of s.  The Set s is NOT modified.
   *  Make sure that duplicate elements are not created.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Your implementation should NOT copy elements of s or "this", though it
   *  will copy _references_ to the elements of s.  Your implementation will
   *  create new _nodes_ for the elements of s that are added to "this", but
   *  you should reuse the nodes that are already part of "this".
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT ATTEMPT TO COPY ELEMENTS; just copy _references_ to them.
   **/
  public void union(Set s) {
    // Your solution here.

      //special case
      if(list.length() == 0) {
          list = s.list;
          return;
      }
      if(s.list.length() == 0) {
          return;
      }

      //normal case
      cur = list.front();
      count = 0;
      try{
          ListNode node = s.list.front();
          for(int i = 0; i < s.list.length(); i++) {
              count++;
              if(!contains(node)) {
                  //比list的最後一個大才要插到後面
                  if(cur == list.back() && ((Comparable)node.item()).compareTo(cur.item()) > 0) {
                      list.insertBack(node.item());
                  } else {
                      cur.insertBefore(node.item());
                  }
              }
              node = node.next();
          }
      } catch(InvalidNodeException e) {
          e.printStackTrace();
      }

  }

  //回傳node是否存在於此Set中(此方法需要node依小到大傳入)
  private boolean contains(ListNode node) throws InvalidNodeException{
      Comparable nodeValue = (Comparable) node.item();
      while(true) {
          count++;
          Comparable curValue = (Comparable) cur.item();
          if (curValue.compareTo(nodeValue) == 0) {
              return true;
          } else if (curValue.compareTo(nodeValue) < 0) {
              if(!cur.next().isValidNode()){
                  return false;
              }
          } else if (curValue.compareTo(nodeValue) > 0){
              return false;
          }
          cur = cur.next();
      }
  }

  /**
   *  intersect() modifies this Set so that it contains the intersection of
   *  its own elements and the elements of s.  The Set s is NOT modified.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Do not construct any new ListNodes during the execution of intersect.
   *  Reuse the nodes of "this" that will be in the intersection.
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT CONSTRUCT ANY NEW NODES.
   *  DO NOT ATTEMPT TO COPY ELEMENTS.
   **/
  public void intersect(Set s) {
    // Your solution here.

      //special case
      if(list.length() == 0) {
          return;
      }
      if(s.list.length() == 0) {
          list = s.list;
          return;
      }

      //normal case
      cur = list.front();
      count = 0;
      try{
          ListNode node = s.list.front();
          int times = s.list.length(); //因為s裡面的元素會被移除, 所以長度可能會變, 故需要一開始先把長度存起來!
          for(int i = 0; i < times; i++) {
              count++;
              ListNode next = node.next();
              if(!contains(node)){
                  node.remove();
              }
              node = next;
          }
          list = s.list; //把s.list指定給list
      } catch(InvalidNodeException e) {
          e.printStackTrace();
      }
  }

  /**
   *  toString() returns a String representation of this Set.  The String must
   *  have the following format:
   *    {  } for an empty Set.  No spaces before "{" or after "}"; two spaces
   *            between them.
   *    {  1  2  3  } for a Set of three Integer elements.  No spaces before
   *            "{" or after "}"; two spaces before and after each element.
   *            Elements are printed with their own toString method, whatever
   *            that may be.  The elements must appear in sorted order, from
   *            lowest to highest according to the compareTo() method.
   *
   *  WARNING:  THE AUTOGRADER EXPECTS YOU TO PRINT SETS IN _EXACTLY_ THIS
   *            FORMAT, RIGHT UP TO THE TWO SPACES BETWEEN ELEMENTS.  ANY
   *            DEVIATIONS WILL LOSE POINTS.
   **/
  public String toString() {
    // Replace the following line with your solution.
      StringBuilder sb = new StringBuilder("{  ");
      try{
          ListNode node = list.front();
          while(node.isValidNode()) {
              sb.append(node.item() + "  ");
              node = node.next();
          }
          sb.append("}");
      } catch(InvalidNodeException e) {
          e.printStackTrace();
      }
      return sb.toString();
  }

  public static void main(String[] argv) {
      Set s = new Set();
      s.insert(new Integer(3));
      s.insert(new Integer(4));
      s.insert(new Integer(3));
      System.out.println("Set s = " + s);

      Set s2 = new Set();
      s2.insert(new Integer(4));
      s2.insert(new Integer(5));
      s2.insert(new Integer(5));
      System.out.println("Set s2 = " + s2);

      Set s3 = new Set();
      s3.insert(new Integer(5));
      s3.insert(new Integer(3));
      s3.insert(new Integer(8));
      System.out.println("Set s3 = " + s3);

      s.union(s2);
      System.out.println("After s.union(s2), s = " + s);

      s.intersect(s3);
      System.out.println("After s.intersect(s3), s = " + s);

      System.out.println("s.cardinality() = " + s.cardinality());
    // You may want to add more (ungraded) test code here.
      System.out.println("\n-------------MyTest------------------\n");

      //測試specail case
      Set s4 = new Set();
      s4.insert(new Integer(0));
      s4.insert(new Integer(1));
      s4.insert(new Integer(2));

      Set s5 = new Set();
      s5.insert(new Integer(0));
      s5.insert(new Integer(2));
      s5.insert(new Integer(1));

      Set s6 = new Set();

      s4.union(s6);
      System.out.println(s4 + "size = " + s4.cardinality());
      System.out.println(s4.count);
      s6.union(s5);
      System.out.println(s6 + "size = " + s6.cardinality());
      System.out.println(s6.count);

      s4.intersect(s6);
      System.out.println(s4 + "size = " + s4.cardinality());
      System.out.println(s4.count);
      s6.intersect(s5);
      System.out.println(s6 + "size = " + s6.cardinality());
      System.out.println(s6.count);

      //亂數測試
      Random rand = new Random();
      rand.setSeed(System.currentTimeMillis());

      Set set7 = new Set();
      for(int i = 0; i <= 10; i++) {
          set7.insert(new Integer(rand.nextInt(20)));
      }
      Set set8 = new Set();
      for(int i = 0; i <= 10; i++) {
          set8.insert(new Integer(rand.nextInt(20)));
      }
      System.out.println("set7 = " + set7);
      System.out.println("set8 = " + set8);
      set7.intersect(set8);
      System.out.println(set7.count);
      System.out.println("After set7.intersect(set8) -> set7 = " + set7);

      Set set9 = new Set();
      for(int i = 0; i <= 10; i++) {
          set9.insert(new Integer(rand.nextInt(20)));
      }
      Set set10 = new Set();
      for(int i = 0; i <= 10; i++) {
          set10.insert(new Integer(rand.nextInt(20)));
      }
      System.out.println("set9 = " + set9);
      System.out.println("set10 = " + set10);
      set9.union(set10);
      System.out.println(set9.count);
      System.out.println("After set9.union(set10) -> set9 = " + set9);
  }
}
