package com.aoc4456.othellokotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.aoc4456.othellokotlin.board.BoardActivity
import com.aoc4456.othellokotlin.model.Difficulty
import com.aoc4456.othellokotlin.model.KEY_INTENT_DIFFICULTY

class MainMenuActivity : AppCompatActivity() {

    private lateinit var btnEasy : Button
    private lateinit var btnHuman : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEasy = findViewById(R.id.btnEasy)
        btnEasy.setOnClickListener {
            val intent = Intent(this, BoardActivity::class.java)
            intent.putExtra(KEY_INTENT_DIFFICULTY,Difficulty.WEAK)
            startActivity(intent)
        }

        btnHuman = findViewById(R.id.btnHuman)
        btnHuman.setOnClickListener {
            val intent = Intent(this, BoardActivity::class.java)
            intent.putExtra(KEY_INTENT_DIFFICULTY,Difficulty.HUMAN)
            startActivity(intent)
        }
    }
}