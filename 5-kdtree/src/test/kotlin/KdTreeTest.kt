import edu.princeton.cs.algs4.Point2D
import io.kotlintest.specs.FlatSpec

class KdTreeTest : FlatSpec() {
  init {
    "an empty tree" should "start empty" {
      val tree = KdTree()
      tree.isEmpty shouldBe true
      tree.size() shouldBe 0
    }

    "a single node tree" should "not be empty, and should be size 1" {
      val tree = KdTree()
      val p = Point2D(0.1, .4)
      tree.insert(p)

      tree.isEmpty shouldBe false
      tree.size() shouldBe 1
      tree.contains(p) shouldBe true
    }

    "duplicate points" should "be ignored" {
      val tree = KdTree()
      val p0 = Point2D(.1, .2)
      tree.insert(p0)
      val p = Point2D(0.1, .4)
      tree.insert(p)

      tree.isEmpty shouldBe false
      tree.size() shouldBe 2
      tree.contains(p) shouldBe true

      val dup = Point2D(0.1, .4)
      tree.insert(dup)

      tree.isEmpty shouldBe false
      tree.size() shouldBe 2
      tree.contains(dup) shouldBe true

    }

    "boundary" should "accept 1,1 as a point"{
      val tree = KdTree()
      val p = Point2D(1.0, 1.0)
      tree.insert(p)

      tree.isEmpty shouldBe false
      tree.size() shouldBe 1
      tree.contains(Point2D(1.0,1.0)) shouldBe true
    }


  }
}