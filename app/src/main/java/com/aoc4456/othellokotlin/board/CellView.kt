package com.aoc4456.othellokotlin.board

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.aoc4456.othellokotlin.R
import com.aoc4456.othellokotlin.model.Cell
import com.aoc4456.othellokotlin.model.Stone

class CellView : ConstraintLayout {

    private lateinit var cellBackground: View
    private lateinit var stoneView: View

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        View.inflate(context, R.layout.cell, this)
        cellBackground = findViewById(R.id.cell)
        stoneView = findViewById(R.id.stoneView)
    }

    fun setAppearanceCell(cell: Cell) {
        cellBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_700))
        stoneView.visibility = View.INVISIBLE

        when (cell.stone) {
            Stone.BLACK -> {
                stoneView.visibility = VISIBLE
                stoneView.backgroundTintList = ColorStateList.valueOf(Color.BLACK)
            }
            Stone.WHITE -> {
                stoneView.visibility = VISIBLE
                stoneView.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            }
            else -> {
            }
        }
        if (cell.highlight) {
            cellBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_200))
        }
    }
}