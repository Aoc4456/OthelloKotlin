package com.aoc4456.othellokotlin.board

import com.aoc4456.othellokotlin.model.Cell
import com.aoc4456.othellokotlin.model.Stone
import com.aoc4456.othellokotlin.model.Turn
import timber.log.Timber
import kotlin.math.min

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
            .plus(getFlipCellsUpperRight(cellList, placed))
            .plus(getFlipCellsRight(cellList, placed))
            .plus(getFlipCellsBottomRight(cellList, placed))
            .plus(getFlipCellsBottom(cellList, placed))
            .plus(getFlipCellsBottomLeft(cellList, placed))
            .plus(getFlipCellsLeft(cellList, placed))
            .plus(getFlipCellsUpperLeft(cellList, placed))
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

    /** 右上方向 */
    private fun getFlipCellsUpperRight(cellList: List<List<Cell>>, placed: Cell): List<Cell> {
        if (placed.vertical == 0) return emptyList()
        if (placed.horizontal == BOARD_SIZE - 1) return emptyList()
        val upperRightList = mutableListOf<Cell>()
        upperRightList.add(placed)

        val distanceVertical = placed.vertical // 上端までの距離
        val distanceHorizontal = BOARD_SIZE - (placed.horizontal + 1) // 右端までの距離
        val distanceToEdge = min(distanceVertical, distanceHorizontal)

        for (i in 1..distanceToEdge) { // 置いた場所のひとつ右上から、右上方向に行けるまで
            upperRightList.add(cellList[placed.vertical - i][placed.horizontal + i])
        }
        return getSandwichedStones(upperRightList)
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

    /** 右下方向 */
    private fun getFlipCellsBottomRight(cellList: List<List<Cell>>, placed: Cell): List<Cell> {
        if (placed.vertical == BOARD_SIZE - 1) return emptyList()
        if (placed.horizontal == BOARD_SIZE - 1) return emptyList()
        val bottomRightList = mutableListOf<Cell>()
        bottomRightList.add(placed)

        val distanceVertical = BOARD_SIZE - (placed.vertical + 1) // 下端までの距離
        val distanceHorizontal = BOARD_SIZE - (placed.horizontal + 1) // 右端までの距離
        val distanceToEdge = min(distanceVertical, distanceHorizontal)

        for (i in 1..distanceToEdge) { // 置いた場所のひとつ右下から、右下方向に行けるまで
            bottomRightList.add(cellList[placed.vertical + i][placed.horizontal + i])
        }
        return getSandwichedStones(bottomRightList)
    }

    /** 下方向 */
    private fun getFlipCellsBottom(cellList: List<List<Cell>>, placed: Cell): List<Cell> {
        if (placed.vertical == BOARD_SIZE - 1) return emptyList()
        val downList = mutableListOf<Cell>()
        downList.add(placed)
        for (i in (placed.vertical + 1) until BOARD_SIZE) { // 置いた場所のひとつ下から盤の一番下まで
            downList.add(cellList[i][placed.horizontal])
        }
        return getSandwichedStones(downList)
    }

    /** 左下方向 */
    private fun getFlipCellsBottomLeft(cellList: List<List<Cell>>, placed: Cell): List<Cell> {
        if (placed.vertical == BOARD_SIZE - 1) return emptyList()
        if (placed.horizontal == 0) return emptyList()
        val bottomLeftList = mutableListOf<Cell>()
        bottomLeftList.add(placed)

        val distanceVertical = BOARD_SIZE - (placed.vertical + 1) // 下端までの距離
        val distanceHorizontal = placed.horizontal // 左端までの距離
        val distanceToEdge = min(distanceVertical, distanceHorizontal)

        for (i in 1..distanceToEdge) { // 置いた場所のひとつ左下から、左下方向に行けるまで
            bottomLeftList.add(cellList[placed.vertical + i][placed.horizontal - i])
        }
        return getSandwichedStones(bottomLeftList)
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

    /** 左上方向 */
    private fun getFlipCellsUpperLeft(cellList: List<List<Cell>>, placed: Cell): List<Cell> {
        if (placed.vertical == 0) return emptyList()
        if (placed.horizontal == 0) return emptyList()
        val upperLeftList = mutableListOf<Cell>()
        upperLeftList.add(placed)

        val distanceVertical = placed.vertical // 上端までの距離
        val distanceHorizontal = placed.horizontal // 左端までの距離
        val distanceToEdge = min(distanceVertical, distanceHorizontal)

        for (i in 1..distanceToEdge) { // 置いた場所のひとつ左上から、左上方向に行けるまで
            upperLeftList.add(cellList[placed.vertical - i][placed.horizontal - i])
        }
        return getSandwichedStones(upperLeftList)
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