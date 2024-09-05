package br.edu.ifsp.agendacompose.domain.repository

import br.edu.ifsp.agendacompose.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun getContacts(): Flow<List<Contact>>

    suspend fun getContactById(id: Int): Contact?

    suspend fun insertContact(contact: Contact)

    suspend fun deleteContact(contact: Contact)
}




