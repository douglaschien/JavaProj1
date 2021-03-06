/* DList.java */

public class DList {

  private DListNode head;//for now we just declare a variable called head of DListNode type but!! this node int[] has not been constructed yet!!
  // IMPORTANT!!!
  private DListNode tail;

  private int size;

  protected DListNode newNode(int[] item, DListNode prev, DListNode next) {
    return new DListNode(item, prev, next);
  }

  public DList() {
    //  Your solution here.
    head = null;
    tail=head;
    size = 0;
  }

  public DListNode getHead(){
    return head;
  }
  public DListNode getTail(){
    return tail;
  }



  public void insertBack(int[] item) {
    // Your solution here.
    if (size==0) {
      DListNode i = newNode(item, null,null);
      head=i;
      tail=i;
      size++;
    }else{
      DListNode i = newNode(item, tail,null);
      tail.next=i;
      tail=i;
      size++;
    }
  }

  public void remove(DListNode node) {
    // Your solution here.
    if (node==null) {
      return;
    }
    node.prev.next=node.next;
    node.next.prev=node.prev;
    size--;
  }

  public String toString() {
    String result = "[  ";
    DListNode current = head.next;
    // System.out.println(head.next);
    while (current != head) {
      result = result + current.item + "  ";
      current = current.next;
    }
    return result + "]";
  }

}
