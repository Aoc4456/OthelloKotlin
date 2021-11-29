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

    override fun getNextPosition(cellList: List<List<Cell>>, turn: Turn): Pair<Int, Int> {
        TODO("Not yet implemented")
    }
}

/**
 * 【参考】オセロ（リバーシ）の作り方（アルゴリズム） ～石の位置による評価～
 *  https://uguisu.skr.jp/othello/5-1.html
 *  より洗練された重み付けの方法
 */
class StrongAI() : AI {
    override val difficulty = Difficulty.STRONG

    override fun getNextPosition(cellList: List<List<Cell>>, turn: Turn): Pair<Int, Int> {
        TODO("Not yet implemented")
    }
}

class Human() : AI {
    override val difficulty = Difficulty.HUMAN

    override fun getNextPosition(cellList: List<List<Cell>>, turn: Turn): Pair<Int, Int> {
        throw IllegalStateException("手動モードでこのメソッドを呼ばないでください")
    }
}
