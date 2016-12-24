import static java.util.stream.Collectors.toList;

public class Board {
  private final MyBoard delegate;
  private int hamming = -1;
  private int manhattan = -1;
  private Boolean isGoal;
  private Iterable<Board> neighbors;
  private Board twin;
  private String stringRep;


  public Board(int[][] blocks) {
    this(new MyBoard(blocks));
  }          // construct a board from an n-by-n array of blocks

  private Board(MyBoard myBoard) {
    this.delegate = myBoard;
  }

  // (where blocks[i][j] = block in row i, column j)
  public int dimension() {
    return delegate.dimension();
  }               // board dimension n

  // extract to utils
  public int hamming() {
    if (this.hamming == -1) {
      this.hamming = delegate.hamming();
    }
    return this.hamming;
  }                  // number of blocks out of place

  public int manhattan() {
    if (this.manhattan == -1) {
      this.manhattan = delegate.manhattan();
    }
    return this.manhattan;
  }                // sum of Manhattan distances between blocks and goal

  public boolean isGoal() {
    if (this.isGoal == null) {
      this.isGoal = delegate.isGoal();
    }
    return this.isGoal;
  }               // is this board the goal board?

  public Board twin() {
    if (twin == null) {
      this.twin = new Board(delegate.twin());
    }
    return this.twin;
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
    final Board that = (Board) y;
    return this.delegate.equals(that.delegate);
  }       // does this board equal y?

  public Iterable<Board> neighbors() {
    if (neighbors == null) {
      this.neighbors = delegate.neighbors().stream().map(Board::new).collect(toList());
    }
    return neighbors;
  }    // all neighboring boards

  public String toString() {
    if (this.stringRep == null) {
      this.stringRep = delegate.toString();
    }
    return this.stringRep;
  }               // string representation of this board (in the output format specified below)


  public static void main(String[] args) {

  } // unit tests (not graded)
}