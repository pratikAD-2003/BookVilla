package com.pycreations.bookvilla.data.models

data class GenreResponseModel(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)