package br.edu.ifsp.agendacompose.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContatoDAO {
    @Insert
    suspend fun inserirContato(contato: Contato)

    @Update
    suspend fun atualizarContato (contato: Contato)

    @Delete
    suspend fun apagarContato(contato: Contato)

    @Query("SELECT * FROM contatos ORDER BY nome")
    fun listarContatos(): LiveData<List<Contato>>

}