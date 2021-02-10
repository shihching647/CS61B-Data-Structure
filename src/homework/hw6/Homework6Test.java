package homework.hw6;/* Homework6Test.java */

import homework.hw6.dict.*;

/**
 *  Initializes a hash table, then stocks it with random SimpleBoards.
 **/

public class Homework6Test {

  /**
   *  Generates a random 8 x 8 SimpleBoard.
   **/

  private static SimpleBoard randomBoard() {
    SimpleBoard board = new SimpleBoard();
    for (int y = 0; y < 8; y++) {
      for (int x = 0; x < 8; x++) {
	double fval = Math.random() * 12;
	int value = (int) fval;
	board.setElementAt(x, y, value);
      }
    }
    return board;
  }

  /**
   *  Empties the given table, then inserts "numBoards" boards into the table.
   *  @param table is the hash table to be initialized.
   *  @param numBoards is the number of random boards to place in the table.
   **/

  public static void initTable(HashTableChained table, int numBoards) {
    table.makeEmpty();
    for (int i = 0; i < numBoards; i++) {
      table.insert(randomBoard(), new Integer(i));
    }
  }

  /**
   *  Creates a hash table and stores a certain number of SimpleBoards in it.
   *  The number is 100 by default, but you can specify any number at the
   *  command line.  For example:
   *
   *    java Homework6Test 12000
   **/

  public static void main(String[] args) {
    int numBoards;

    if (args.length == 0) {
      numBoards = 100;
    } else {
      numBoards = Integer.parseInt(args[0]);
    }
    HashTableChained table = new HashTableChained(numBoards);
    initTable(table, numBoards);

    // To test your hash function, add a method to your HashTableChained class
    // that counts the number of collisions--or better yet, also prints
    // a histograph of the number of entries in each bucket.  Call this method
    // from here.

    /**
     * the expected number of collisions is
     *   n - N + N (1 - 1/N)^n.
     * For example, if you have N = 200 buckets and n = 100
     * keys, expect about 21.15 collisions
     */
    table.showBucket();

    //Test basic function, insert(), remove(), find()
    HashTableChained map = new HashTableChained(1);
    System.out.println("-----------------TEST insert()-----------------");
    System.out.println(map);
    map.insert(1, "oneXXX");
    System.out.println(map);
    map.insert(1, "one");
    System.out.println(map);
    map.insert(1, "oneYYY");
    System.out.println(map);
    map.insert(2, "two");
    System.out.println(map);
    map.insert(3, "three");
    System.out.println(map);
    map.insert(4, "four");
    System.out.println(map);

    System.out.println("\n-------------------TEST find()----------------------");
    System.out.println(map.find(1).value());
    System.out.println(map.find(1).value());
    System.out.println(map.find(1).value());
    System.out.println(map.find(2).value());
    System.out.println(map.find(6));

    System.out.println("\n----------------TEST remove()----------------------");
    map.remove(1);
    System.out.println(map);
    map.remove(2);
    System.out.println(map);
    map.remove(3);
    System.out.println(map);
    map.remove(4);
    System.out.println(map);
    map.remove(5);
    System.out.println(map);

    System.out.println("\n---------------TEST makeEmpty()-------------------");
    map.makeEmpty();
    System.out.println(map);

  }

}
