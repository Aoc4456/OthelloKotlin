package com.aoc4456.othellokotlin.model

data class Cell(
    val vertical :Int,
    val horizontal : Int,
    var stone: Stone,
    var highlight: Boolean = false
)