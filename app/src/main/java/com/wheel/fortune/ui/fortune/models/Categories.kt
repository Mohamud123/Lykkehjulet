package com.wheel.fortune.ui.fortune.models

data class Categories(
    val id : Int,
    val category : String,
    val word : List<GuessWord>,
) {
}