package com.example.appfilmesapi.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Constants {
    const val API_KEY = "518992ade1aa45f07f66cc2d8d4c5a83"
    const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/"
}

object RetrofitService {

    const val API_KEY = "518992ade1aa45f07f66cc2d8d4c5a83"
    const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/"

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)//Leitura de dados
        .writeTimeout(10, TimeUnit.SECONDS)//Escrita de dados
        //.connectTimeout()
        .addInterceptor( AuthInterceptor() )
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory( GsonConverterFactory.create() )
        .client( okHttpClient )
        .build()

    val filmeAPI: FilmeAPI = retrofit.create( FilmeAPI::class.java )

}
