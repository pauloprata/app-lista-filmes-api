package com.example.appfilmesapi.api

import com.example.appfilmesapi.model.FilmeResposta
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmeAPI {
    @GET("movie/popular")
    suspend fun  recuperarFilmesPopulares(
        @Query("page") pagina: Int = 1
    ): Response<FilmeResposta>
}