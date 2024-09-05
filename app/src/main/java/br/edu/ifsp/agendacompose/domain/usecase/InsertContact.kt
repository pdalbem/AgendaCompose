package br.edu.ifsp.agendacompose.domain.usecase

import br.edu.ifsp.agendacompose.domain.model.Contact
import br.edu.ifsp.agendacompose.domain.repository.ContactRepository

class InsertContact (private val repository: ContactRepository){
    suspend operator fun invoke(contact: Contact) {
        repository.insertContact(contact)
    }

}