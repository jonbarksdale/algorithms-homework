import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
  private TestableDeque<Item> delegate;

  public Deque() {
    delegate = new TestableDeque<>();
  }                           // construct an empty deque

  public boolean isEmpty() {
    return delegate.isEmpty();
  }                 // is the deque empty?

  public int size() {
    return delegate.size();
  }                        // return the number of items on the deque

  /*
  NOTE: corner case - what happens to tail when the first node is added?
   */
  public void addFirst(Item item) {
    delegate.addFirst(item);
  }          // add the item to the front

  public void addLast(Item item) {
    delegate.addLast(item);
  }           // add the item to the end

  public Item removeFirst() {
    return delegate.removeFirst();
  }                // remove and return the item from the front

  public Item removeLast() {
    return delegate.removeLast();
  }                 // remove and return the item from the end

  public Iterator<Item> iterator() {
    return delegate.iterator();
  }         // return an iterator over items in order from front to end

  public static void main(String[] args) {

  }   // unit testing
}
