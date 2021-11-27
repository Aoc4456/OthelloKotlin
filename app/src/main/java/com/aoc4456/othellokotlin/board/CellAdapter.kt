package com.aoc4456.othellokotlin.board

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aoc4456.othellokotlin.model.Cell

class CellAdapter(private val viewModel: BoardViewModel) :
    ListAdapter<Cell, CellAdapter.ViewHolder>(CellAdapterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cellView = CellView(parent.context)
        return ViewHolder(cellView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cell = getItem(position)
        val cellView = holder.itemView as CellView
        cellView.setAppearanceCell(cell)

        cellView.setOnClickListener {
            if (cell.highlight) {
                viewModel.onClickCell(cell.vertical, cell.horizontal)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

class CellAdapterDiffCallback : DiffUtil.ItemCallback<Cell>() {

    // アイテム自体が同じか
    override fun areItemsTheSame(oldItem: Cell, newItem: Cell): Boolean {
        return oldItem.vertical == newItem.vertical && oldItem.horizontal == newItem.horizontal
    }

    // アイテムの内容が同じか
    override fun areContentsTheSame(oldItem: Cell, newItem: Cell): Boolean {
        return oldItem == newItem
    }
}
