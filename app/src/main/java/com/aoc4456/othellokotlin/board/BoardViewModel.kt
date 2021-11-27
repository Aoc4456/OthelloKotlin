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

    /** プレイヤーの色 */
    private var playerColor = Turn.BLACK

    /** 今プレイヤーの番かどうか */
    private val isPlayerTurn = nowTurn == playerColor

    /** 盤を更新したいとき */
    private val _updateBoard = PublishLiveData<Boolean>()
    val updateBoard: PublishLiveData<Boolean> = _updateBoard

    /** 先行/後攻を決定 */
    fun decideTheOrder(isBlack:Boolean){
        playerColor = when(isBlack){
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

    /** プレーの繰り返し部分 */
    private fun playTurn(){
        if(isPlayerTurn){

        }
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

    /** 置ける場所をハイライトする */
    

    companion object {
        const val BOARD_SIZE = 8
    }
}
