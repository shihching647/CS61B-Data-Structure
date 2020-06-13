package project.pj1;/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes
 *  a PixImage object.  Descriptions of the methods you must implement appear
 *  below.  They include constructors of the form
 *
 *      public RunLengthEncoding(int width, int height);
 *      public RunLengthEncoding(int width, int height, int[] red, int[] green,
 *                               int[] blue, int[] runLengths) {
 *      public RunLengthEncoding(PixImage image) {
 *
 *  that create a run-length encoding of a PixImage having the specified width
 *  and height.
 *
 *  The first constructor creates a run-length encoding of a PixImage in which
 *  every pixel is black.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts a PixImage object into a run-length encoding of that image.
 *
 *  See the README file accompanying this project for additional details.
 */

import java.util.Arrays;

public class RunLengthEncoding implements Iterable {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
  public static final short MIN_PIXEL_VALUE = 0;
  public static final short MAX_PIXEL_VALUE = 255;
  public static final int PIXEL_VALUE = 0;
  public static final int TIMES = 1;
  public static final int RED = 0;
  public static final int GREEN = 1;
  public static final int BLUE = 2;
  private int width;
  private int height;
  private DList redStrip;
  private DList greenStrip;
  private DList blueStrip;
  private int stripLength; //只針對灰階圖使用(因為RGB長度都相同)

  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with two parameters) constructs a run-length
   *  encoding of a black PixImage of the specified width and height, in which
   *  every pixel has red, green, and blue intensities of zero.
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   */

  public RunLengthEncoding(int width, int height) {
    // Your solution here.
      this.width = width;
      this.height = height;
      redStrip = new DList(new int[]{0, width * height});
      greenStrip = new DList(new int[]{0, width * height});
      blueStrip = new DList(new int[]{0, width * height});
      this.stripLength = 1;
  }

  /**
   *  RunLengthEncoding() (with six parameters) constructs a run-length
   *  encoding of a PixImage of the specified width and height.  The runs of
   *  the run-length encoding are taken from four input arrays of equal length.
   *  Run i has length runLengths[i] and RGB intensities red[i], green[i], and
   *  blue[i].
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   *  @param red is an array that specifies the red intensity of each run.
   *  @param green is an array that specifies the green intensity of each run.
   *  @param blue is an array that specifies the blue intensity of each run.
   *  @param runLengths is an array that specifies the length of each run.
   *
   *  NOTE:  All four input arrays should have the same length (not zero).
   *  All pixel intensities in the first three arrays should be in the range
   *  0...255.  The sum of all the elements of the runLengths array should be
   *  width * height.  (Feel free to quit with an error message if any of these
   *  conditions are not met--though we won't be testing that.)
   */

  public RunLengthEncoding(int width, int height, int[] red, int[] green,
                           int[] blue, int[] runLengths) {
    // Your solution here.

      //Test for the four arrays length
      if(!(red.length == green.length && green.length == blue.length && blue.length == runLengths.length)) {
          throw new IllegalArgumentException("The four input arrays must have the same length.");
      }

      //Test for the red, green, blue arrays
      for(int i = 0; i < red.length; i++) {
          if(red[i] < MIN_PIXEL_VALUE || red[i] > MAX_PIXEL_VALUE || green[i] < MIN_PIXEL_VALUE || green[i] > MAX_PIXEL_VALUE
                  || blue[i] < MIN_PIXEL_VALUE || blue[i] > MAX_PIXEL_VALUE) {
              throw new IllegalArgumentException("The red, green and blue pixel arrays should have value between 0 and 255.");
          }
      }

      this.width = width;
      this.height = height;
      this.stripLength = runLengths.length;
      redStrip = new DList();
      greenStrip = new DList();
      blueStrip = new DList();
      for(int i = 0; i < stripLength; i++) {
          redStrip.insertEnd(new int[]{ red[i], runLengths[i]});
          greenStrip.insertEnd(new int[]{ green[i], runLengths[i]});
          blueStrip.insertEnd(new int[]{ blue[i], runLengths[i]});
      }
  }

  /**
   *  getWidth() returns the width of the image that this run-length encoding
   *  represents.
   *
   *  @return the width of the image that this run-length encoding represents.
   */

  public int getWidth() {
    // Replace the following line with your solution.
    return width;
  }

  /**
   *  getHeight() returns the height of the image that this run-length encoding
   *  represents.
   *
   *  @return the height of the image that this run-length encoding represents.
   */
  public int getHeight() {
    // Replace the following line with your solution.
    return height;
  }

  /**
   *  iterator() returns a newly created RunIterator that can iterate through
   *  the runs of this RunLengthEncoding.
   *
   *  @return a newly created RunIterator object set to the first run of this
   *  RunLengthEncoding.
   */
  public RunIterator iterator() {
    // Replace the following line with your solution.
      System.out.println("iterator被呼叫!!!");
      RunIterator it = new RunIterator(redStrip, greenStrip, blueStrip, stripLength); //stripLength只針對灰階圖使用(因為RGB長度都相同)
      return it;
    // You'll want to construct a new RunIterator, but first you'll need to
    // write a constructor in the RunIterator class.
  }

  /**
   *  toPixImage() converts a run-length encoding of an image into a PixImage
   *  object.
   *
   *  @return the PixImage that this RunLengthEncoding encodes.
   */
  public PixImage toPixImage() {
    // Replace the following line with your solution.
      PixImage image = new PixImage(width, height);
      buildPixel(image, RED);
      buildPixel(image, GREEN);
      buildPixel(image, BLUE);
      return image;
  }

  private void buildPixel(PixImage image, int component) {
      DList strip = new DList();
      switch (component) {
          case RED: strip = redStrip; break;
          case GREEN: strip = greenStrip; break;
          case BLUE: strip = blueStrip; break;
      }
      DListNode node = strip.getHead();
      int currentLength = 0;
      while(node != null) {
          int l = ((int[]) node.getItem())[TIMES];
          short pixelValue = (short)((int[]) node.getItem())[PIXEL_VALUE];
          for(int x = currentLength; x < currentLength + l; x++) {
              int x_loc = x % width;
              int y_loc = x / width;
              switch (component) {
                  case RED: image.setPixel(x_loc, y_loc, pixelValue, image.getGreen(x_loc, y_loc), image.getBlue(x_loc, y_loc)); break;
                  case GREEN: image.setPixel(x_loc, y_loc, image.getRed(x_loc, y_loc), pixelValue, image.getBlue(x_loc, y_loc)); break;
                  case BLUE: image.setPixel(x_loc, y_loc, image.getRed(x_loc, y_loc), image.getGreen(x_loc, y_loc), pixelValue); break;
              }
          }
          currentLength += l;
          node = node.getNext();
      }
  }

  /**
   *  toString() returns a String representation of this RunLengthEncoding.
   *
   *  This method isn't required, but it should be very useful to you when
   *  you're debugging your code.  It's up to you how you represent
   *  a RunLengthEncoding as a String.
   *
   *  @return a String representation of this RunLengthEncoding.
   */
  public String toString() {
    // Replace the following line with your solution.
      StringBuilder sb = new StringBuilder();
      DListNode redNode = redStrip.getHead();
      while (redNode != null) {
          sb.append(Arrays.toString((int[])redNode.getItem()) + ", ");
          redNode = redNode.getNext();
      }
      sb.append("\n");
      DListNode greenNode = greenStrip.getHead();
      while (greenNode != null) {
          sb.append(Arrays.toString((int[])greenNode.getItem()) + ", ");
          greenNode = greenNode.getNext();
      }
      sb.append("\n");
      DListNode blueNode = greenStrip.getHead();
      while (blueNode != null) {
          sb.append(Arrays.toString((int[])blueNode.getItem()) + ", ");
          blueNode = blueNode.getNext();
      }
      return sb.toString();
  }


  /**
   *  The following methods are required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of a specified PixImage.
   * 
   *  Note that you must encode the image in row-major format, i.e., the second
   *  pixel should be (1, 0) and not (0, 1).
   *
   *  @param image is the PixImage to run-length encode.
   */
  public RunLengthEncoding(PixImage image) {
    // Your solution here, but you should probably leave the following line
    // at the end.
      this.width = image.getWidth();
      this.height = image.getHeight();
      redStrip = getStrip(image, RED);
      greenStrip = getStrip(image, GREEN);
      blueStrip = getStrip(image, BLUE);
      check();
  }

  private DList getStrip(PixImage image, int component) {
      DList strip = new DList();
      short curPixel = PixImage.getPixelComponent(0, 0, component, image);
      int length = 0;
      int stripLength = 0;
      for(int y = 0; y < height; y++) {
          for(int x = 0; x < width; x++) {
              if(PixImage.getPixelComponent(x, y, component, image) == curPixel) {
                  length += 1;
              } else {
                  strip.insertEnd(new int[]{curPixel, length});
                  stripLength++;
                  curPixel = PixImage.getPixelComponent(x, y, component, image);
                  length = 1;
              }
          }
      }
      strip.insertEnd(new int[]{curPixel, length}); //最後要再多跑一次, 把最後一個run加回去
      stripLength++;
      this.stripLength = stripLength;  //stripLength只針對灰階圖使用(因為RGB長度都相同)
      return strip;
  }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same RGB intensities, or if the sum of
   *  all run lengths does not equal the number of pixels in the image.
   */
  public void check() {
      checkInvariants(RED);
      checkInvariants(GREEN);
      checkInvariants(BLUE);
  }

  private void checkInvariants(int component) {
      DList strip = new DList();
      switch (component) {
          case RED: strip = redStrip; break;
          case GREEN: strip = greenStrip; break;
          case BLUE: strip = blueStrip; break;
      }
      DListNode node = strip.getHead();
      int totalLength = 0;
      while (node != null) {
          //1.檢查任兩個run的pixel value絕對不一樣
          if(node != strip.getHead() && node != strip.getTail()){
              int prev = ((int[])node.getPrev().getItem())[PIXEL_VALUE];
              int cur = ((int[])node.getItem())[PIXEL_VALUE];
              int next = ((int[])node.getNext().getItem())[PIXEL_VALUE];
              if(prev == cur || next == cur){
                  System.err.println("錯誤!有兩個run的pixel value相同!!");
              }
          }
          //計算length
          int length = ((int[])node.getItem())[TIMES];
          if(length < 1) System.err.println("有某一個run的長度小於1!!"); //2.檢查每一個run的length都不小於1
          totalLength += length;
          node = node.getNext();
      }
      //3.檢查總長度是否等於 width * height
      if(totalLength != width * height) {
          System.err.println("總長度錯誤!!");
      }
  }



  /**
   *  The following method is required for Part IV.
   */

  /**
   *  setPixel() modifies this run-length encoding so that the specified color
   *  is stored at the given (x, y) coordinates.  The old pixel value at that
   *  coordinate should be overwritten and all others should remain the same.
   *  The updated run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs with exactly the same RGB color.
   *
   *  @param x the x-coordinate of the pixel to modify.
   *  @param y the y-coordinate of the pixel to modify.
   *  @param red the new red intensity to store at coordinate (x, y).
   *  @param green the new green intensity to store at coordinate (x, y).
   *  @param blue the new blue intensity to store at coordinate (x, y).
   */
  public void setPixel(int x, int y, short red, short green, short blue) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
      int length = (y * width) + (x + 1);
//      setPixelByLength(length, RED, red);
//      setPixelByLength(length, GREEN, green);
//      setPixelByLength(length, BLUE, blue);
      setPixelByLength2(length, RED, red);
      setPixelByLength2(length, GREEN, green);
      setPixelByLength2(length, BLUE, blue);
      check();
  }

  private void setPixelByLength(int length, int component, short value) {
      DList strip = new DList();
      switch (component) {
          case RED: strip = redStrip; break;
          case GREEN: strip = greenStrip; break;
          case BLUE: strip = blueStrip; break;
      }
      DListNode node = strip.getHead();
      int curLength = 0;
      while(curLength < length) {
          curLength += ((int[]) node.getItem())[TIMES];
          node = node.getNext();
      }

      short prevPixel = -1, originalPixel = -1,  nextPixel = -1;
      int prevLength = -1, originalLength = -1, nextLength = -1;
      DListNode prevNode = null, originalNode = null, nextNode = null;
      if(node == null) {
          node = new DListNode();
          node.setPrev(strip.getTail());
          originalNode = strip.getTail();
          originalPixel = (short) ((int[]) originalNode.getItem())[PIXEL_VALUE];
          originalLength = ((int[]) originalNode.getItem())[TIMES];
          prevNode = originalNode.getPrev();
          prevPixel = (short) ((int[]) prevNode.getItem())[PIXEL_VALUE];
          prevLength = ((int[])prevNode.getItem())[TIMES];
      }else {
          if(node.getPrev() != strip.getHead()){
              prevNode = node.getPrev().getPrev();
              prevPixel = (short) ((int[]) prevNode.getItem())[PIXEL_VALUE];
              prevLength = ((int[])prevNode.getItem())[TIMES];
          }
          if(node != null){
              nextNode = node;
              nextPixel = (short) ((int[]) nextNode.getItem())[PIXEL_VALUE];
              nextLength = ((int[]) nextNode.getItem())[TIMES];
          }
          if(node != strip.getHead()){
              originalNode = node.getPrev();
              originalPixel = (short) ((int[]) originalNode.getItem())[PIXEL_VALUE];
              originalLength = ((int[]) originalNode.getItem())[TIMES];
          }
      }

      if(originalPixel != value) {
          if(prevPixel != -1 && prevPixel == value && originalLength == 1){
              node.getPrev().getPrev().setItem(new int[]{prevPixel, prevLength + originalLength});
              node.getPrev().getPrev().setNext(node);
              node.setPrev(node.getPrev().getPrev());
              if(originalNode != null){
                  strip.remove(originalNode);
              }
          } else if(nextPixel != -1 && nextPixel == value) {
              node.setItem(new int[]{nextPixel, nextLength + originalLength});
              if(node.getPrev() != strip.getHead()){
                  node.getPrev().getPrev().setNext(node);
                  node.setPrev(node.getPrev().getPrev());
              }
              if(originalNode != null){
                  strip.remove(originalNode);
              }
          } else {
              if(originalLength == 1) {
                  node.getPrev().setItem(new int[]{value, 1});
              } else {
                  if(originalNode != null){
                      strip.remove(originalNode);
                  }
                  DListNode newNode = new DListNode(new int[]{value, 1});
                  DListNode originalNode2 = new DListNode(new int[]{originalPixel, curLength - length});
                  DListNode originalNode1 = new DListNode(new int[]{originalPixel, originalLength - 1 - (curLength - length)});
                  if(((int[]) originalNode1.getItem())[TIMES] > 0 && ((int[]) originalNode2.getItem())[TIMES] > 0) {
                      strip.insertAfter(prevNode, originalNode1);
                      strip.insertAfter(originalNode1, newNode);
                      strip.insertAfter(newNode, originalNode2);
                  } else if(((int[]) originalNode1.getItem())[TIMES] > 0){
                      strip.insertAfter(prevNode, originalNode1);
                      strip.insertAfter(originalNode1, newNode);
                  } else if(((int[]) originalNode2.getItem())[TIMES] > 0) {
                      strip.insertAfter(prevNode, newNode);
                      strip.insertAfter(newNode, originalNode2);
                  }
              }
          }
      }
      verifyDuplicate(strip);
      verifyLengthZero(strip);
  }

    private void setPixelByLength2(int length, int component, short value) {
        DList strip = new DList();
        switch (component) {
            case RED: strip = redStrip; break;
            case GREEN: strip = greenStrip; break;
            case BLUE: strip = blueStrip; break;
        }
        DListNode node = strip.getHead();
        int curLength = 0;
        while(curLength < length) {
            curLength += ((int[]) node.getItem())[TIMES];
            node = node.getNext();
        }

        short prevPixel = -1, originalPixel = -1,  nextPixel = -1;
        int prevLength = -1, originalLength = -1, nextLength = -1;
        DListNode prevNode = null, originalNode = null, nextNode = null;

        if(node == null) {
            node = new DListNode();
            node.setPrev(strip.getTail());
            originalNode = strip.getTail();
            originalPixel = (short) ((int[]) originalNode.getItem())[PIXEL_VALUE];
            originalLength = ((int[]) originalNode.getItem())[TIMES];
            prevNode = originalNode.getPrev();
            prevPixel = (short) ((int[]) prevNode.getItem())[PIXEL_VALUE];
            prevLength = ((int[])prevNode.getItem())[TIMES];
        }else {
            if(node.getPrev() != strip.getHead()){
                prevNode = node.getPrev().getPrev();
                prevPixel = (short) ((int[]) prevNode.getItem())[PIXEL_VALUE];
                prevLength = ((int[])prevNode.getItem())[TIMES];
            }
            if(node != null){
                nextNode = node;
                nextPixel = (short) ((int[]) nextNode.getItem())[PIXEL_VALUE];
                nextLength = ((int[]) nextNode.getItem())[TIMES];
            }
            if(node != strip.getHead()){
                originalNode = node.getPrev();
                originalPixel = (short) ((int[]) originalNode.getItem())[PIXEL_VALUE];
                originalLength = ((int[]) originalNode.getItem())[TIMES];
            }
        }
        verifyDuplicate(strip);
        verifyLengthZero(strip);
    }

  private void verifyDuplicate(DList strip) {
      DListNode node = strip.getHead();
      while (node != strip.getTail()) {
          int pixel = ((int[]) node.getItem())[PIXEL_VALUE];
          int length = ((int[]) node.getItem())[TIMES];
          DListNode nextNode = node.getNext();
          int nextPixel = ((int[]) nextNode.getItem())[PIXEL_VALUE];
          int nextLength = ((int[]) nextNode.getItem())[TIMES];
          DListNode newNode;
          if(pixel == nextPixel) {
              newNode = new DListNode(new int[]{pixel, length + nextLength});
              strip.insertAfter(nextNode, newNode);
              strip.remove(node);
              strip.remove(nextNode);
              node = newNode;
              continue;
          }
          node = node.getNext();
      }
  }

  private void verifyLengthZero(DList strip) {
      DListNode node = strip.getHead();
      while(node != strip.getTail()) {
          int length = ((int[]) node.getItem())[TIMES];
          DListNode nextNode = node.getNext();
          if(length == 0) {
              strip.remove(node);
          }
          node = nextNode;
      }
  }

  /**
   * TEST CODE:  YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT.
   * You are welcome to add tests, though.  Methods below this point will not
   * be tested.  This is not the autograder, which will be provided separately.
   */


  /**
   * doTest() checks whether the condition is true and prints the given error
   * message if it is not.
   *
   * @param b the condition to check.
   * @param msg the error message to print if the condition is false.
   */
  private static void doTest(boolean b, String msg) {
    if (b) {
      System.out.println("Good.");
    } else {
      System.err.println(msg);
    }
  }

  /**
   * array2PixImage() converts a 2D array of grayscale intensities to
   * a grayscale PixImage.
   *
   * @param pixels a 2D array of grayscale intensities in the range 0...255.
   * @return a new PixImage whose red, green, and blue values are equal to
   * the input grayscale intensities.
   */
  private static PixImage array2PixImage(int[][] pixels) {
    int width = pixels.length;
    int height = pixels[0].length;
    PixImage image = new PixImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
                       (short) pixels[x][y]);
      }
    }

    return image;
  }

  /**
   * setAndCheckRLE() sets the given coordinate in the given run-length
   * encoding to the given value and then checks whether the resulting
   * run-length encoding is correct.
   *
   * @param rle the run-length encoding to modify.
   * @param x the x-coordinate to set.
   * @param y the y-coordinate to set.
   * @param intensity the grayscale intensity to assign to pixel (x, y).
   */
  private static void setAndCheckRLE(RunLengthEncoding rle,
                                     int x, int y, int intensity) {
    rle.setPixel(x, y,
                 (short) intensity, (short) intensity, (short) intensity);
    rle.check();
  }

  /**
   * main() runs a series of tests of the run-length encoding code.
   */
  public static void main(String[] args) {
    // Be forwarned that when you write arrays directly in Java as below,
    // each "row" of text is a column of your image--the numbers get
    // transposed.
    PixImage image1 = array2PixImage(new int[][] { { 0, 3, 6 },
                                                   { 1, 4, 7 },
                                                   { 2, 5, 8 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x3 image.  Input image:");
    System.out.print(image1);
    RunLengthEncoding rle1 = new RunLengthEncoding(image1);
    rle1.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle1.getWidth() == 3 && rle1.getHeight() == 3,
           "RLE1 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(image1.equals(rle1.toPixImage()),
           "image1 -> RLE1 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 42);
    image1.setPixel(0, 0, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           /*
                       array2PixImage(new int[][] { { 42, 3, 6 },
                                                    { 1, 4, 7 },
                                                    { 2, 5, 8 } })),
           */
           "Setting RLE1[0][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 0, 42);
    image1.setPixel(1, 0, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 1, 2);
    image1.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 0);
    image1.setPixel(0, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 7);
    image1.setPixel(2, 2, (short) 7, (short) 7, (short) 7);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 7 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 42);
    image1.setPixel(2, 2, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 2, 42);
    image1.setPixel(1, 2, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][2] = 42 fails.");


    PixImage image2 = array2PixImage(new int[][] { { 2, 3, 5 },
                                                   { 2, 4, 5 },
                                                   { 3, 4, 6 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on another 3x3 image.  Input image:");
    System.out.print(image2);
    RunLengthEncoding rle2 = new RunLengthEncoding(image2);
    rle2.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle2.getWidth() == 3 && rle2.getHeight() == 3,
           "RLE2 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(rle2.toPixImage().equals(image2),
           "image2 -> RLE2 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 0, 1, 2);
    image2.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 2, 0, 2);
    image2.setPixel(2, 0, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[2][0] = 2 fails.");


    PixImage image3 = array2PixImage(new int[][] { { 0, 5 },
                                                   { 1, 6 },
                                                   { 2, 7 },
                                                   { 3, 8 },
                                                   { 4, 9 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 5x2 image.  Input image:");
    System.out.print(image3);
    RunLengthEncoding rle3 = new RunLengthEncoding(image3);
    rle3.check();
    System.out.println("Testing getWidth/getHeight on a 5x2 encoding.");
    doTest(rle3.getWidth() == 5 && rle3.getHeight() == 2,
           "RLE3 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 5x2 encoding.");
    doTest(rle3.toPixImage().equals(image3),
           "image3 -> RLE3 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 4, 0, 6);
    image3.setPixel(4, 0, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[4][0] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 1, 6);
    image3.setPixel(0, 1, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][1] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 0, 1);
    image3.setPixel(0, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][0] = 1 fails.");


    PixImage image4 = array2PixImage(new int[][] { { 0, 3 },
                                                   { 1, 4 },
                                                   { 2, 5 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x2 image.  Input image:");
    System.out.print(image4);
    RunLengthEncoding rle4 = new RunLengthEncoding(image4);
    rle4.check();
    System.out.println("Testing getWidth/getHeight on a 3x2 encoding.");
    doTest(rle4.getWidth() == 3 && rle4.getHeight() == 2,
           "RLE4 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x2 encoding.");
    doTest(rle4.toPixImage().equals(image4),
           "image4 -> RLE4 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 2, 0, 0);
    image4.setPixel(2, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[2][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 0);
    image4.setPixel(1, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 1);
    image4.setPixel(1, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 1 fails.");
  }
}
