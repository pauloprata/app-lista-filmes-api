package com.example.appfilmesapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appfilmesapi.api.RetrofitService
import com.example.appfilmesapi.databinding.ActivityDetalheBinding
import com.example.appfilmesapi.model.Filme
import com.squareup.picasso.Picasso

class DetalheActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetalheBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var filme: Filme? = null
        val bundle = intent.extras
        if (bundle != null){
            filme = bundle.getSerializable("filme") as Filme
            if (filme != null){
                val nomeImagem = filme.poster_path
                val tamanhoImagem = "w780"
                val url_base = RetrofitService.BASE_URL_IMAGE
                val url = url_base + tamanhoImagem + nomeImagem
                Picasso.get().load(url).into( binding.imgPoster)

                binding.textFilmeTitulo.text = filme.title
            }
        }

    }
}