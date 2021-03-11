/* Tree234Node.java */

package homework.hw7.dict;

/**
 *  A Tree234Node is a node in a 2-3-4 tree (Tree234 class).
 *
 *  DO NOT CHANGE ANYTHING IN THIS FILE.
 *  You may add helper methods and additional constructors, though.
 **/
class Tree234Node {

  /**
   *  keys is the number of keys in this node.  Always 1, 2, or 3.
   *  key1 through key3 are the keys of this node.  If keys == 1, the value
   *    of key2 doesn't matter.  If keys < 3, the value of key3 doesn't matter.
   *  parent is this node's parent; null if this is the root.
   *  child1 through child4 are the children of this node.  If this is a leaf
   *    node, they must all be set to null.  If this node has no third and/or
   *    fourth child, child3 and/or child4 must be set to null.
   **/
  int keys;
  int key1;
  int key2;
  int key3;
  Tree234Node parent;
  Tree234Node child1;
  Tree234Node child2;
  Tree234Node child3;
  Tree234Node child4;

  Tree234Node(Tree234Node p, int key) {
    keys = 1;
    key1 = key;
    parent = p;
    child1 = null;
    child2 = null;
    child3 = null;
    child4 = null;
  }

  Tree234Node(Tree234Node p, int key, Tree234Node child1, Tree234Node child2) {
    keys = 1;
    key1 = key;
    parent = p;
    this.child1 = child1;
    this.child2 = child2;
    this.child3 = null;
    this.child4 = null;
  }

  boolean isFull() {
    return (child4 != null || keys == 3);
  }

  boolean isLeaftNode() {
    return child1 == null;
  }

  /**
   * this node已包含有三個key, 把key2擺到parent裡, 此node分成leafNode(包含key1),rightNode(包含key3), 並把彼此間的關係建立好
   * @param key 欲插入234Tree的key
   * @return if (key < key2) return leaf node else return right node
   */
  Tree234Node split(int key) {

    int insertKey = this.key2;
    Tree234Node leftNode = new Tree234Node(null, this.key1, child1, child2);
    Tree234Node rightNode = new Tree234Node(null, this.key3, child3, child4);

    if (parent == null) {
      //處理key
      parent = new Tree234Node(null, insertKey);
      //處理child
      parent.child1 = leftNode;
      parent.child2 = rightNode;

    } else if (parent.keys == 1){
      if (parent.child1 == this) { //insertKey < parent.key1
        //處理key
        parent.key2 = parent.key1;
        parent.key1 = insertKey;
        //處理child
        parent.child3 = parent.child2;
        parent.child1 = leftNode;
        parent.child2 = rightNode;
      } else if (parent.child2 == this){
        //處理key
        parent.key2 = insertKey;
        //處理child
        parent.child2 = leftNode;
        parent.child3 = rightNode;
      } else {
        throw new RuntimeException("parent.keys與children數不符合");
      }
      parent.keys++;
    } else if (parent.keys == 2) {
      if (parent.child1 == this) {
        //處理key
        parent.key3 = parent.key2;
        parent.key2 = parent.key1;
        parent.key1 = insertKey;
        //處理child
        parent.child4 = parent.child3;
        parent.child3 = parent.child2;
        parent.child2 = rightNode;
        parent.child1 = leftNode;
      } else if (parent.child2 == this) {
        //處理key
        parent.key3 = parent.key2;
        parent.key2 = insertKey;
        //處理child
        parent.child4 = parent.child3;
        parent.child3 = rightNode;
        parent.child2 = leftNode;
      } else if (parent.child3 == this) {
        //處理key
        parent.key3 = insertKey;
        //處理child
        parent.child4 = rightNode;
        parent.child3 = leftNode;
      } else {
        throw new RuntimeException("parent.keys與children數不符合");
      }
      parent.keys++;
    } else {
      throw new RuntimeException("parent沒有空間給新的key插入");
    }

    //left,right node的parent要指回parent
    leftNode.parent = parent;
    rightNode.parent = parent;
    if (!leftNode.isLeaftNode()) {
      leftNode.child1.parent = leftNode;
      leftNode.child2.parent = leftNode;
    }
    if (!rightNode.isLeaftNode()) {
      rightNode.child1.parent = rightNode;
      rightNode.child2.parent = rightNode;
    }

    //回傳
    if (key < insertKey) {
      return leftNode;
    } else {
      return rightNode;
    }
  }


  /**
   *  toString() recursively prints this Tree234Node and its descendants as
   *  a String.  Each node is printed in the form such as (for a 3-key node)
   *
   *      (child1)key1(child2)key2(child3)key3(child4)
   *
   *  where each child is a recursive call to toString, and null children
   *  are printed as a space with no parentheses.  Here's an example.
   *      ((1)7(11 16)22(23)28(37 49))50((60)84(86 95 100))
   *
   *  DO NOT CHANGE THIS METHOD.
   **/
  public String toString() {
    String s = "";

    if (child1 != null) {
      s = "(" + child1.toString() + ")";
    }
    s = s + key1;
    if (child2 != null) {
      s = s + "(" + child2.toString() + ")";
    } else if (keys > 1) {
      s = s + " ";
    }
    if (keys > 1) {
      s = s + key2;
      if (child3 != null) {
        s = s + "(" + child3.toString() + ")";
      } else if (keys > 2) {
        s = s + " ";
      }
    }
    if (keys > 2) {
      s = s + key3;
      if (child4 != null) {
        s = s + "(" + child4.toString() + ")";
      }
    }
    return s;
  }

  /**
   *  printSubtree() recursively prints this Tree234Node and its descendants as
   *  a tree (albeit sideways).
   *
   *  You're welcome to change this method if you like.  It won't be tested.
   **/
  public void printSubtree(int spaces) {
    if (child4 != null) {
      child4.printSubtree(spaces + 5);
    }
    if (keys == 3) {
      for (int i = 0; i < spaces; i++) {
        System.out.print(" ");
      }
      System.out.println(key3);
    }

    if (child3 != null) {
      child3.printSubtree(spaces + 5);
    }
    if (keys > 1) {
      for (int i = 0; i < spaces; i++) {
        System.out.print(" ");
      }
      System.out.println(key2);
    }

    if (child2 != null) {
      child2.printSubtree(spaces + 5);
    }
    for (int i = 0; i < spaces; i++) {
      System.out.print(" ");
    }
    System.out.println(key1);

    if (child1 != null) {
      child1.printSubtree(spaces + 5);
    }
  }
}
