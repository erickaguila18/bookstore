package com.enkodo.bookstore.api.response.books

data class Book(
    val author: String,
    val description: String,
    val genre: String,
    val img: String,
    val imported: Boolean,
    val isbn: String,
    val title: String
)