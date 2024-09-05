package br.edu.ifsp.agendacompose.presentation.util

sealed class Screen(val route: String) {
    data object ContactsListScreen: Screen("contacts_list_screen")
    data object ContactDetailScreen: Screen("contact_detail_screen")
}