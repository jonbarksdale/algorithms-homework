import edu.princeton.cs.algs4.*;

import java.util.Comparator;
import java.util.Objects;

public class Solver {

  private static final Comparator<Step> comparator = Comparator.comparing(step -> step.board.manhattan() + step.moves);

  private final boolean solvable;
  private final int moves;
  private final Stack<Board> solution;

  public Solver(Board initial) {
    Objects.requireNonNull(initial);

    final Board twinInitial = initial.twin();

    final MinPQ<Step> pq = new MinPQ<>(comparator);
    final MinPQ<Step> twinPq = new MinPQ<>(comparator);

    pq.insert(new Step(initial, 0, null));
    twinPq.insert(new Step(twinInitial, 0, null));

    Step current = null;
    Step twinCurrent = null;
    while (current == null
        || ((!current.board.isGoal() && !pq.isEmpty())
        && (!twinCurrent.board.isGoal() && !twinPq.isEmpty()))
        ) {

      current = nextStep(pq, current);
      twinCurrent = nextStep(twinPq, twinCurrent);
    }

    if (current.board.isGoal()) {
      this.solvable = true;
      this.moves = current.moves;
      this.solution = new Stack<>();

      while (current != null) {
        solution.push(current.board);
        current = current.previous;
      }

    } else {
      this.solvable = false;
      this.moves = -1;
      this.solution = null;
    }

  }           // find a solution to the initial board (using the A* algorithm)

  private Step nextStep(MinPQ<Step> pq, Step current) {
    Step prev = current;
    current = pq.delMin();
    Iterable<Board> neighbors = current.board.neighbors();
    for (Board board : neighbors) {
      if (prev == null || !board.equals(prev.board)) {
        pq.insert(new Step(board, current.moves + 1, current));
      }
    }
    return current;
  }

  public boolean isSolvable() {
    return solvable;
  }           // is the initial board solvable?

  public int moves() {
    return moves;
  }                    // min number of moves to solve initial board; -1 if unsolvable

  public Iterable<Board> solution() {
    // helper method for stepping
    return solution;
  }     // sequence of boards in a shortest solution; null if unsolvable

  public static void main(String[] args) {
  }// solve a slider puzzle (given below)

  private class Step {
    final Board board;
    final int moves;
    final Step previous;

    Step(Board board, int moves, Step previous) {
      this.board = board;
      this.moves = moves;
      this.previous = previous;
    }
  }
}
