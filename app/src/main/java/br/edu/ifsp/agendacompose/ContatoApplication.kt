package br.edu.ifsp.agendacompose

import android.app.Application
import br.edu.ifsp.agendacompose.data.ContatoDatabase
import br.edu.ifsp.agendacompose.data.ContatoRepositorio

class ContatoApplication: Application() {
    private val database by lazy { ContatoDatabase.getDatabase(this) }
    val repository by lazy { ContatoRepositorio(database.contatoDAO()) }
}