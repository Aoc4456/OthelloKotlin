package com.aoc4456.othellokotlin.ai

import com.aoc4456.othellokotlin.board.FlipOverUtils
import com.aoc4456.othellokotlin.model.Cell
import com.aoc4456.othellokotlin.model.Difficulty
import com.aoc4456.othellokotlin.model.Turn

class AI(private val difficulty: Difficulty) {

    /** CPUが石を置く */
    fun getNextPosition(
        cellList: List<List<Cell>>,
        turn: Turn
    ): Pair<Int, Int> {
        return when (difficulty) {
            Difficulty.WEAK -> {
                // 全ての置ける場所から、ランダムな位置を返す
                val allCellsCanPut = FlipOverUtils.getAllCellsCanPut(cellList, turn)
                val randomCell = allCellsCanPut.random()
                Pair(randomCell.vertical, randomCell.horizontal)
            }
            else -> {
                // TODO 「よわい」以外の難易度も実装する
                val allCellsCanPut = FlipOverUtils.getAllCellsCanPut(cellList, turn)
                val randomCell = allCellsCanPut.random()
                Pair(randomCell.vertical, randomCell.horizontal)
            }
        }
    }
}