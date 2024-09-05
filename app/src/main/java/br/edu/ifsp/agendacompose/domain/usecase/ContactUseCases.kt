package br.edu.ifsp.agendacompose.domain.usecase

data class ContactUseCases(
    val getContacts: GetContacts,
    val deleteContact: DeleteContact,
    val insertContact: InsertContact,
    val getContact: GetContact
)