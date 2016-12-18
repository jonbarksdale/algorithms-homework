import org.junit.Test;

public class PercolationStatsTest {

  @Test
  public void testStats(){
    PercolationStats stats = new PercolationStats(1, 10);

    System.out.println(stats.mean());
    System.out.println("thresholds");
//    for (int i = 0; i < stats.getThresholds().length; i++) {
//      System.out.println(stats.getThresholds()[i]);
//    }
  }
}
