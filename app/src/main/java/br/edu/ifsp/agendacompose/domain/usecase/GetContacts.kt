package br.edu.ifsp.agendacompose.domain.usecase

import br.edu.ifsp.agendacompose.domain.model.Contact
import br.edu.ifsp.agendacompose.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow

class GetContacts(
    private val repository: ContactRepository
) {

    operator fun invoke(): Flow<List<Contact>>{
        return repository.getContacts()
    }


}

