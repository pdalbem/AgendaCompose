package br.edu.ifsp.agendacompose.domain.usecase

import br.edu.ifsp.agendacompose.domain.model.Contact
import br.edu.ifsp.agendacompose.domain.repository.ContactRepository

class GetContact (private  val repository: ContactRepository){
    suspend operator fun invoke(id: Int): Contact? {
        return repository.getContactById(id)
    }

}
