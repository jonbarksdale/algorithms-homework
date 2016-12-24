import edu.princeton.cs.algs4.*;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Solver {

  private static final Comparator<Step> comparator = Comparator.comparing(step -> step.priority);

  private final boolean solvable;
  private final int moves;
  private final Stack<Board> solution;

  public Solver(Board initial) {
    Objects.requireNonNull(initial);

    final Board twinInitial = initial.twin();

    final Set<String> checkedBoards = new HashSet<>();
    final Set<String> checkedTwins = new HashSet<>();


    final MinPQ<Step> pq = new MinPQ<>(comparator);
    final MinPQ<Step> twinPq = new MinPQ<>(comparator);

    pq.insert(new Step(initial, 0, null));
    twinPq.insert(new Step(twinInitial, 0, null));
    checkedBoards.add(initial.toString());
    checkedTwins.add(twinInitial.toString());

    Step current = null;
    Step twinCurrent = null;
    while (current == null
        || ((!current.goal && !pq.isEmpty())
        && (!twinCurrent.goal && !twinPq.isEmpty()))
        ) {

      current = nextStep(pq, checkedBoards);
      twinCurrent = nextStep(twinPq, checkedTwins);
    }
    twinCurrent = null;

    if (current.goal) {
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

    current = null;
  }           // find a solution to the initial board (using the A* algorithm)

  private Step nextStep(MinPQ<Step> pq, Set<String> checked) {
    Step current = pq.delMin();
    Iterable<Board> neighbors = current.board.neighbors();
    for (Board board : neighbors) {
      if (!checked.contains(board.toString())) {
        pq.insert(new Step(board, current.moves + 1, current));
        checked.add(board.toString());
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
    final int priority;

    final boolean goal;

    Step(Board board, int moves, Step previous) {
      this.board = board;
      this.moves = moves;
      this.previous = previous;
      this.goal = board.isGoal();
      this.priority = board.manhattan() + moves;
    }


  }
}
