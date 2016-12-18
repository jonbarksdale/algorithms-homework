import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

public class FastCollinearPoints {
  private ArrayList<LineSegment> segments = new ArrayList<>();

  public FastCollinearPoints(Point[] points) {
    Point[] myPoints = Arrays.copyOf(Objects.requireNonNull(points), points.length);
    // may be able to move this into unique check
    Arrays.sort(myPoints);

    uniqueCheck(myPoints);

    if (myPoints.length >= 4) {
      findSegments(myPoints);
    }
  }     // finds all line segments containing 4 or more points

  private void findSegments(Point[] points) {
    // todo: need to filter out segments with matching slopes on the same line.
    // The issue is that we are finding the same line with the subsequent point in line
    for (int i = 0; i < points.length; i++) {
      Point p = points[i];

      Map<Double, List<Point>> map = Arrays.stream(points, i, points.length)
          .collect(groupingBy(p::slopeTo));
      segments = map.values().stream()
          .filter(points1 -> points1.size() >= 3) // 3, because this wont include the origin
          .map(TreeSet::new)// sort via natural order
          .map(treeSet -> new LineSegment(p, treeSet.last()))
          .collect(toCollection(() -> segments));
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
  }       // the number of line segments

  public LineSegment[] segments() {
    return segments.toArray(new LineSegment[0]);
  }                // the line segments
}