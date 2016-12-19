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

    /*
    Alternate algorithm - for each point, subset the array, sort by slope order with a stable sort

    Or just add a remove dup check at the end.
    dup if slope is same, and it contains any of the points. (slope and intercept are same would work too.)
     */

    List<Pair> pairs = new ArrayList<>();

    for (int i = 0; i < points.length; i++) {
      Point p = points[i];

      Map<Double, List<Point>> map = Arrays.stream(points, i, points.length)
          .collect(groupingBy(p::slopeTo));
      map.values().stream()
          .filter(points1 -> points1.size() >= 3) // 3, because this wont include the origin
          .map(TreeSet::new)// sort via natural order
          .map(treeSet -> new Pair(p, treeSet.last()))
          .filter(current -> pairs.stream().noneMatch(existing -> isSameLine(existing, current)))
          .forEach(pairs::add);
    }

    pairs.stream().map(Pair::toLineSegment).forEach(segments::add);
  }

  private boolean isSameLine(final Pair first, final Pair last) {
    return first.slope == last.slope &&
        (first.a.compareTo(last.a) == 0 || first.b.compareTo(last.b) == 0);
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

  private class Pair {
    final Point a;
    final Point b;
    final double slope;

    Pair(Point a, Point b) {
      this.a = a;
      this.b = b;

      this.slope = a.slopeTo(b);
    }

    LineSegment toLineSegment() {
      return new LineSegment(a, b);
    }

  }
}