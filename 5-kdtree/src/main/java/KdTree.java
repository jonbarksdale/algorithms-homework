import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

public class KdTree {
  private Node root;
  private int size = 0;
  private static final RectHV unitSquare = new RectHV(0, 0, 1, 1);

  // construct an empty set of points
  public KdTree() {
  }

  // is the set empty?
  public boolean isEmpty() {
    return root == null;
  }

  // number of points in the set
  public int size() {
    return size;
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    this.root = insert(root, Objects.requireNonNull(p), true, unitSquare);
  }

  private Node insert(Node node, Point2D point, boolean useX, RectHV rect) {
    if (node == null) {
      this.size++;
      return new Node(point, rect);
    } else {

      if (node.point.equals(point)) {
        return node;
      }

      Comparator<Point2D> comparator = useX ?
          Comparator.comparingDouble(Point2D::x) :
          Comparator.comparingDouble(Point2D::y);

      int cmp = comparator.compare(point, node.point);

      if (cmp < 0) {
        if (useX) {
          RectHV childRect = new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
          node.left = insert(node.left, point, !useX, childRect);
        } else {
          RectHV childRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
          node.left = insert(node.left, point, !useX, childRect);
        }
      } else {
        if (useX) {
          RectHV childRect = new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
          node.right = insert(node.right, point, !useX, childRect);
        } else {
          RectHV childRect = new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
          node.right = insert(node.right, point, !useX, childRect);
        }
      }

      return node;
    }
  }

  // does the set contain point p?
  public boolean contains(Point2D p) {
    return get(root, p, true).isPresent();
  }

  private Optional<Node> get(Node node, Point2D point, boolean useX) {
    if (node == null) {
      return Optional.empty();
    } else {

      if (point.equals(node.point)) {
        return Optional.of(node);
      }

      Comparator<Point2D> comparator = useX ?
          Comparator.comparingDouble(Point2D::x) :
          Comparator.comparingDouble(Point2D::y);

      int cmp = comparator.compare(point, node.point);

      if (cmp < 0) {
        return get(node.left, point, !useX);
      } else {
        return get(node.right, point, !useX);
      }
    }
  }

  // draw all points to standard draw
  public void draw() {
    if (root != null) {
      root.draw(true);
    }
  }

  // all points that are inside the rectangle
  public Iterable<Point2D> range(RectHV rect) {
    return range(root, Objects.requireNonNull(rect));
  }

  private LinkedList<Point2D> range(Node node, RectHV searchRect) {
    LinkedList<Point2D> result = new LinkedList<>();
    if (node == null) {
      return result;
    } else {
      if (searchRect.contains(node.point)) {
        result.add(node.point);
      }
      if (node.left.rect.intersects(searchRect)) {
        result.addAll(range(node.left, searchRect));
      }
      if (node.right.rect.intersects(searchRect)) {
        result.addAll(range(node.right, searchRect));
      }

      return result;
    }
  }


  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {
    /*
    Nearest neighbor search. To find a closest point to a given query point, start at the root and recursively search in both subtrees using the following pruning rule: if the closest point discovered so far is closer than the distance between the query point and the rectangle corresponding to a node, there is no need to explore that node (or its subtrees). That is, a node is searched only if it might contain a point that is closer than the best one found so far. The effectiveness of the pruning rule depends on quickly finding a nearby point. To do this, organize your recursive method so that when there are two possible subtrees to go down, you always choose the subtree that is on the same side of the splitting line as the query point as the first subtree to explore—the closest point found while exploring the first subtree may enable pruning of the second subtree.
     */
    return nearest(root, p, null);
  }

  private Point2D nearest(Node node, Point2D searchPoint, Point2D closest) {
    if (node == null) {
      return closest;
    } else {
      double nodeDist = node.point.distanceSquaredTo(searchPoint);
      double minDist;
      if (closest == null) {
        closest = node.point;
        minDist = nodeDist;
      } else {
        minDist = closest.distanceSquaredTo(searchPoint);
        if (minDist > nodeDist) {
          closest = node.point;
          minDist = nodeDist;
        }
      }

      if (node.left.rect.distanceSquaredTo(searchPoint) < minDist) {
        closest = nearest(node.left, searchPoint, closest);
      }
      if (node.right.rect.distanceSquaredTo(searchPoint) < minDist) {
        closest = nearest(node.right, searchPoint, closest);
      }

      return closest;
    }
  }

  public static void main(String[] args) {
  }

  private static class Node {
    Node(Point2D p, RectHV rect) {
      this.point = p;
      this.rect = rect;
    }

    final Point2D point;
    final RectHV rect;
    Node left;
    Node right;

    public void draw(boolean drawXLine) {
      StdDraw.setPenColor(Color.BLACK);
      StdDraw.setPenRadius(.01);
      point.draw();

      StdDraw.setPenRadius(.005);
      if (drawXLine) {
        StdDraw.setPenColor(Color.RED);
        StdDraw.line(point.x(), 0, point.x(), 1);
      } else {
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.line(0, point.y(), 1, point.y());
      }
      if (left != null) {
        left.draw(!drawXLine);
      }

      if (right != null) {
        right.draw(!drawXLine);
      }
    }
  }
}
