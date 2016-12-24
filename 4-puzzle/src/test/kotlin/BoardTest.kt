import io.kotlintest.specs.FlatSpec

class BoardTest : FlatSpec() {
  init {
    "constructor" should "explode on null input" {
      shouldThrow<NullPointerException> {
        Board(null)
      }
    }

    "constructor" should "explode on incorrect dimensions" {
      shouldThrow<IllegalArgumentException> {
        Board(Array(3, { IntArray(4, { 0 }) }))
      }
    }

//    "constructor" should "explode on invalid array" {
//      shouldThrow<IllegalArgumentException> {
//        Board(Array(3, { IntArray(3, { 0 }) }))
//      }
//    }

    "dimension" should "return the dim of the board" {
      val board = Board(Array(5, { i ->
        IntArray(5, { j ->
          (i * 5 + j) % (5 * 5)
        })
      }))

      board.dimension() shouldBe 5
    }

    "hamming" should "return the number of out of place numbers" {
      val board = Board(arrayOf(
          intArrayOf(8, 1, 3),
          intArrayOf(4, 0, 2),
          intArrayOf(7, 6, 5)
      ))

      board.hamming() shouldBe 5
    }

    "manhattan" should "return the distances from goal summed" {
      val board = Board(arrayOf(
          intArrayOf(8, 1, 3),
          intArrayOf(4, 0, 2),
          intArrayOf(7, 6, 5)
      ))

      board.manhattan() shouldBe 10
    }

    "isGoal" should "return false if it is not the goal" {
      val board = Board(arrayOf(
          intArrayOf(8, 1, 3),
          intArrayOf(4, 0, 2),
          intArrayOf(7, 6, 5)
      ))

      board.isGoal shouldBe false
    }

    "isGoal" should "return true if it is the goal" {
      val board = Board(arrayOf(
          intArrayOf(1, 2, 3),
          intArrayOf(4, 5, 6),
          intArrayOf(7, 8, 0)
      ))

      board.isGoal shouldBe true
    }

    "twin" should "return a twin of the current board" {
      val board = Board(arrayOf(
          intArrayOf(8, 1, 3),
          intArrayOf(4, 0, 2),
          intArrayOf(7, 6, 5)
      ))

      val twin = board.twin()
      (board == twin) shouldBe false

      levenshtein(board.toString(), twin.toString()) shouldBe 2
    }

    "twin" should "return a twin of the current board when 0 is in the starting position"{
      val board = Board(arrayOf(
          intArrayOf(0, 1, 3),
          intArrayOf(4, 8, 2),
          intArrayOf(7, 6, 5)
      ))

      val twin = board.twin()
      (board == twin) shouldBe false

      levenshtein(board.toString(), twin.toString()) shouldBe 2
    }

    "neighbors" should "return 4 neighbors if 0 is in center" {
      val board = Board(arrayOf(
          intArrayOf(8, 1, 3),
          intArrayOf(4, 0, 2),
          intArrayOf(7, 6, 5)
      ))

      val neighbors = board.neighbors()
      neighbors.count() shouldBe 4
    }

    "neighbors" should "return 2 neighbors if 0 is in a corner" {
      val board = Board(arrayOf(
          intArrayOf(8, 1, 3),
          intArrayOf(4, 5, 2),
          intArrayOf(7, 6, 0)
      ))

      val neighbors = board.neighbors()
      neighbors.count() shouldBe 2

//      neighbors.forEach { it -> println(it) }
    }

    "neighbors" should "return 3 neighbors if 0 is on a side" {
      val board = Board(arrayOf(
          intArrayOf(8, 1, 3),
          intArrayOf(4, 5, 0),
          intArrayOf(7, 6, 2)
      ))

      val neighbors = board.neighbors()
      neighbors.count() shouldBe 3

//      neighbors.forEach { it -> println(it) }
    }


  }

  fun levenshtein(lhs: CharSequence, rhs: CharSequence): Int {
    val lhsLength = lhs.length
    val rhsLength = rhs.length

    var cost = Array(lhsLength) { it }
    var newCost = Array(lhsLength) { 0 }

    for (i in 1..rhsLength - 1) {
      newCost[0] = i

      for (j in 1..lhsLength - 1) {
        val match = if (lhs[j - 1] == rhs[i - 1]) 0 else 1

        val costReplace = cost[j - 1] + match
        val costInsert = cost[j] + 1
        val costDelete = newCost[j - 1] + 1

        newCost[j] = Math.min(Math.min(costInsert, costDelete), costReplace)
      }

      val swap = cost
      cost = newCost
      newCost = swap
    }

    return cost[lhsLength - 1]
  }
}