import io.kotlintest.specs.FlatSpec

class BruteCollinearPointsTest : FlatSpec() {
  init {

    "constructor" should "throw null pointer on null points" {
      shouldThrow<NullPointerException> {
        BruteCollinearPoints(null)
      }
    }

    "constructor" should "throw null pointer if array contains nulls" {
      shouldThrow<NullPointerException> {
        BruteCollinearPoints(arrayOf(Point(1, 1), null))
      }
    }

    "constructor" should "throw illegal argument if duplicate points in input" {
      shouldThrow<IllegalArgumentException> {
        BruteCollinearPoints(arrayOf(Point(1, 1), Point(0, 0), Point(1, 1)))
      }
    }

    "constructor" should "work on empty input" {
      val brute = BruteCollinearPoints(arrayOf())
      brute.numberOfSegments() shouldEqual 0
      brute.segments().size shouldBe 0
    }

    "constructor" should "work on inputs smaller that 4 points" {
      val brute = BruteCollinearPoints(arrayOf(Point(1, 1), Point(1, 3)))
      brute.numberOfSegments() shouldEqual 0
      brute.segments().size shouldBe 0
    }

    "constructor" should "find a line segment of length 4" {
      val p1 = Point(1, 1)
      val p2 = Point(2, 2)
      val p3 = Point(3, 3)
      val p4 = Point(4, 4)

      // bullshit point
      val p5 = Point(4, 12)

      val brute = BruteCollinearPoints(arrayOf(p5, p2, p1, p4, p3))

      brute.numberOfSegments() shouldBe 1
      val seg = brute.segments()[0]
      seg.toString() shouldBe "$p1 -> $p4"
    }

    "constructor" should "find multiple line segments " {
      val p1 = Point(1, 1)
      val p2 = Point(2, 2)
      val p3 = Point(3, 3)
      val p4 = Point(4, 4)

      val p5 = Point(4, 2)
      val p6 = Point(5, 1)
      val p7 = Point(2, 4)

      val inputs = arrayOf(p2, p6, p1, p7, p4, p3, p5)

      val brute = BruteCollinearPoints(inputs)

      brute.numberOfSegments() shouldBe 2
      val seg1 = brute.segments()[0]
      seg1.toString() shouldBe "$p1 -> $p4"
      val seg2 = brute.segments()[1]
      seg2.toString() shouldBe "$p6 -> $p7"
    }

  }
}