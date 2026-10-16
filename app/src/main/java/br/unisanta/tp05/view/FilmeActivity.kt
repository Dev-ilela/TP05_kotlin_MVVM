package br.unisanta.tp05.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import br.unisanta.tp05.R
import br.unisanta.tp05.databinding.ActivityFilmeBinding
import br.unisanta.tp05.viewmodel.FilmeViewModel
import coil.load

class FilmeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmeBinding
    private lateinit var filmeViewModel: FilmeViewModel
    private var posicao = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFilmeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        filmeViewModel = ViewModelProvider(this)[FilmeViewModel::class.java]

        posicao = intent.getIntExtra("posicao", -1)

        if(posicao != -1){
            val filme = filmeViewModel.obterFilme(posicao)
            binding.tvTituloTela.text = "Editar Filme"
            binding.edtNome.setText(filme.nome)
            binding.edtNota.setText(filme.nota.toString())
            binding.edtUrl.setText(filme.url)
            binding.imgFilme.load(filme.url)
        }

        binding.edtUrl.doAfterTextChanged {
            binding.imgFilme.load(binding.edtUrl.text.toString())
        }

        binding.btnSalvar.setOnClickListener {
            val nome = binding.edtNome.text.toString()
            val nota = binding.edtNota.text.toString()
            val url = binding.edtUrl.text.toString()

            val erro = filmeViewModel.validar(nome, nota, url)

            if(erro.isNotEmpty()){
                Toast.makeText(this, erro, Toast.LENGTH_SHORT).show()
            }
            else{
                val retorno = if(posicao == -1){
                    filmeViewModel.salvar(nome, nota, url)
                } else {
                    filmeViewModel.editar(posicao, nome, nota, url)
                }
                Toast.makeText(this, retorno, Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        binding.fabVoltar.setOnClickListener {
            finish()
        }
    }
}
