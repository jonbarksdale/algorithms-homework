import BoardUtils.*
import io.kotlintest.specs.FlatSpec

class SolverTest : FlatSpec() {
  init {
    "solver" should "solve 2x2" {
      val raw = arrayOf(
          intArrayOf(1, 0),
          intArrayOf(3, 2)
      )
      val board = Board(raw)

      val solver = Solver(board)
      solver.moves() shouldBe 1
      solver.isSolvable shouldBe true

      val iterator = solver.solution().iterator()
      iterator.next() shouldBe board
      iterator.next() shouldBe Board(swap(raw, 0, 1, 1, 1))
    }

    "solver" should "solve show not solvable when not solvable" {
      val board = Board(arrayOf(
          intArrayOf(1, 0),
          intArrayOf(2, 3)
      ))

      val solver = Solver(board)
      solver.moves() shouldBe -1
      solver.isSolvable shouldBe false
      solver.solution() shouldBe null
    }

  }
}