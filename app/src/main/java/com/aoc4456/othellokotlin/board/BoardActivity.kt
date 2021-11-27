package com.aoc4456.othellokotlin.board

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.aoc4456.othellokotlin.R
import com.aoc4456.othellokotlin.databinding.ActivityBoardBinding

class BoardActivity : AppCompatActivity(), BlackOrWhiteDialogListener {

    private val viewModel: BoardViewModel by viewModels()
    private lateinit var binding: ActivityBoardBinding

    private val cellAdapter = CellAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board)

        binding.recyclerView.let {
            it.layoutManager = GridLayoutManager(this, BoardViewModel.BOARD_SIZE)
            it.adapter = cellAdapter
        }

        viewModel.updateBoard.observe(this){
            updateBoard()
        }

        BlackOrWhiteDialog().show(supportFragmentManager, "BlackOrWhiteDialogFragment")
    }

    override fun onClickButtonInDialog(isBlack: Boolean) {
        viewModel.decideTheOrder(isBlack)
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun updateBoard() {
        cellAdapter.submitList(viewModel.cellList.flatten())
    }
}