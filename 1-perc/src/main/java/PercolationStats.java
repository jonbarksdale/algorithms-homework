import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private final double[] thresholds;
  private final int trials;

//  public double[] getThresholds() { return thresholds; }

  public PercolationStats(int n, int trials) {
    if (n < 1 || trials < 1) {
      throw new IllegalArgumentException();
    }
    this.trials = trials;

    thresholds = new double[trials];
    for (int i = 0; i < trials; i++) {
      thresholds[i] = performTrial(n);
    }
  }    // perform trials independent experiments on an n-by-n grid

  private double performTrial(int n) {
    Percolation p = new Percolation(n);
    double openCount = 0;
    while (!p.percolates()) {

      int i = 0;
      int j = 0;
      do {
        i = StdRandom.uniform(n) + 1;
        j = StdRandom.uniform(n) + 1;
//        System.out.println("count: " + openCount + " - " + i + ", " + j + ": " + p.isOpen(i, j));
      } while (p.isOpen(i, j));

      p.open(i, j);
      openCount += 1d;
    }

//    System.out.println(openCount + " / " + n * n + " = " + openCount / (double) (n * n));
    return openCount / (double) (n * n);
  }

  public double mean() {
    return StdStats.mean(thresholds);
  }                          // sample mean of percolation threshold

  public double stddev() {
    return StdStats.stddev(thresholds);
  }                        // sample standard deviation of percolation threshold

  public double confidenceLo() {
    return mean() - 1.96 * stddev() / Math.sqrt(trials);
  }                  // low  endpoint of 95% confidence interval

  public double confidenceHi() {
    return mean() + 1.96 * stddev() / Math.sqrt(trials);
  }                  // high endpoint of 95% confidence interval

  public static void main(String[] args) {
  }    // test client (described below)
}
