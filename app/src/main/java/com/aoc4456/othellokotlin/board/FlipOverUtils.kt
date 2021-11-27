package com.aoc4456.othellokotlin.board

import com.aoc4456.othellokotlin.model.Cell
import com.aoc4456.othellokotlin.model.Stone
import timber.log.Timber

/**
 * 石をひっくり返せるかどうかの判断に関する関数たち
 */
object FlipOverUtils {

    /* 引数の場所に置いたとき、ひっくり帰るセルの一覧を取得 **/
    fun getCellsToFlip(cellList: List<List<Cell>>, placed: Cell): List<Cell> {
        if (cellList[placed.vertical][placed.horizontal].stone != Stone.NONE) {
            Timber.d("既に石が置いてあります : ${placed.vertical} / ${placed.vertical}")
            return emptyList()
        }
        return getFlipCellUp(cellList, placed)
    }

    /** 置かれた位置から上方向 */
    private fun getFlipCellUp(cellList: List<List<Cell>>, placed: Cell): List<Cell> {
        if (placed.vertical == 0) return emptyList()
        val upperList = mutableListOf<Cell>()
        upperList.add(placed)
        for (i in (placed.vertical - 1) downTo 0) { // 置いた場所のひとつ上から盤の一番上まで
            upperList.add(cellList[i][placed.horizontal])
        }
        return getSandwichedStones(upperList)
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