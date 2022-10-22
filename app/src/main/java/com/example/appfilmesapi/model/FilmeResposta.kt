package com.example.appfilmesapi.model

import com.example.appfilmesapi.model.Filme

data class FilmeResposta(
    val page: Int,
    val results: List<Filme>,
    val total_pages: Int,
    val total_results: Int
)