package br.edu.ifsp.agendacompose.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.agendacompose.data.Contato
import br.edu.ifsp.agendacompose.data.ContatoRepositorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ContatoViewModel (private val contatoRepositorio: ContatoRepositorio): ViewModel() {

    var listaContatos: LiveData<List<Contato>> = contatoRepositorio.getAllContacts()

    fun insert(c: Contato) {
        viewModelScope.launch(Dispatchers.IO) {
            contatoRepositorio.insert(c)
        }
    }

    fun update(c: Contato) {
        viewModelScope.launch(Dispatchers.IO) {
            contatoRepositorio.update(c)
        }
    }

    fun delete(c: Contato) {
        viewModelScope.launch(Dispatchers.IO) {
            contatoRepositorio.delete(c)
        }
    }


    class MainViewModelFactory(private val contatoRepositorio: ContatoRepositorio)
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ContatoViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return ContatoViewModel(contatoRepositorio) as T
            }
            throw IllegalArgumentException("Unknown VieModel Class")
        }
    }
}