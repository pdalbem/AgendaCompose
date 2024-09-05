package br.edu.ifsp.agendacompose.data.repository

import br.edu.ifsp.agendacompose.data.datasource.ContactDao
import br.edu.ifsp.agendacompose.domain.model.Contact
import br.edu.ifsp.agendacompose.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow


class ContactRepositoryImpl(
    private val dao: ContactDao
) : ContactRepository {
    override fun getContacts(): Flow<List<Contact>> {
        return  dao.getContacts()
    }

    override suspend fun getContactById(id: Int): Contact? {
      return  dao.getContactById(id)
    }

    override suspend fun insertContact(contact: Contact) {
       dao.insertContact(contact)
    }

    override suspend fun deleteContact(contact: Contact) {
        dao.deleteContact(contact)
    }


}