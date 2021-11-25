package com.aoc4456.othellokotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.aoc4456.othellokotlin.board.BoardActivity

class MainMenuActivity : AppCompatActivity() {

    private lateinit var btnEasy : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEasy = findViewById(R.id.btnEasy)
        btnEasy.setOnClickListener {
            val intent = Intent(this, BoardActivity::class.java)
            startActivity(intent)
        }
    }
}