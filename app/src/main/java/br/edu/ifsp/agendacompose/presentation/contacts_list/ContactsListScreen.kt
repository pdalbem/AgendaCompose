package br.edu.ifsp.agendacompose.presentation.contacts_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.edu.ifsp.agendacompose.domain.model.Contact
import br.edu.ifsp.agendacompose.presentation.util.Screen


@Composable
fun ContactsListScreen(
    navController: NavController,
    viewModel: ContactsListViewModel = hiltViewModel()
) {
    val state by viewModel.stateList.collectAsState()
    val contactList by remember(state) {
        derivedStateOf {
            when (state) {
                is ContactsListUiState.Success -> (state as ContactsListUiState.Success).contacts
                is ContactsListUiState.Search -> (state as ContactsListUiState.Search).contacts
                else -> emptyList()
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("contactID", 0)
                    navController.navigate(Screen.ContactDetailScreen.route)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                content = {
                    Icon(imageVector = Icons.Sharp.Person, contentDescription = "Add contact")
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).background(MaterialTheme.colorScheme.background)) {
            Header()
            SearchBar(viewModel)
            when {
                contactList.isEmpty() -> EmptyContactListMessage()
                else -> ContactList(contactList, navController)
            }
        }
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Contacts",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(viewModel: ContactsListViewModel) {
    var searchText by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = searchText,
        onValueChange = {
            searchText = it
            viewModel.searchContacts(searchText)
        },
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        label = { Text("Search") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search icon") },
        placeholder = { Text("Name, phone or email", color = Color.LightGray) },
        shape = RoundedCornerShape(10.dp),
        colors =   outlinedTextFieldColors(
            focusedLabelColor = MaterialTheme.colorScheme.primary, // Primary Color
            unfocusedLabelColor = Color.Gray,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray
        )
    )
}

@Composable
fun ContactList(contactList: List<Contact>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(contactList) { contact ->
            ListItem(contact = contact, navController = navController)
        }
    }
}

@Composable
fun ListItem(contact: Contact, navController: NavController, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("contactID", contact.id)
                navController.navigate(Screen.ContactDetailScreen.route)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = contact.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface) // Primary Text Color
            Text(text = contact.phone, fontSize = 16.sp, color = Color.Gray)
        }
    }
}

@Composable
fun EmptyContactListMessage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No contacts found.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}



