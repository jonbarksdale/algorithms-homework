import io.kotlintest.specs.FlatSpec

class KdTreeTest : FlatSpec() {
  init {
    "an empty tree" should "start empty" {
      val tree = KdTree()
      tree.isEmpty shouldBe true
      tree.size() shouldBe 0
    }

    "insert" should "insert" {

    }

  }
}