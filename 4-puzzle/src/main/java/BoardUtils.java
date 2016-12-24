import java.util.Arrays;

public class BoardUtils {

  public static int hammingDistance(int[][] current, int[][] goal) {
    final int n = current.length;
    int outOfPlace = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (current[i][j] > 0 && current[i][j] != goal[i][j]) {
          outOfPlace++;
        }
      }
    }
    return outOfPlace;
  }

  static int[][] swap(final int[][] board, final int i, final int j, final int x, final int y) {
    final int[][] newState = cloneBoard(board);
    newState[i][j] = board[x][y];
    newState[x][y] = board[i][j];
    return newState;
  }

  static int[][] cloneBoard(int[][] board) {
    final int n = board.length;
    final int[][] newBoard = new int[n][n];
    for (int k = 0; k < n; k++) {
      newBoard[k] = Arrays.copyOf(board[k], n);
    }
    return newBoard;
  }
}
