/* HashTableChained.java */

package homework.hw6.dict;

import homework.hw4.list.DList;
import homework.hw4.list.DListNode;

import java.util.Random;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
  private DList[] table;
  private int n = 0;
  private int capacity;
  private int prime = 109345121;
  private long scale, shift;
  private int times = 0; //紀錄collisions的次數
  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
    this.capacity = 2 * sizeEstimate;
    Random rand = new Random();
    scale = rand.nextInt(prime - 1) + 1;
    shift = rand.nextInt(prime);
    makeEmpty();
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    // Your solution here.
    this(97);
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
    return (int) (Math.abs(code * scale + shift) % prime % capacity);
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
    return n;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    return n == 0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
    int hashValue = compFunction(key.hashCode());
    Entry entry = createEntry(key, value);
    DList chain = table[hashValue];
    if(chain.front() != null) {
      checkCollision(chain, entry);
    }
    chain.insertBack(entry);
    n++;
    if(n > capacity / 2)         // keep load factor <= 0.5
      resize(2 * capacity - 1);
    return entry;
  }

  //check if collision happened, 必須檢查該bucket裡的所有entry, 才能確定collision是否發生
  private void checkCollision(DList chain, Entry entry) {
    DListNode node = chain.front();
    while (node != null) {
      Entry oldEntry = (Entry) node.item;
      //collision未發生,直接return
      if(entry.key.equals(oldEntry.key)) {
        return;
      }
      node = chain.next(node);
    }
    System.out.println("Collision happened " + ++times + " times.");
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    // Replace the following line with your solution.
    int hashValue = compFunction(key.hashCode());
    DList chain = table[hashValue];
    if (chain.front() != null) {
      DListNode pos = findEntryInChain(chain, key);
      if (pos != null) return (Entry) pos.item;
    }
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
    int hashValue = compFunction(key.hashCode());
    DList chain = table[hashValue];
    DListNode pos = findEntryInChain(chain, key);
    if (pos != null) {
      chain.remove(pos);
      n--;
      return (Entry) pos.item;
    }
    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
    table = new DList[capacity];
    for (int i = 0; i < capacity; i++) {
      table[i] = new DList();
    }
    n = 0;
  }

  private Entry createEntry(Object key, Object value) {
    Entry entry = new Entry();
    entry.key = key;
    entry.value = value;
    return entry;
  }

  private DListNode findEntryInChain(DList chain, Object key) {
    DListNode node = chain.front();
    while (node != null) {
      Entry oldEntry = (Entry) node.item;
      if (oldEntry.key.equals(key)) {
        return node;
      }
      node = chain.next(node);
    }
    return null;
  }

  private void resize(int capacity) {
    Entry[] buffer = new Entry[n];
    int i = 0;
    for (DList chain : table) {
      DListNode node = chain.front();
      while (node != null) {
        buffer[i++] = (Entry) node.item;
        node = chain.next(node);
      }
    }

    this.capacity = capacity;
    makeEmpty();
    for (Entry entry : buffer) {
      insert(entry.key, entry.value);
    }
  }

  public String toString() {
    StringBuilder sb = new StringBuilder("Capacity = "+capacity+", size = "+n+"\n[");
    for (DList chain : table) {
      DListNode node = chain.front();
      while (node != null) {
        Entry entry = (Entry) node.item;
        sb.append("(" + entry.key + "," + entry.value + "),");
        node = chain.next(node);
      }
    }
    return n > 0 ? sb.append("\b]").toString() : sb.append("]").toString();
  }

  public void showBucket() {
    for (int i = 0; i < table.length; i++) {
      DList chain = table[i];
      System.out.print("bucket_" + i + ": ");
      DListNode node = chain.front();
      while (node != null) {
        System.out.print("*");
        node = chain.next(node);
      }
      System.out.println();
    }
  }
}
