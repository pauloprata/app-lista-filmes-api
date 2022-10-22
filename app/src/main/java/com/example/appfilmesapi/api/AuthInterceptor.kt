package com.example.appfilmesapi.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {

        //Chain -> cadeia 1 2 REQUISICAO 4 5
        val requisicaoAtual = chain.request().newBuilder()

        //https://api.themoviedb.org/3/ + movie/popular
        val urlAtual = chain.request().url()
        val novaUrl = urlAtual.newBuilder()
            .addQueryParameter("api_key", RetrofitService.API_KEY)
            .build()

        val construtorRequisicao = requisicaoAtual.url( novaUrl )

        return chain.proceed( construtorRequisicao.build() )

    }
}