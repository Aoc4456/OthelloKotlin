package com.aoc4456.othellokotlin.board

import androidx.lifecycle.ViewModel
import com.aoc4456.othellokotlin.model.Cell
import com.aoc4456.othellokotlin.model.Stone
import com.aoc4456.othellokotlin.model.Turn
import com.aoc4456.othellokotlin.util.PublishLiveData
import timber.log.Timber

class BoardViewModel() : ViewModel() {

    /* 盤面の状態を２次元リストで表す */
    val cellList: MutableList<MutableList<Cell>> = mutableListOf()

    /** 今どちらの番か */
    private var nowTurn = Turn.BLACK

    /** 盤を更新したいとき */
    private val _updateBoard = PublishLiveData<Boolean>()
    val updateBoard: PublishLiveData<Boolean> = _updateBoard

    /** 先行/後攻を決定 */
    fun decideTheOrder(isBlack:Boolean){
        nowTurn = when(isBlack){
            true -> Turn.BLACK
            else -> Turn.WHITE
        }
        gameStart()
    }

    /** ゲーム開始 */
    fun gameStart() {
        initializeBoard()
        _updateBoard.value = true
    }

    /** 初期配置 */
    private fun initializeBoard() {
        cellList.clear()
        for (i in 0 until BOARD_SIZE) {
            cellList.add(i, mutableListOf())
            for (j in 0 until BOARD_SIZE) {
                cellList[i].add(Cell(i, j, Stone.NONE))
            }
        }
        val upperLeftPosition = BOARD_SIZE / 2 - 1
        cellList[upperLeftPosition][upperLeftPosition].stone = Stone.WHITE
        cellList[upperLeftPosition + 1][upperLeftPosition + 1].stone = Stone.WHITE
        cellList[upperLeftPosition + 1][upperLeftPosition].stone = Stone.BLACK
        cellList[upperLeftPosition][upperLeftPosition + 1].stone = Stone.BLACK
    }

    /* 引数の場所に置いたとき、ひっくり帰るセルの一覧を取得 **/
    private fun getCellsToFlip(cell: Cell): List<Cell> {
        if (cellList[cell.vertical][cell.horizontal].stone != Stone.NONE) {
            Timber.d("既に石が置いてあります : ${cell.vertical} / ${cell.vertical}")
            return emptyList()
        }
        return getCellsToFlip(cell)
    }

    /** 置かれた位置から上方向 */
    private fun getFlipCellUp(placed: Cell): List<Cell> {
        if (placed.vertical == 0) return emptyList()
        val upperList = mutableListOf<Cell>()
        upperList.add(placed)
        for (i in (placed.vertical - 1) downTo 0) { // 置いた場所のひとつ上から盤の一番上まで
            upperList.add(cellList[i][placed.horizontal])
        }
        return getSandwichedStones(upperList)
    }

    private fun getSandwichedStones(list:List<Cell>):List<Cell>{
        // 置いた石
        val ownStone = list.first()
        // 置かれている石
        val otherStones = list.drop(1)
        // 次の自分の石の位置
        val endIndex = otherStones.indexOfFirst { it.stone == ownStone.stone }
        if(endIndex == -1) return emptyList()

        val sandWitched = mutableListOf<Cell>()
        for(i in 0 until endIndex){
            // 途中に空白のセルがあったらだめ
            if(otherStones[i].stone == Stone.NONE) return emptyList()
            sandWitched.add(otherStones[i])
        }
        return sandWitched
    }

    companion object {
        const val BOARD_SIZE = 8
    }
}
