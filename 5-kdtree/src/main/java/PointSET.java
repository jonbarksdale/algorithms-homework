import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.*;

public class PointSET {
  private final TreeSet<Point2D> backingSet;

  // construct an empty set of points
  public PointSET() {
    this.backingSet = new TreeSet<>();
  }

  // is the set empty?
  public boolean isEmpty() {
    return backingSet.isEmpty();
  }

  // number of points in the set
  public int size() {
    return backingSet.size();
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    backingSet.add(Objects.requireNonNull(p));
  }

  // does the set contain point p?
  public boolean contains(Point2D p) {
    return backingSet.contains(Objects.requireNonNull(p));
  }

  // draw all points to standard draw
  public void draw() {
    StdDraw.setPenColor(Color.BLACK);
    StdDraw.setPenRadius(0.01);
    backingSet.forEach(Point2D::draw);
  }

  // all points that are inside the rectangle
  public Iterable<Point2D> range(RectHV rect) {
    Objects.requireNonNull(rect);

    Set<Point2D> set = new TreeSet<>();
    for (Point2D point2D : backingSet) {
      if (rect.contains(point2D)) {
        set.add(point2D);
      }
    }
    return set;
  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {
    Objects.requireNonNull(p);
    boolean seen = false;
    Point2D best = null;
    Comparator<Point2D> comparator = Comparator.comparing(p::distanceTo);
    for (Point2D point2D : backingSet) {
      if (!seen || comparator.compare(point2D, best) < 0) {
        seen = true;
        best = point2D;
      }
    }
    return seen ? best : null;
  }

  public static void main(String[] args) {

  }
}
