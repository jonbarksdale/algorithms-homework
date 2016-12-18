import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.junit.Assert.*;

public class TestableDequeTest {

  /*
   * test cases -
    * constructor?
    * addFirst
    *
   */

  TestableDeque<String> deq;

  @Before
  public void setUp() throws Exception {
    deq = new TestableDeque<>();
  }

  @Test
  public void testEmptyQueue_AddFirst_RemoveLast() throws Exception {
    deq.addFirst("1");
    assertEquals(deq.head.value, deq.tail.value);
    assertEquals(null, deq.head.next);
    assertEquals(null, deq.head.prev);
    assertEquals(null, deq.tail.next);
    assertEquals(null, deq.tail.prev);
    assertEquals(1, deq.size());

    String actual = deq.removeLast();
    assertEquals("1", actual);
    assertEquals(0, deq.size());

    deq.addFirst("1");
    deq.removeLast();
  }

  @Test
  public void testEmptyQueue_AddFirst_RemoveFirst() throws Exception {
    deq.addFirst("1");
    assertEquals(deq.head.value, deq.tail.value);
    assertEquals(null, deq.head.next);
    assertEquals(null, deq.head.prev);
    assertEquals(null, deq.tail.next);
    assertEquals(null, deq.tail.prev);
    assertEquals(1, deq.size());

    String actual = deq.removeFirst();
    assertEquals("1", actual);
    assertEquals(0, deq.size());

  }

  @Test
  public void testEmptyQueue_AddLast_RemoveFirst() throws Exception {
    deq.addLast("1");
    assertEquals(deq.head.value, deq.tail.value);
    assertEquals(null, deq.head.next);
    assertEquals(null, deq.head.prev);
    assertEquals(null, deq.tail.next);
    assertEquals(null, deq.tail.prev);
    assertEquals(1, deq.size());

    String actual = deq.removeFirst();
    assertEquals("1", actual);
    assertEquals(0, deq.size());
  }

  @Test
  public void testEmptyQueue_AddLast_RemoveLast() throws Exception {
    deq.addLast("1");
    assertEquals(deq.head.value, deq.tail.value);
    assertEquals(null, deq.head.next);
    assertEquals(null, deq.head.prev);
    assertEquals(null, deq.tail.next);
    assertEquals(null, deq.tail.prev);
    assertEquals(1, deq.size());

    String actual = deq.removeLast();
    assertEquals("1", actual);
    assertEquals(0, deq.size());
  }


  @Test(expected = NullPointerException.class)
  public void testAddFirst_null() {
    deq.addFirst(null);
  }

  @Test(expected = NullPointerException.class)
  public void testAddLast_null() {
    deq.addLast(null);
  }

  @Test(expected = NoSuchElementException.class)
  public void testRemoveFirst_empty() {
    deq.removeFirst();
  }

  @Test(expected = NoSuchElementException.class)
  public void testRemoveLast_empty() {
    deq.removeLast();
  }

  @Test
  public void happyPath() throws Exception {
    deq.addFirst("3");
    deq.addFirst("2");
    deq.addLast("4");
    deq.addLast("5");
    deq.addFirst("1");
    deq.addLast("6");

    assertEquals(6, deq.size());

    assertEquals("1", deq.removeFirst());
    assertEquals("6", deq.removeLast());
    assertEquals("2", deq.removeFirst());
    assertEquals("3", deq.removeFirst());
    assertEquals("5", deq.removeLast());
    assertEquals("4", deq.removeFirst());

    assertEquals(0, deq.size());
  }


  @Test
  public void testIterator_hasNext_empty() throws Exception {
    Iterator<String> iterator = deq.iterator();

    assertFalse(iterator.hasNext());
  }

  @Test(expected = NoSuchElementException.class)
  public void testIterator_next_empty() throws Exception {
    deq.iterator().next();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testIterator_remove() throws Exception {
    deq.iterator().remove();
  }

  @Test
  public void iteratorHappyPath() {
    deq.addFirst("3");
    deq.addFirst("2");
    deq.addLast("4");
    deq.addLast("5");
    deq.addFirst("1");
    deq.addLast("6");

    int i = 1;
    for (String actual : deq) {
      assertEquals(Integer.toString(i++), actual);
    }
  }

  public void printState() {
    System.out.println(deq.head.forwardString());
    System.out.println(deq.tail.backwardString());
  }
}