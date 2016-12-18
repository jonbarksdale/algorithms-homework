import io.kotlintest.specs.FlatSpec

class PointTest : FlatSpec() {
  init {

    "compareTo" should "return 0 when equal" {
      val p1 = Point(1, 2)
      val p2 = Point(1, 2)

      p1.compareTo(p2) shouldBe 0
    }

    "compareTo" should "return 1 when greater than the argument" {
      val p1 = Point(2, 2)
      val p2 = Point(2, 1)

      p1.compareTo(p2) shouldBe 1
    }

    "compareTo" should "return 1 when greater than the argument, using x as tie breaker" {
      val p1 = Point(2, 2)
      val p2 = Point(1, 2)

      p1.compareTo(p2) shouldBe 1
    }


    "compareTo" should "return -1 when less than the argument" {
      val p1 = Point(2, 1)
      val p2 = Point(2, 2)

      p1.compareTo(p2) shouldBe -1
    }


    "compareTo" should "return -1 when less than the argument" {
      val p1 = Point(1, 1)
      val p2 = Point(2, 1)

      p1.compareTo(p2) shouldBe -1
    }

    "slopeTo" should "be +0.0 if the line is horizontal" {
      val p1 = Point(1, 1)
      val p2 = Point(2, 1)

      p1.slopeTo(p2) shouldBe exactly(0.0)
      p2.slopeTo(p1) shouldBe exactly(0.0)
    }

    "slopeTo" should "be positive infinity if the line segment is vertical" {
      val p1 = Point(1, 1)
      val p2 = Point(1, 2)

      p1.slopeTo(p2) shouldBe exactly(Double.POSITIVE_INFINITY)
      p2.slopeTo(p1) shouldBe exactly(Double.POSITIVE_INFINITY)
    }

    "slopeTo" should "be negative infinity if the points are the same" {
      val p1 = Point(1, 1)
      val p2 = Point(1, 1)

      p1.slopeTo(p2) shouldBe exactly(Double.NEGATIVE_INFINITY)
    }

    "slopeTo" should "be delta y over delta x" {
      val p1 = Point(0, 0)
      val p2 = Point(1, 1)
      val p3 = Point(4, 2)

      p1.slopeTo(p2) shouldBe exactly(1.0)
      p2.slopeTo(p1) shouldBe exactly(1.0)

      p1.slopeTo(p3) shouldBe exactly(.5)
      p3.slopeTo(p1) shouldBe exactly(.5)
    }

    "slopeOrder" should "produce a comparator that sorts by slope between a point and the creator" {
      val p0 = Point(0, 0)
      val comp = p0.slopeOrder()

      val p1 = Point(3, 1)
      val p2 = Point(5, 2)
      val p3 = Point(4, 4)
      val p4 = Point(1, 7)

      val points = arrayOf(p2, p1, p4, p3)

      points.sortWith(comp)

      var i = 0
      points[i++] shouldBe p1
      points[i++] shouldBe p2
      points[i++] shouldBe p3
      points[i++] shouldBe p4
    }

  }
}