package com.vladbstrv.nasaapp.view.recyclerview

const val TYPE_EARTH = 1
const val TYPE_MARS = 2
const val TYPE_HEADER = 3

data class Data(
    val id: Int = 0,
    val name: String = "Text",
    val description: String? = null,
    val type: Int = TYPE_MARS
)
