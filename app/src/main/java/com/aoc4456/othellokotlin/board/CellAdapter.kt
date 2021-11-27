package com.aoc4456.othellokotlin.board

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aoc4456.othellokotlin.model.Cell

class CellAdapter : ListAdapter<Cell, CellAdapter.ViewHolder>(CellAdapterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ViewHolder private constructor(parent: ViewGroup) : RecyclerView.ViewHolder(parent) {

    }
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
