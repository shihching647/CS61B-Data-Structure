package project.pj1;/* RunIterator.java */

/**
 *  The RunIterator class iterates over a RunLengthEncoding and allows other
 *  classes to inspect the runs in a run-length encoding, one run at a time.
 *  A newly constructed RunIterator "points" to the first run in the encoding
 *  used to construct it.  Each time next() is invoked, it returns a run
 *  (represented as an array of four ints); a sequence of calls to next()
 *  returns run in consecutive order until every run has been returned.
 *
 *  Client classes should never call the RunIterator constructor directly;
 *  instead they should invoke the iterator() method on a RunLengthEncoding
 *  object, which will construct a properly initialized RunIterator for the
 *  client.
 *
 *  Calls to hasNext() determine whether another run is available, or whether
 *  the iterator has reached the end of the run-length encoding.  When
 *  a RunIterator reaches the end of an encoding, it is no longer useful, and
 *  the next() method may throw an exception; thus it is recommended to check
 *  hasNext() before each call to next().  To iterate through the encoding
 *  again, construct a new RunIterator by invoking iterator() on the
 *  RunLengthEncoding and throw the old RunIterator away.
 *
 *  A RunIterator is not guaranteed to work if the underlying RunLengthEncoding
 *  is modified after the RunIterator is constructed.  (Especially if it is
 *  modified by setPixel().)
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("rawtypes")
public class RunIterator implements Iterator {

  /**
   *  Define any variables associated with a RunIterator object here.
   *  These variables MUST be private.
   */
    public static final int PIXEL_VALUE = 0;
    public static final int TIMES = 1;
    private DListNode currentNode;
    private DList runLength;


  /**
   *  RunIterator() constructs a new iterator starting with a specified run.
   *
   *  @param node the run where this iterator starts.
   */
  // Unlike all the other methods we have asked you to write, the RunIterator()
  // constructor does not have a predefined signature, because no outside
  // class should ever call this constructor except the iterator() method in
  // the RunLengthEncoding class.  The correct way for outside classes to
  // get access to a RunIterator is to call the iterator() method on a
  // RunLengthEncoding object.  You are welcome to add any parameters to the
  // constructor that you want so that your RunLengthEncoding.iterator()
  // implementation can construct a RunIterator that points to the first run of
  // the encoding.

    /**
     * 初始化Iterator(目前只適用於灰階圖, 所以RGB三個一起跑迴圈, sreipLength為RGB的run的長度(三個都會一樣))
     * @param redStrip
     * @param greenStrip
     * @param blueStrip
     * @param stripLength
     */
  RunIterator(DList redStrip, DList greenStrip, DList blueStrip, int stripLength) {
    // Your solution here.  You may add parameters to the method signature.
      runLength = new DList();
      DListNode redNode = redStrip.getHead();
      DListNode greenNode = greenStrip.getHead();
      DListNode blueNode = blueStrip.getHead();
      for(int i = 0; i < stripLength; i++) {
          int times = ((int[]) (redNode.getItem()))[TIMES];
          int red = ((int[]) (redNode.getItem()))[PIXEL_VALUE];
          int green = ((int[]) (greenNode.getItem()))[PIXEL_VALUE];
          int blue = ((int[]) (blueNode.getItem()))[PIXEL_VALUE];
          runLength.insertEnd(new int[]{times, red, green, blue});
          if(redNode != null && greenNode != null && blueNode != null) {
              redNode = redNode.getNext();
              greenNode = greenNode.getNext();
              blueNode = blueNode.getNext();
          }
      }
      currentNode = runLength.getHead();
  }

  /**
   *  hasNext() returns true if this iterator has more runs.  If it returns
   *  false, then the next call to next() may throw an exception.
   *
   *  @return true if the iterator has more elements.
   */
  public boolean hasNext() {
    // Replace the following line with your solution.
      if(currentNode == null) {
          return false;
      } else {
          return true;
      }
  }

  /**
   *  next() returns an array of 4 ints that specifies the current run in the
   *  sequence.  It also advances the iterator to the next run, so that the
   *  next call to next() will return the following run.
   *
   *  If "this" RunIterator has returned every run, it cannot be expected to
   *  behave well.  (Technically, it is supposed to throw a
   *  NoSuchElementException, but we haven't learned about exceptions yet.)
   *
   *  @return an array of 4 ints that specify the current run in the sequence.
   *  The pixel count is in index [0]; the red value is in index [1]; the green
   *  value is in index [2]; and the blue value is in index [3].
   *  @throws NoSuchElementException if the iteration has no more elements.
   *  (We strongly recommend calling hasNext() to check whether there are any
   *  more runs before calling next().)
   *
   *  The returned four-int array is constructed in next(), and can be
   *  discarded by the calling method after use.  The array should not be part
   *  of your RunLengthEncoding data structure!  It must be freshly constructed
   *  for the sole purpose of returning four ints.
   */
  public int[] next() throws NoSuchElementException{
    // Construct a new array of 4 ints, fill in its values, and return it.
    // Don't forget to advance the RunIterator's pointer so that the next
    // call to next() will return the subsequent run.

    // Replace the following line with your solution.
      if(currentNode == null) {
          throw new NoSuchElementException("All runs have been returned!!");
      }
      int[] result = (int[]) currentNode.getItem();
      System.out.println(Arrays.toString(result));
      currentNode = currentNode.getNext();
      return result;
  }

  /**
   *  remove() would remove from the underlying run-length encoding the run
   *  identified by this iterator, but we are NOT implementing it.
   *
   *  DO NOT CHANGE THIS METHOD.
   */
  public void remove() {
    throw new UnsupportedOperationException();
  }

  public static void main(String[] args) {
      DList redStrip = new DList();
      redStrip.insertEnd(new int[]{1,1});
      redStrip.insertEnd(new int[]{2,2});
      DList greenStrip = new DList();
      greenStrip.insertEnd(new int[]{3,1});
      greenStrip.insertEnd(new int[]{4,2});
      DList blueStrip = new DList();
      blueStrip.insertEnd(new int[]{5,1});
      blueStrip.insertEnd(new int[]{6,2});
      RunIterator it = new RunIterator(redStrip, greenStrip,blueStrip, 2);
      while(it.hasNext()) {
          System.out.println(Arrays.toString(it.next()));
      }
  }
}
