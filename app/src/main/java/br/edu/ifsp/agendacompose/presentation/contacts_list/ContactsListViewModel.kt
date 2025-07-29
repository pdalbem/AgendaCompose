package br.edu.ifsp.agendacompose.presentation.contacts_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.agendacompose.domain.model.Contact
import br.edu.ifsp.agendacompose.domain.usecase.ContactUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class ContactsListUiState {
    data class Success(val contacts: List<Contact>) : ContactsListUiState()
    data class Search(val contacts: List<Contact>) : ContactsListUiState()
    data object Empty : ContactsListUiState()
    data object Loading : ContactsListUiState()
}

@HiltViewModel
class ContactsListViewModel @Inject constructor(
    private val contactUseCases: ContactUseCases
) : ViewModel() {

    private val _stateList = MutableStateFlow<ContactsListUiState>(ContactsListUiState.Loading)
    val stateList = _stateList.asStateFlow()

    private var allContacts: List<Contact> = emptyList()

    init {
        fetchContacts()
    }

    private fun fetchContacts() {
        viewModelScope.launch {
            contactUseCases.getContacts()
                .onStart { _stateList.value = ContactsListUiState.Loading }
                .collect { result ->
                    allContacts = result
                    updateState()
                }
        }
    }

    fun searchContacts(query: String) {
        val filteredContacts = if (query.isBlank()) {
            allContacts
        } else {
            allContacts.filter { contact ->
                contact.name.contains(query, ignoreCase = true) ||
                        contact.phone.contains(query, ignoreCase = true) ||
                        contact.email.contains(query, ignoreCase = true)
            }
        }
        _stateList.value = when {
            filteredContacts.isEmpty() && query.isNotBlank() -> ContactsListUiState.Empty
            else -> ContactsListUiState.Search(filteredContacts)
        }
    }

    private fun updateState() {
        _stateList.value = if (allContacts.isEmpty()) {
            ContactsListUiState.Empty
        } else {
            ContactsListUiState.Success(allContacts)
        }
    }
}
