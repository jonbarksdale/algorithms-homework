import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertFalse;

public class PercolationTest {

  @Test
  public void testToFlat() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    int n = 4;
    Percolation p = new Percolation(4);

//    for (int i = 0; i < n; i++) {
//      for (int j = 0; j <n; j++) {
//        int flat = (int) toFlat.invoke(p, i, j);
//        int flat = p.toFlat(i, j);

//        System.out.printf("%d, %d (%d, %d): %d%n", i, j, i+1, j+1, flat);
//      }
//    }
  }

  @Test
  public void testIsFull() {
    int n = 4;
    Percolation p = new Percolation(n);

    assertFalse(p.isFull(4, 4));
    p.open(4, 4);

    assertFalse(p.isFull(4, 4));
  }

  @Test
  public void testSingle(){
    Percolation p = new Percolation(1);
    assertFalse(p.percolates());

  }
}
