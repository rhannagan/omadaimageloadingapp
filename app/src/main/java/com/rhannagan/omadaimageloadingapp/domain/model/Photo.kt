package com.rhannagan.omadaimageloadingapp.domain.model

data class Photo(
    val imageUrl: String,
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String,
    val totalPages: Int,
    val currentPage: Int
) {
    companion object {
        val EMPTY = Photo(
            "",
            "",
            "",
            "",
            "",
            "",
            0,
            0
        )
    }
}