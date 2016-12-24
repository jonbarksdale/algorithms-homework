import java.util.*;

public class MyBoard {

  // TODO: idea, move from 2d array to 1d array.  Makes for simpler searching
  final int[][] board;

  // TODO: make a copy of input
  public MyBoard(int[][] blocks) {
    this.board = BoardUtils.cloneBoard(Objects.requireNonNull(blocks));
    if (blocks.length != blocks[0].length) {
      throw new IllegalArgumentException("Require an NxN board");
    }

//    validateInput(blocks);

  }          // construct a board from an n-by-n array of blocks

  private void validateInput(int[][] blocks) {
    TreeSet<Integer> sorted = new TreeSet<>();
    int n = board.length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        sorted.add(blocks[i][j]);
      }
    }

    if (sorted.first() != 0 || sorted.last() != (n * n) - 1) {
      throw new IllegalArgumentException();
    }
  }

  // (where blocks[i][j] = block in row i, column j)
  public int dimension() {
    return board.length;
  }               // board dimension n

  // extract to utils
  public int hamming() {
    final int n = board.length;
    int outOfPlace = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (board[i][j] > 0 && board[i][j] != goalValue(j, i, n)) {
          outOfPlace++;
        }
      }
    }
    return outOfPlace;
  }                  // number of blocks out of place

  private int goalValue(final int x, final int y, final int n) {
    return (y * n + x + 1) % (n * n);
  }

  public int manhattan() {
    int distance = 0;
    // counter intuitively, i is y and j is x
    int n = board.length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int value = board[i][j];
        if (value > 0) {
          value -= 1;

          int homeX = value % n;
          int homeY = value / n;

          distance += Math.abs(homeX - j);
          distance += Math.abs(homeY - i);
        }
      }
    }
    return distance;
  }                // sum of Manhattan distances between blocks and goal

  public boolean isGoal() {
    final int n = board.length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (board[i][j] != goalValue(j, i, n)) {
          return false;
        }
      }
    }
    return true;
  }               // is this board the goal board?


  public MyBoard twin() {
    // TODO: remove the loops, 0 can only be so many places
    int n = board.length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n - 1; j++) {
        if (board[i][j] != 0 && board[i][j + 1] != 0) {
          return new MyBoard(BoardUtils.swap(board, i, j, i, j + 1));
        }
      }
    }

    return null;
  }                   // a board that is obtained by exchanging any pair of blocks

  @Override
  public boolean equals(Object y) {
    if (y == this) {
      return true;
    }
    if (y == null) {
      return false;
    }
    if (this.getClass() != y.getClass()) {
      return false;
    }
    final MyBoard that = (MyBoard) y;
    return Arrays.deepEquals(this.board, that.board);
  }       // does this board equal y?

  public List<MyBoard> neighbors() {
    /*
    alg - find 0, generate new boards
     */
    int i;
    int j = 0;

    int val = -1;
    int n = board.length;
    for (i = 0; i < n; i++) {
      for (j = 0; j < n; j++) {
        val = board[i][j];
        if (val == 0) {
          break;
        }
      }
      if (val == 0) {
        break;
      }
    }

    List<MyBoard> neighbors = new LinkedList<>();

    if (i > 0) {
      neighbors.add(new MyBoard(BoardUtils.swap(board, i, j, i - 1, j)));
    }
    if (i < n - 1) {
      neighbors.add(new MyBoard(BoardUtils.swap(board, i, j, i + 1, j)));
    }
    if (j > 0) {
      neighbors.add(new MyBoard(BoardUtils.swap(board, i, j, i, j - 1)));
    }
    if (j < n - 1) {
      neighbors.add(new MyBoard(BoardUtils.swap(board, i, j, i, j + 1)));
    }

    return neighbors;
  }    // all neighboring boards

  public String toString() {
    StringBuilder s = new StringBuilder();
    int n = board.length;
    s.append(n).append("\n");
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        s.append(String.format("%2d ", board[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
//    return Arrays.deepToString(board);
  }               // string representation of this board (in the output format specified below)


  public static void main(String[] args) {

  } // unit tests (not graded)
}