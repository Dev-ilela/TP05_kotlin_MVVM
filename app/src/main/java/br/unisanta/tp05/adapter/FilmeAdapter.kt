package br.unisanta.tp05.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.unisanta.tp05.databinding.ItemFilmeBinding
import br.unisanta.tp05.model.Filme
import coil.load

class FilmeAdapter(
    private val filmes:MutableList<Filme>,
    private val onEditar:(Int) -> Unit,
    private val onExcluir:(Int) -> Unit
): RecyclerView.Adapter<FilmeAdapter.ViewHolder>()
{
    class ViewHolder(val binding: ItemFilmeBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilmeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filmes.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filme = filmes[position]
        holder.binding.tvNome.text = filme.nome
        holder.binding.tvNota.text = "Nota: ${filme.nota}"
        holder.binding.imgFilme.load(filme.url)

        holder.binding.btnEditar.setOnClickListener {
            onEditar(holder.adapterPosition)
        }

        holder.binding.btnExcluir.setOnClickListener {
            onExcluir(holder.adapterPosition)
        }
    }
}
