import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class TestableRandomizedQueueTest {

  private TestableRandomizedQueue<Integer> queue;

  @Before
  public void setUp() throws Exception {
    queue = new TestableRandomizedQueue<>();
  }

  @Test
  public void testEmptyQueue() throws Exception {
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
  }

  @Test(expected = NullPointerException.class)
  public void testEnqueue_null() throws Exception {
    queue.enqueue(null);
  }

  @Test(expected = NoSuchElementException.class)
  public void testDequeue_empty() throws Exception {
    queue.dequeue();
  }

  @Test(expected = NoSuchElementException.class)
  public void testSample_empty() throws Exception {
    queue.sample();
  }

  @Test
  public void testIterator_empty_hasNext() throws Exception {
    assertFalse(queue.iterator().hasNext());
  }

  @Test(expected = NoSuchElementException.class)
  public void testIterator_empty_next() throws Exception {
    queue.iterator().next();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testIterator_remove() throws Exception {
    queue.iterator().remove();
  }

  @Test
  public void happyPath() throws Exception {
    queue.enqueue(1);
    assertEquals(1, queue.size());
    assertEquals(1, queue.length());
    assertFalse(queue.isEmpty());

    assertEquals((Integer) 1, queue.dequeue());
    assertEquals(0, queue.size());
    assertEquals(1, queue.length());
    assertTrue(queue.isEmpty());


    queue.enqueue(1);
    queue.enqueue(2);
    queue.enqueue(3);

    assertEquals(3, queue.size());
    assertEquals(4, queue.length());
    assertFalse(queue.isEmpty());

    IntStream.range(4, 13).forEach(queue::enqueue);

    assertEquals(12, queue.size());
    assertEquals(16, queue.length());

    IntStream.range(0, 8).forEach(i -> System.out.println(queue.dequeue()));
    assertEquals(4, queue.size());
    assertEquals(8, queue.length());
  }

  @Test
  public void testIterator() throws Exception {
    final int samples = 40;
    IntStream.range(0, samples).forEach(queue::enqueue);

    Iterator<Integer> iter1 = queue.iterator();
    Iterator<Integer> iter2 = queue.iterator();

    int diffCount = IntStream.range(0, samples).map(i -> {
      Integer first = iter1.next();
      Integer second = iter2.next();
//      System.out.println(i + ": " + first + " : " + second);
      if (!Objects.equals(first, second)) {
        return 1;
      } else {
        return 0;
      }
    }).sum();

    assertTrue(diffCount > 0);
  }
}
