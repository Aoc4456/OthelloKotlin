package com.aoc4456.othellokotlin.ai

import com.aoc4456.othellokotlin.board.FlipOverUtils
import com.aoc4456.othellokotlin.model.Cell
import com.aoc4456.othellokotlin.model.Difficulty
import com.aoc4456.othellokotlin.model.Turn

interface AI {
    val difficulty: Difficulty

    /** CPUが次に置くポジションを得る */
    fun getNextPosition(cellList: List<List<Cell>>, turn: Turn): Pair<Int, Int>
}

fun getAIFromDifficulty(difficulty: Difficulty): AI {
    return when (difficulty) {
        Difficulty.WEAK -> WeakAI()
        Difficulty.NORMAL -> NormalAI()
        Difficulty.STRONG -> StrongAI()
        else -> return Human()
    }
}

/**
 * 置ける場所のうち、ランダムな位置を返す
 */
class WeakAI() : AI {
    override val difficulty = Difficulty.WEAK

    override fun getNextPosition(cellList: List<List<Cell>>, turn: Turn): Pair<Int, Int> {
        val allCellsCanPut = FlipOverUtils.getAllCellsCanPut(cellList, turn)
        val randomCell = allCellsCanPut.random()
        return Pair(randomCell.vertical, randomCell.horizontal)
    }
}

/**
 * 【参考】オセロ（リバーシ）の作り方（アルゴリズム） ～石の位置による評価～
 *  https://uguisu.skr.jp/othello/5-1.html
 *  重み付けの方法
 */
class NormalAI() : AI {
    override val difficulty = Difficulty.NORMAL

    private val normalEvaluationBoard = arrayOf(
        arrayOf(120, -20, 20, 5, 5, 20, -20, 120),
        arrayOf(-20, -40, -5, -5, -5, -5, -40, 20),
        arrayOf(20, -5, 15, 3, 3, 15, -5, 20),
        arrayOf(5, -5, 3, 3, 3, 3, -5, 5),
        arrayOf(5, -5, 3, 3, 3, 3, -5, 5),
        arrayOf(20, -5, 15, 3, 3, 15, -5, 20),
        arrayOf(-20, -40, -5, -5, -5, -5, -40, 20),
        arrayOf(120, -20, 20, 5, 5, 20, -20, 120)
    )

    override fun getNextPosition(cellList: List<List<Cell>>, turn: Turn): Pair<Int, Int> {
        val cell = getNextPositionFromEvaluationBoard(cellList, turn, normalEvaluationBoard)
        return Pair(cell.vertical, cell.horizontal)
    }
}

/**
 * 【参考】オセロ（リバーシ）の作り方（アルゴリズム） ～石の位置による評価～
 *  https://uguisu.skr.jp/othello/5-1.html
 *  より洗練された重み付けの方法
 */
class StrongAI() : AI {
    override val difficulty = Difficulty.STRONG

    private val strongEvaluationBoard = arrayOf(
        arrayOf(30, -12, 0, -1, -1, 0, -12, 30),
        arrayOf(-12, -15, -3, -3, -3, -3, -15, -12),
        arrayOf(0, -3, 0, -1, -1, 0, -3, 0),
        arrayOf(-1, -3, -1, -1, -1, -1, -3, -1),
        arrayOf(-1, -3, -1, -1, -1, -1, -3, -1),
        arrayOf(0, -3, 0, -1, -1, 0, -3, 0),
        arrayOf(-12, -15, -3, -3, -3, -3, -15, -12),
        arrayOf(30, -12, 0, -1, -1, 0, -12, 30)
    )

    override fun getNextPosition(cellList: List<List<Cell>>, turn: Turn): Pair<Int, Int> {
        val cell = getNextPositionFromEvaluationBoard(cellList, turn, strongEvaluationBoard)
        return Pair(cell.vertical, cell.horizontal)
    }
}

class Human() : AI {
    override val difficulty = Difficulty.HUMAN

    override fun getNextPosition(cellList: List<List<Cell>>, turn: Turn): Pair<Int, Int> {
        throw IllegalStateException("手動モードでこのメソッドを呼ばないでください")
    }
}

/**
 * 重み付けされた表から、一番評価値の合計が高くなるマスを返す
 */
private fun getNextPositionFromEvaluationBoard(
    cellList: List<List<Cell>>,
    turn: Turn,
    evaluationBoard: Array<Array<Int>>
): Cell {
    val allCellsCanPut = FlipOverUtils.getAllCellsCanPut(cellList, turn)

    val totalScorePairList = mutableListOf<Pair<Cell, Int>>() // 打つ場所と、そこに打った時の評価値をペアにしていく
    allCellsCanPut.forEach { placed ->
        val placedAndFlipOvers = FlipOverUtils.getCellsToFlip(cellList, placed).plus(placed)
        val totalScore =
            placedAndFlipOvers.map { evaluationBoard[it.vertical][it.horizontal] }.sum()
        totalScorePairList.add(Pair(placed, totalScore))
    }
    return totalScorePairList.maxByOrNull { it.second }!!.first
}
