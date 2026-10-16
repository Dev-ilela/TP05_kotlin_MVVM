package br.unisanta.tp05.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.unisanta.tp05.R
import br.unisanta.tp05.adapter.FilmeAdapter
import br.unisanta.tp05.databinding.ActivityMainBinding
import br.unisanta.tp05.viewmodel.FilmeViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var filmeViewModel: FilmeViewModel
    private lateinit var adapter: FilmeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        filmeViewModel = ViewModelProvider(this)[FilmeViewModel::class.java]

        adapter = FilmeAdapter(filmeViewModel.obterFilmes(),
            onEditar = { posicao ->
                val intent = Intent(this, FilmeActivity::class.java)
                intent.putExtra("posicao", posicao)
                startActivity(intent)
            },
            onExcluir = { posicao ->
                confirmarExclusao(posicao)
            }
        )

        binding.rvFilmes.adapter = adapter
        binding.rvFilmes.layoutManager = LinearLayoutManager(this)

        binding.fabAdicionar.setOnClickListener {
            val intent = Intent(this, FilmeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    private fun confirmarExclusao(posicao:Int){
        val filme = filmeViewModel.obterFilme(posicao)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Excluir filme")
        builder.setMessage("Deseja excluir ${filme.nome}?")
        builder.setPositiveButton("Sim") { _, _ ->
            val retorno = filmeViewModel.excluir(posicao)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, retorno, Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Não", null)
        val dialog = builder.create()
        dialog.show()
    }
}
