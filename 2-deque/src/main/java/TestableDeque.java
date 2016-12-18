import java.util.Iterator;
import java.util.NoSuchElementException;

public class TestableDeque<Item> implements Iterable<Item> {
  private int size = 0;
  Node head = null;
  Node tail = null;

  public TestableDeque() {
  }                           // construct an empty deque

  public boolean isEmpty() {
    return size == 0;
  }                 // is the deque empty?

  public int size() {
    return size;
  }                        // return the number of items on the deque

  /*
  NOTE: corner case - what happens to tail when the first node is added?
   */
  public void addFirst(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    Node oldHead = head;
    head = new Node(item, head, null);
    if (oldHead != null) {
      oldHead.prev = head;
    } else {
//    if (size == 0) {
      tail = head;
    }
    size++;
  }          // add the item to the front

  public void addLast(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    Node oldTail = tail;
    tail = new Node(item, null, tail);
    if (oldTail != null) {

      oldTail.next = tail;
    } else {
//    if (size == 0) {
      head = tail;
    }
    size++;
  }           // add the item to the end

  public Item removeFirst() {
    if (head == null) {
      throw new NoSuchElementException();
    }
    Item value = head.value;
    head = head.next;
    if (head != null) {
      head.prev = null;
    } else {
      tail = null;
    }
    size--;
    return value;

  }                // remove and return the item from the front

  public Item removeLast() {
    if (tail == null) {
      throw new NoSuchElementException();
    }
    Item value = tail.value;
    tail = tail.prev;
    if (tail != null) {
      tail.next = null;
    } else {
      head = null;
    }
    size--;
    return value;
  }                 // remove and return the item from the end

  public Iterator<Item> iterator() {
    return new Iterator<Item>() {

      Node current = head;

      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public Item next() {
        if (current == null) {
          throw new NoSuchElementException();
        }
        Item value = current.value;
        current = current.next;
        return value;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }         // return an iterator over items in order from front to end

  public static void main(String[] args) {

  }   // unit testing

  class Node {
    Item value;
    Node next;
    Node prev;

    Node(Item value, Node next, Node prev) {
      this.value = value;
      this.next = next;
      this.prev = prev;
    }

    @Override
    public String toString() {
      String nextRep = next == null ? "null" : "Node";
      String prevRep = prev == null ? "null" : "Node";
      return "Node{" +
          "value=" + value +
          ", next=" + nextRep +
          ", prev=" + prevRep +
          '}';
    }

    public String forwardString() {
      String nextRep = next == null ? "null" : next.forwardString();
      String prevRep = prev == null ? "null" : "Node";
      return "Node{" +
          "value=" + value +
          ", next=" + nextRep +
          ", prev=" + prevRep +
          '}';

    }

    public String backwardString() {
      String nextRep = next == null ? "null" : "Node";
      String prevRep = prev == null ? "null" : prev.backwardString();
      return "Node{" +
          "value=" + value +
          ", next=" + nextRep +
          ", prev=" + prevRep +
          '}';

    }

  }
}
