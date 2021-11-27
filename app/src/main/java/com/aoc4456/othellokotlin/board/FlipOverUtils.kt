package com.aoc4456.othellokotlin.board

import com.aoc4456.othellokotlin.model.Cell
import com.aoc4456.othellokotlin.model.Stone
import com.aoc4456.othellokotlin.model.Turn
import timber.log.Timber

/**
 * 石をひっくり返せるかどうかの判断に関する関数たち
 */
object FlipOverUtils {

    /** 次に置ける全てのセルを取得 */
    fun getAllCellsCanPut(cellList: List<List<Cell>>, turn: Turn): List<Cell> {
        val color = when (turn) {
            Turn.BLACK -> Stone.BLACK
            Turn.WHITE -> Stone.WHITE
        }

        val canPutCells = mutableListOf<Cell>()
        cellList.flatten().forEach {
            if (it.stone == Stone.NONE) {
                val nextCell = Cell(it.vertical, it.horizontal, color)
                if (getCellsToFlip(cellList, nextCell).isNotEmpty()) {
                    canPutCells.add(nextCell)
                }
            }
        }
        return canPutCells
    }

    /* 引数の場所に置いたとき、ひっくり帰るセルの一覧を取得 **/
    fun getCellsToFlip(cellList: List<List<Cell>>, placed: Cell): List<Cell> {
        if (cellList[placed.vertical][placed.horizontal].stone != Stone.NONE) {
            Timber.d("既に石が置いてあります : ${placed.vertical} / ${placed.vertical}")
            return emptyList()
        }
        return getFlipCellsUp(cellList, placed)
    }

    /** 置かれた位置から上方向 */
    private fun getFlipCellsUp(cellList: List<List<Cell>>, placed: Cell): List<Cell> {
        if (placed.vertical == 0) return emptyList()
        val upperList = mutableListOf<Cell>()
        upperList.add(placed)
        for (i in (placed.vertical - 1) downTo 0) { // 置いた場所のひとつ上から盤の一番上まで
            upperList.add(cellList[i][placed.horizontal])
        }
        return getSandwichedStones(upperList)
    }

    /** 右方向 */
    private fun getFlipCellsRight(cellList: List<List<Cell>>, placed: Cell): List<Cell> {
        if (placed.horizontal == BOARD_SIZE - 1) return emptyList()
        val rightList = mutableListOf<Cell>()
        rightList.add(placed)
        for (i in (placed.horizontal + 1) until BOARD_SIZE) { // 置いた場所のひとつ右から盤の一番右まで
            rightList.add(cellList[placed.vertical][i])
        }
        return getSandwichedStones(rightList)
    }

    /** 下方向 */
    private fun getFlipCellsDown(cellList: List<List<Cell>>, placed: Cell): List<Cell> {
        if (placed.vertical == BOARD_SIZE - 1) return emptyList()
        val downList = mutableListOf<Cell>()
        downList.add(placed)
        for (i in (placed.vertical + 1) until BOARD_SIZE) { // 置いた場所のひとつ下から盤の一番下まで
            downList.add(cellList[i][placed.horizontal])
        }
        return getSandwichedStones(downList)
    }

    /** 左方向 */
    private fun getFlipCellsLeft(cellList: List<List<Cell>>, placed: Cell): List<Cell> {
        if (placed.horizontal == 0) return emptyList()
        val leftList = mutableListOf<Cell>()
        leftList.add(placed)
        for (i in (placed.horizontal - 1) downTo 0) { // 置いた場所のひとつ左から盤の一番左まで
            leftList.add(cellList[placed.vertical][i])
        }
        return getSandwichedStones(leftList)
    }

    private fun getSandwichedStones(list: List<Cell>): List<Cell> {
        // 置いた石
        val ownStone = list.first()
        // 置かれている石
        val otherStones = list.drop(1)
        // 次の自分の石の位置
        val endIndex = otherStones.indexOfFirst { it.stone == ownStone.stone }
        if (endIndex == -1) return emptyList()

        val sandWitched = mutableListOf<Cell>()
        for (i in 0 until endIndex) {
            // 途中に空白のセルがあったらだめ
            if (otherStones[i].stone == Stone.NONE) return emptyList()
            sandWitched.add(otherStones[i])
        }
        return sandWitched
    }
}