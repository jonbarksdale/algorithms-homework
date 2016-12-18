import java.util.*;

/*
Brute force. Write a program BruteCollinearPoints.java that examines 4 points at a time and checks whether they all lie on the same line segment, returning all such line segments. To check whether the 4 points p, q, r, and s are collinear, check whether the three slopes between p and q, between p and r, and between p and s are all equal.

public class BruteCollinearPoints {
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   public           int numberOfSegments()        // the number of line segments
   public LineSegment[] segments()                // the line segments
}
The method segments() should include each line segment containing 4 points exactly once. If 4 points appear on a line segment in the order p→q→r→s, then you should include either the line segment p→s or s→p (but not both) and you should not include subsegments such as p→r or q→r. For simplicity, we will not supply any input to BruteCollinearPoints that has 5 or more collinear points.

Corner cases. Throw a java.lang.NullPointerException either the argument to the constructor is null or if any point in the array is null. Throw a java.lang.IllegalArgumentException if the argument to the constructor contains a repeated point.

Performance requirement. The order of growth of the running time of your program should be n4 in the worst case and it should use space proportional to n plus the number of line segments returned.
 */
public class BruteCollinearPoints {
  private List<LineSegment> segments = new ArrayList<>();


  public BruteCollinearPoints(Point[] points) {
    Point[] myPoints = Arrays.copyOf(Objects.requireNonNull(points), points.length);
    // may be able to move this into unique check
    Arrays.sort(myPoints);

    uniqueCheck(myPoints);

    if (myPoints.length >= 4) {
      findSegments(myPoints);
    }
  }    // finds all line segments containing 4 points

  private void findSegments(Point[] points) {
    for (int i = 0; i < points.length - 3; i++) {
      final Point p = points[i];
      for (int j = i + 1; j < points.length - 2; j++) {
        final Point q = points[j];
        final double slope = p.slopeTo(q);
        for (int k = j + 1; k < points.length - 1; k++) {
          final Point r = points[k];
          if (slope == p.slopeTo(r)) {
            for (int l = k + 1; l < points.length; l++) {
              final Point s = points[l];
              if (slope == p.slopeTo(s)) {
                segments.add(new LineSegment(p, s));
              }
            }
          }
        }
      }
    }
  }

  /**
   * Throws IllegalArgumentException if there are any duplicate points, ~ linear time.
   *
   * @param points sorted array of points
   */
  private void uniqueCheck(Point[] points) {
    for (int i = 0; i < points.length - 1; i++) {
      if (points[i].compareTo(points[i + 1]) == 0) {
        throw new IllegalArgumentException();
      }
    }
  }

  public int numberOfSegments() {
    return segments.size();
  }        // the number of line segments

  public LineSegment[] segments() {
    return segments.toArray(new LineSegment[0]);
  }                // the line segments
}