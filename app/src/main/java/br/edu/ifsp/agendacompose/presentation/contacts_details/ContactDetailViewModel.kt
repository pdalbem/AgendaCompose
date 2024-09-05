package br.edu.ifsp.agendacompose.presentation.contacts_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.agendacompose.domain.model.Contact
import br.edu.ifsp.agendacompose.domain.usecase.ContactUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ContactDetailUIState {
    data object InsertSuccess : ContactDetailUIState()
    data object UpdateSuccess : ContactDetailUIState()
    data object DeleteSuccess : ContactDetailUIState()
    data class GetByIdSuccess(val c: Contact) : ContactDetailUIState()
    data object ShowLoading : ContactDetailUIState()
    data class Error(val message: String) : ContactDetailUIState()

}

@HiltViewModel
class ContactDetailViewModel @Inject constructor(
    private val contactUseCases: ContactUseCases
) : ViewModel() {

    private val _stateDetail = MutableStateFlow<ContactDetailUIState>(ContactDetailUIState.ShowLoading)
    val stateDetail: StateFlow<ContactDetailUIState> = _stateDetail.asStateFlow()

    private val _contactDetails = MutableStateFlow<Contact?>(null)
    val contactDetails: StateFlow<Contact?> = _contactDetails.asStateFlow()

    fun insert(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        try {
            contactUseCases.insertContact(contact)
            _stateDetail.value = ContactDetailUIState.InsertSuccess
        } catch (e: Exception) {
            _stateDetail.value = ContactDetailUIState.Error("Failed to insert contact: ${e.localizedMessage}")
        }
    }

    fun delete(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        try {
            contactUseCases.deleteContact(contact)
            _stateDetail.value = ContactDetailUIState.DeleteSuccess
        } catch (e: Exception) {
            _stateDetail.value = ContactDetailUIState.Error("Failed to delete contact: ${e.localizedMessage}")
        }
    }

    fun getContactById(id: Int) = viewModelScope.launch {
        _stateDetail.value = ContactDetailUIState.ShowLoading
        try {
            val contact = contactUseCases.getContact(id)
            if (contact != null) {
                _contactDetails.value = contact
                _stateDetail.value = ContactDetailUIState.GetByIdSuccess(contact)
            } else {
                _stateDetail.value = ContactDetailUIState.Error("Contact not found")
            }
        } catch (e: Exception) {
            _stateDetail.value = ContactDetailUIState.Error("Failed to retrieve contact: ${e.localizedMessage}")
        }
    }

    fun updateContact(name: String, phone: String, email: String) {
        _contactDetails.value?.let {
            _contactDetails.value = it.copy(name = name, phone = phone, email = email)
        }
    }

}
