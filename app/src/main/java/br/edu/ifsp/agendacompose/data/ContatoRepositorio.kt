package br.edu.ifsp.agendacompose.data

import androidx.lifecycle.LiveData

class ContatoRepositorio  (private val contatoDAO: ContatoDAO) {

    suspend fun insert(c: Contato){
        contatoDAO.inserirContato(c)
    }

    suspend fun delete(c: Contato){
        contatoDAO.apagarContato(c)
    }

    suspend fun update(c: Contato){
        contatoDAO.atualizarContato(c)
    }

    fun getAllContacts(): LiveData<List<Contato>> {
        return contatoDAO.listarContatos()

    }
}