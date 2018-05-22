/* RunLengthEncoding.java */

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

import java.util.Iterator;

public class RunLengthEncoding implements Iterable {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
  private int width;
  private int height;
  private DList list;




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
    this.width=width;
    this.height=height;
    list=new DList();
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
    this.width=width;
    this.height=height;
    list=new DList();
    for (int i=0;i<runLengths.length;i++) {
      int[] item={red[i],green[i],blue[i],runLengths[i]};
      this.list.insertBack(item);
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
    RunIterator a = new RunIterator(list);
    return a;
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
    PixImage a = new PixImage(this.width,this.height);
    // DlistNode b=this.head.next;
    RunIterator iter = this.iterator();
    int x= -1, y=0;
    while(iter.hasNext()){
      int[] item = iter.next();
      int num=item[3];
      int red=item[0];
      int green=item[1];
      int blue=item[2];
      for (int i=0; i<num; i++) {      //column
        x+=1;//row
        if (x>=width) {
          y+=1;
          x%=width;
        }
        a.setPixel(x,y,(short)red,(short)green,(short)blue);
      }
    }
    System.out.println(a.getRed(0,1));
    return a;
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
    DListNode a = list.getHead();
    String s= "STP";
    while(a!=null){
      s+=" => ";
      s+="["+a.item[0]+","+a.item[3]+"]";
      a=a.next;
    }
    return s;
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
    width=image.getWidth();
    height=image.getHeight();
    DList a = new DList();
    for (int j=0; j<height; j++) {
      for (int i=0; i<width ; i++) {
        int[] item={image.getRed(i,j),image.getGreen(i,j),image.getBlue(i,j),1};
        a.insertBack(item);
      }
    }
    // DList newList=new DList();
    DListNode c= a.getHead();
    DListNode p= c.next;
    while(c!=null&&p!=null){//why the condition includes c!=null&&p!=null? Because inside the cycle body, we use both p and c's fields. we must assure that both of them are not null first !!! or we get nullpointerexception!!!
    int aggregate=1;
      while (c.item[0]==p.item[0]&&c.item[1]==p.item[1]&&c.item[2]==p.item[2]) {//why we don't put p=c.next first in the cycle body? Because if we do so, p could become null, then p.item be nullpointer!!!
      aggregate+=1;
      p=p.next;
    }
    c.item[3]=aggregate;
    c.next=p;
    c=c.next;
    p=c.next;
  }
    // System.out.println("tail is null?:"+(newList.getTail()==null));

  this.list=a;

    // check();
    // // System.out.println("newList is null?:"+(newList==null));
    // System.out.println("list is null?:"+(this.list==null));
    // System.out.println("tail is null?:"+(this.list.getTail()==null));
}

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same RGB intensities, or if the sum of
   *  all run lengths does not equal the number of pixels in the image.
   */
  public void check() {
    // Your solution here.
    RunIterator iter = this.iterator();
    int aggregate=0;
    DListNode current=null;
    while(iter.hasNext()){
      int[] k= iter.next();
      current= iter.getCurrent();
      int[] t=current.item;
      aggregate+=k[3];
      if (k[0]==t[0]&&k[1]==t[1]&&k[2]==t[2]) {
        System.out.println("FALSE");
        return;
      }
    }
    // DListNode sail = this.list.getTail();
    // System.out.println("list in check() is null?:"+(list==null));
    // System.out.println("sail is null?:"+(sail==null));
    if ((aggregate+current.item[3])!=width*height) {
      System.out.println("FFALSE");
      return;
    }
    System.out.println("TRUE");
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
    int lc=y*width+x+1;
    int c=0;
    DListNode a=list.getHead();
    while(lc>c&&a!=null){
      c+=a.item[3];
      a=a.next;
    }
    c-=a.item[3];
    a=a.prev;
    int num=lc-c;
    if (a.item[0]==red) {
      return;
    }else if (a!=list.getHead()&&a!=list.getTail()) {
      if (num!=1&&num!=a.item[3]) {
        int[] item = {red,green,blue,1};
        int[] itemBefore = {a.item[0],a.item[1],a.item[2],num-1};
        int[] itemAfter = {a.item[0],a.item[1],a.item[2],a.item[3]-num};
        DListNode newBefore = new DListNode(itemBefore,a.prev,null);
        DListNode newAfter = new DListNode(itemAfter,null,a.next);
        DListNode newNode = new DListNode(item,newBefore,newAfter);
        newBefore.next=newNode;
        newAfter.prev=newNode;
      }else if (num==1) {
        if (a.prev.item[0]!=red) {
          int[] item = {red,green,blue,1};
          int[] itemAfter = {a.item[0],a.item[1],a.item[2],a.item[3]-1};
          DListNode newAfter = new DListNode(itemAfter,null,a.next);
          DListNode newNode =new DListNode(item,a.prev,newAfter);
          newAfter.prev=newNode;
        }else{
          a.prev.item[3]+=1;
          a.item[3]-=1;
        }
      }else{
        if (a.next.item[0]!=red) {
          int[] item = {red,green,blue,1};
          int[] itemBefore = {a.item[0],a.item[1],a.item[2],a.item[3]-1};
          DListNode newBefore = new DListNode(itemBefore,a.prev,null);
          DListNode newNode =new DListNode(item,newBefore,a.next);
          newBefore.next=newNode;
        }else{
          a.next.item[3]+=1;
          a.item[3]-=1;
        }
      }
    }else if (a==list.getHead()) { // HEAD
      if (num!=1&&num!=a.item[3]) {
        int[] item = {red,green,blue,1};
        int[] itemBefore = {a.item[0],a.item[1],a.item[2],num-1};
        int[] itemAfter = {a.item[0],a.item[1],a.item[2],a.item[3]-num};
        DListNode newBefore = new DListNode(itemBefore,a.prev,null);
        DListNode newAfter = new DListNode(itemAfter,null,a.next);
        DListNode newNode = new DListNode(item,newBefore,newAfter);
        newBefore.next=newNode;
        newAfter.prev=newNode;
      }else if (num==1) {  
        int[] item = {red,green,blue,1};
        int[] itemAfter = {a.item[0],a.item[1],a.item[2],a.item[3]-1};
        DListNode newAfter = new DListNode(itemAfter,null,a.next);
        DListNode newNode =new DListNode(item,null,newAfter);
        newAfter.prev=newNode; 
      }else{
        if (a.next.item[0]!=red) {
          int[] item = {red,green,blue,1};
          int[] itemBefore = {a.item[0],a.item[1],a.item[2],a.item[3]-1};
          DListNode newBefore = new DListNode(itemBefore,null,null);
          DListNode newNode =new DListNode(item,newBefore,a.next);
          newBefore.next=newNode;
        }else{
          a.next.item[3]+=1;
          a.item[3]-=1;
        }
      }
    }else if (a==list.getTail()) {
      if (num!=1&&num!=a.item[3]) {
        int[] item = {red,green,blue,1};
        int[] itemBefore = {a.item[0],a.item[1],a.item[2],num-1};
        int[] itemAfter = {a.item[0],a.item[1],a.item[2],a.item[3]-num};
        DListNode newBefore = new DListNode(itemBefore,a.prev,null);
        DListNode newAfter = new DListNode(itemAfter,null,a.next);
        DListNode newNode = new DListNode(item,newBefore,newAfter);
        newBefore.next=newNode;
        newAfter.prev=newNode;
      }else if (num==1) {
        if (a.prev.item[0]!=red) {
          int[] item = {red,green,blue,1};
          int[] itemAfter = {a.item[0],a.item[1],a.item[2],a.item[3]-1};
          DListNode newAfter = new DListNode(itemAfter,null,null);
          DListNode newNode =new DListNode(item,a.prev,newAfter);
          newAfter.prev=newNode;
        }else{
          a.prev.item[3]+=1;
          a.item[3]-=1;
        }
      }else{
        int[] item = {red,green,blue,1};
        int[] itemBefore = {a.item[0],a.item[1],a.item[2],a.item[3]-1};
        DListNode newBefore = new DListNode(itemBefore,a.prev,null);
        DListNode newNode =new DListNode(item,newBefore,null);
        newBefore.next=newNode;
      } //TAIL
    }

    check();
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
    System.out.println(rle1);

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
