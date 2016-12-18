import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private final int n;
  private final int[][] state;
  private WeightedQuickUnionUF uf;
  private final int top;
  private final int bottom;

  public Percolation(int n) {
    this.n = n;
    if (n <= 0) {
      throw new IllegalArgumentException();
    }
    state = new int[n][n];
    top = n * n;
    bottom = n * n + 1;
    // plus one for the top (0)
    uf = new WeightedQuickUnionUF(bottom + 1);

    // Add connections for the first row, and the last row
//    for (int i = 0; i < n; i++) {
//      uf.union(top, toFlat(0, i));
//      uf.union(bottom, toFlat(n - 1, i));
//    }
  }                // create n-by-n grid, with all sites blocked

  public void open(int row, int col) {
    checkBounds(row, col);
    int i = row - 1;
    int j = col - 1;

    state[i][j] = 1;

    if (row == n) {
      uf.union(bottom, toFlat(i, j));
    } else if (isOpen(row + 1, col)) {
      uf.union(toFlat(i + 1, j), toFlat(i, j));
    }

    if (row == 1) {
      uf.union(top, toFlat(i, j));
    } else if (isOpen(row - 1, col)) {
      uf.union(toFlat(i - 1, j), toFlat(i, j));
    }

    if (col < n && isOpen(row, col + 1)) {
      uf.union(toFlat(i, j + 1), toFlat(i, j));
    }
    if (col > 1 && isOpen(row, col - 1)) {
      uf.union(toFlat(i, j - 1), toFlat(i, j));
    }
  }       // open site (row, col) if it is not open already

  private void checkBounds(int row, int col) {
    if (row > state.length || row < 1
        || col > state.length || col < 1) {
      throw new IndexOutOfBoundsException("got " + row + ", " + col);
    }
  }

  public boolean isOpen(int row, int col) {
    checkBounds(row, col);
    return state[row - 1][col - 1] == 1;
  }  // is site (row, col) open?

  public boolean isFull(int row, int col) {
    checkBounds(row, col);
    return isOpen(row, col) && uf.connected(top, toFlat(row - 1, col - 1));
  }  // is site (row, col) full?

  public boolean percolates() {
    return uf.connected(top, bottom);
  }             // does the system percolate?

  private int toFlat(int i, int j) {
// 0 is the top, so add one
    return i * state.length + j;
  }
//
//  int resolveRow(int combined) {
//    return (combined - 1) / state.length;
//  }
//
//  int resolveCol(int combined) {
//    return (combined - 1) % state.length;
//  }


  public static void main(String[] args) {

  }  // test client (optional)
}
