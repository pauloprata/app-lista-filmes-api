package com.example.appfilmesapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appfilmesapi.adapter.FilmeAdapter
import com.example.appfilmesapi.api.RetrofitService
import com.example.appfilmesapi.api.RetrofitService.filmeAPI
import com.example.appfilmesapi.databinding.ActivityMainBinding
import com.example.appfilmesapi.model.FilmeResposta

import kotlinx.coroutines.*
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private val TAG = "info_filme"
    private var job: Job? = null
    private var paginaAtual = 1
    var gridLayoutManager: GridLayoutManager? = null
    private val binding by lazy {
        ActivityMainBinding.inflate( layoutInflater )
    }

    private val filmeAPi by lazy {
        RetrofitService.filmeAPI
    }
    var filmeAdapter: FilmeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root )
        filmeAdapter = FilmeAdapter{filme->
            val intent = Intent(this,DetalheActivity::class.java)
            intent.putExtra("filme",filme)
            startActivity(intent)

        }
        binding.rvPopulares.adapter = filmeAdapter
        gridLayoutManager = GridLayoutManager(
            this,2

        )
        binding.rvPopulares.layoutManager = GridLayoutManager(this,2)

        binding.rvPopulares.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val podeRolarVerticalmente = recyclerView.canScrollVertically(1)
                if (!podeRolarVerticalmente)    {
                //Log.i(TAG, "scroll${podeRolarVerticalmente}")

            }


            }

        })

        binding.button.setOnClickListener {

            recuperarFilmesPopularesProximaPagina()
        }


    }

    private fun recuperarFilmesPopularesProximaPagina(){
        if( paginaAtual < 1000 ){
            paginaAtual++
            recuperarFilmesPopulares( paginaAtual )
        }
    }

    private fun carregarDados(){
        job = CoroutineScope(Dispatchers.IO).launch {
            recuperarFilmesPopulares()
        }
    }
    private fun recuperarFilmesPopulares( pagina: Int = 1 ){
        // A cada 20 segundos, atualiza os dados
        job = CoroutineScope(Dispatchers.IO).launch {

            var resposta: Response<FilmeResposta>? = null

            try {
                Log.i(TAG, "pagina atual: $paginaAtual")
                resposta = filmeAPI.recuperarFilmesPopulares( pagina )
            }catch (e:Exception){
                e.printStackTrace()
                Log.i(TAG, "Erro aos recuperar filmes populares")
            }

            if ( resposta != null ){
                if( resposta.isSuccessful ){

                    val listaFilmes = resposta.body()?.results
                    if ( listaFilmes != null ){
                        withContext(Dispatchers.Main){
                            filmeAdapter?.adicionarLista( listaFilmes )
                        }
                    }
                    /*listaFilmes?.forEach { filme ->
                        Log.i(TAG, "${filme.id} - ${filme.title}")
                    }*/
                }else{
                    Log.i(TAG, "Erro na requisição-codigo erro: ${resposta.code()}")
                }
            }



    }
    }
    override fun onStart() {
        super.onStart()
        recuperarFilmesPopulares()
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }
}