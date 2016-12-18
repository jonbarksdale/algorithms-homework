import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Corner cases.
 * The order of two or more iterators to the same randomized queue must be mutually independent; each iterator must maintain its own random order.
 * Throw a java.lang.NullPointerException if the client attempts to add a null item;
 * throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue;
 * throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
 * throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
 * <p>
 * Performance requirements.
 * Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) in constant amortized time. That is, any sequence of m randomized queue operations (starting from an empty queue) should take at most cm steps in the worst case, for some constant c. A randomized queue containing n items must use at most 48n + 192 bytes of memory. Additionally, your iterator implementation must support operations next() and hasNext() in constant worst-case time; and construction in linear time; you may (and will need to) use a linear amount of extra memory per iterator.
 *
 * @param <Item>
 */
public class TestableRandomizedQueue<Item> implements Iterable<Item> {
  private final static int INITIAL_CAPACITY = 1;

  Item[] items;
  int size = 0;

  int length() {
    return items.length;
  }

  public TestableRandomizedQueue() {
    items = (Item[]) new Object[INITIAL_CAPACITY];

  }                // construct an empty randomized queue

  public boolean isEmpty() {
    return size == 0;
  }                // is the queue empty?

  public int size() {
    return size;
  }                       // return the number of items on the queue

  public void enqueue(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    if (size == items.length) {
      resize(2 * items.length);
    }

    items[size++] = item;
  }          // add the item

  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    int randomIndex = StdRandom.uniform(size);

    Item result = items[randomIndex];
    size--;
    if (randomIndex != size) {
      items[randomIndex] = items[size];
    }
    items[size] = null;

    if (size > 0 && size == items.length / 4) {
      resize(items.length / 2);
    }
    return result;
  }                   // remove and return a random item

  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    return items[StdRandom.uniform(size)];
  }                    // return (but do not remove) a random item

  private void resize(int capacity) {
    assert capacity >= size;
//    System.out.println("Resizing array to " + capacity);

    // textbook implementation
    Item[] temp = (Item[]) new Object[capacity];
    System.arraycopy(items, 0, temp, 0, size);
    items = temp;
  }

  // linear in current # of items
  public Iterator<Item> iterator() {
    Item[] iteratedItems = (Item[]) new Object[size];
    System.arraycopy(items, 0, iteratedItems, 0, size);
    StdRandom.shuffle(iteratedItems);
    // all ops in iterator - constant time
    return new Iterator<Item>() {
      int i = 0;

      @Override
      public boolean hasNext() {
        return i < iteratedItems.length;
      }

      @Override
      public Item next() {
        if (i == iteratedItems.length) {
          throw new NoSuchElementException();
        }
        return iteratedItems[i++];
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }        // return an independent iterator over items in random order
}