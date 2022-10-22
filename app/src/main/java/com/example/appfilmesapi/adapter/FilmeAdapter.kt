package com.example.appfilmesapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfilmesapi.api.RetrofitService
import com.example.appfilmesapi.databinding.ItemFilmeBinding
import com.example.appfilmesapi.model.Filme

import com.squareup.picasso.Picasso

class FilmeAdapter(
    val onClickAbrir: (Filme) -> Unit
) : RecyclerView.Adapter<FilmeAdapter.FilmeViewHolder>() {

    var listaFilmes = mutableListOf<Filme>()

    fun adicionarLista(lista: List<Filme>){
        this.listaFilmes.addAll(lista)
        notifyDataSetChanged()

    }
    inner class FilmeViewHolder(itemFilme: ItemFilmeBinding)
        : RecyclerView.ViewHolder(itemFilme.root) {
        private val binding: ItemFilmeBinding
        init {
            this.binding = itemFilme
        }

        fun bind( filme: Filme ){

            val nomeImagem = filme.backdrop_path
            val tamanhoImagem = "w780"
            val url_base = RetrofitService.BASE_URL_IMAGE
            val url = url_base + tamanhoImagem + nomeImagem

            Picasso.get().load(url).into( binding.imgItemFilme )
            binding.textTitulo.text = filme.title
            binding.clItem.setOnClickListener {
                onClickAbrir( filme )
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemFilmeView = ItemFilmeBinding.inflate(
           layoutInflater,parent,false
        )
        return FilmeViewHolder(itemFilmeView)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        val filme = listaFilmes[position]
        holder.bind( filme )
    }

    override fun getItemCount(): Int {
        return listaFilmes.size
    }


}