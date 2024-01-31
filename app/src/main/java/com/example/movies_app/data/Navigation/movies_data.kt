package com.example.movies_app.data.Navigation

data class movies_data(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)