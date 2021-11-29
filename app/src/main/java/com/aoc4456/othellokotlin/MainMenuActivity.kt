package com.aoc4456.othellokotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.aoc4456.othellokotlin.board.BoardActivity
import com.aoc4456.othellokotlin.databinding.ActivityMainBinding
import com.aoc4456.othellokotlin.model.Difficulty
import com.aoc4456.othellokotlin.model.KEY_INTENT_DIFFICULTY

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnWeak.setOnClickListener { goToBoardActivity(Difficulty.WEAK) }
        binding.btnNormal.setOnClickListener { goToBoardActivity(Difficulty.NORMAL) }
        binding.btnStrong.setOnClickListener { goToBoardActivity(Difficulty.STRONG) }
        binding.btnHuman.setOnClickListener { goToBoardActivity(Difficulty.HUMAN) }
    }

    private fun goToBoardActivity(difficulty: Difficulty) {
        val intent = Intent(this, BoardActivity::class.java)
        intent.putExtra(KEY_INTENT_DIFFICULTY, difficulty)
        startActivity(intent)
    }
}