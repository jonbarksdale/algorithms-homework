import io.kotlintest.specs.FlatSpec

class FastCollinearPointsTest : FlatSpec() {
  init {

    "constructor" should "throw null pointer on null points" {
      shouldThrow<NullPointerException> {
        FastCollinearPoints(null)
      }
    }

    "constructor" should "throw null pointer if array contains nulls" {
      shouldThrow<NullPointerException> {
        FastCollinearPoints(arrayOf(Point(1, 1), null))
      }
    }

    "constructor" should "throw illegal argument if duplicate points in input" {
      shouldThrow<IllegalArgumentException> {
        FastCollinearPoints(arrayOf(Point(1, 1), Point(0, 0), Point(1, 1)))
      }
    }

    "constructor" should "work on empty input" {
      val fast = FastCollinearPoints(arrayOf())
      fast.numberOfSegments() shouldEqual 0
      fast.segments().size shouldBe 0
    }

    "constructor" should "work on inputs smaller that 4 points" {
      val fast = FastCollinearPoints(arrayOf(Point(1, 1), Point(1, 3)))
      fast.numberOfSegments() shouldEqual 0
      fast.segments().size shouldBe 0
    }

    "constructor" should "find a line segment of length 4" {
      val p1 = Point(1, 1)
      val p2 = Point(2, 2)
      val p3 = Point(3, 3)
      val p4 = Point(4, 4)

      // bullshit point
      val p5 = Point(4, 12)

      val fast = FastCollinearPoints(arrayOf(p5, p2, p1, p4, p3))

      fast.numberOfSegments() shouldBe 1
      val seg = fast.segments()[0]
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

      val fast = FastCollinearPoints(inputs)

      fast.numberOfSegments() shouldBe 2
      val seg1 = fast.segments()[0]
      seg1.toString() shouldBe "$p1 -> $p4"
      val seg2 = fast.segments()[1]
      seg2.toString() shouldBe "$p6 -> $p7"
    }

    "constructor" should "not include subsegments when total segment is more than 4 points" {
      val p1 = Point(1, 1)
      val p2 = Point(2, 2)
      val p3 = Point(3, 3)
      val p4 = Point(4, 4)
      val p5 = Point(5, 5)

      // bullshit point
      val p6 = Point(4, 12)

      val fast = FastCollinearPoints(arrayOf(p5, p6, p2, p1, p4, p3))

      fast.numberOfSegments() shouldBe 1
      val seg = fast.segments()[0]
      seg.toString() shouldBe "$p1 -> $p5"
    }

  }
}