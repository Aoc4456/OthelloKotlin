package com.aoc4456.othellokotlin.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.aoc4456.othellokotlin.R

class BoardActivity : AppCompatActivity(),BlackOrWhiteDialogListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        BlackOrWhiteDialog().show(supportFragmentManager,"BlackOrWhiteDialogFragment")
    }

    override fun onClickButtonInDialog(isBlack: Boolean) {
        Log.d("ダイアログのボタンが押されました","isBlack = $isBlack")
    }
}