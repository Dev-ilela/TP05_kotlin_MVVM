package br.unisanta.tp05.dao

import br.unisanta.tp05.model.Filme

class FilmeDao {
    companion object{
        private val filmes = mutableListOf<Filme>()

        fun salvar(filme: Filme){
            filmes.add(filme)
        }

        fun editar(posicao:Int, filme: Filme){
            filmes[posicao] = filme
        }

        fun excluir(posicao: Int){
            filmes.removeAt(posicao)
        }

        fun obter():MutableList<Filme>{
            return filmes
        }
    }
}
