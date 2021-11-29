package com.aoc4456.othellokotlin.board

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.aoc4456.othellokotlin.R
import com.aoc4456.othellokotlin.databinding.ActivityBoardBinding
import com.aoc4456.othellokotlin.model.Difficulty
import com.aoc4456.othellokotlin.model.KEY_INTENT_DIFFICULTY

class BoardActivity : AppCompatActivity(), BlackOrWhiteDialogListener {

    private val viewModel: BoardViewModel by viewModels()
    private lateinit var binding: ActivityBoardBinding
    private lateinit var cellAdapter: CellAdapter
    private lateinit var difficulty: Difficulty

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board)

        difficulty = intent.extras!!.get(KEY_INTENT_DIFFICULTY) as Difficulty
        viewModel.setDifficulty(difficulty)

        cellAdapter = CellAdapter(viewModel)

        binding.recyclerView.let {
            it.layoutManager = GridLayoutManager(this, BOARD_SIZE)
            it.adapter = cellAdapter
        }

        viewModel.turnMessage.observe(this) {
            binding.message.text = it
        }

        viewModel.isProgressVisible.observe(this) {
            binding.progressBar.visibility = if (it) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        viewModel.updateBoard.observe(this) {
            updateBoard()
        }

        if(difficulty == Difficulty.HUMAN){
            binding.recyclerView.visibility = View.VISIBLE
            viewModel.gameStart()
        }else{
            BlackOrWhiteDialog().show(supportFragmentManager, "BlackOrWhiteDialogFragment")
        }
    }

    override fun onClickButtonInDialog(isBlack: Boolean) {
        viewModel.decideTheOrder(isBlack)
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun updateBoard() {
        cellAdapter.submitList(viewModel.cellList.flatten())
    }
}