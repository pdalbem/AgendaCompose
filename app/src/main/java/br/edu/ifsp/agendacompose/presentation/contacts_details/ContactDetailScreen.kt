package br.edu.ifsp.agendacompose.presentation.contacts_details


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material.icons.sharp.Done
import androidx.compose.material.icons.sharp.Email
import androidx.compose.material.icons.sharp.Face
import androidx.compose.material.icons.sharp.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.edu.ifsp.agendacompose.domain.model.Contact

data class ContactFormState(
    val name: String,
    val phone: String,
    val email: String,
    val onNameChange: (String) -> Unit,
    val onPhoneChange: (String) -> Unit,
    val onEmailChange: (String) -> Unit
)

@Composable
fun ContactDetailScreen(
    navController: NavController,
    viewModel: ContactDetailViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val contactID by remember { mutableStateOf(navController.previousBackStackEntry?.savedStateHandle?.get<Int>("contactID") ?: 0) }
    val contactDetails by viewModel.contactDetails.collectAsState()
    val state by viewModel.stateDetail.collectAsState()


    // Initialize state
    val (name, setName) = remember { mutableStateOf(contactDetails?.name ?: "") }
    val (phone, setPhone) = remember { mutableStateOf(contactDetails?.phone ?: "") }
    val (email, setEmail) = remember { mutableStateOf(contactDetails?.email ?: "") }

    LaunchedEffect(contactID) {
        if (contactID != 0) {
            viewModel.getContactById(contactID)
        }
    }

    LaunchedEffect(contactDetails) {
        contactDetails?.let {
            setName(it.name)
            setPhone(it.phone)
            setEmail(it.email)
        }
    }


    LaunchedEffect(state) {
        when (state) {
            is ContactDetailUIState.InsertSuccess -> {
                snackbarHostState.showSnackbar("Contact saved", duration = SnackbarDuration.Long)
            }
            is ContactDetailUIState.DeleteSuccess -> {
                snackbarHostState.showSnackbar("Contact removed", duration = SnackbarDuration.Long)
            }
            is ContactDetailUIState.Error -> {
                snackbarHostState.showSnackbar((state as ContactDetailUIState.Error).message, duration = SnackbarDuration.Long)
            }
            else -> Unit
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            ContactDetailHeader(
                contactID = contactID,
                onBackClick = { navController.popBackStack() },
                onDeleteClick = {
                    contactDetails?.let {
                        viewModel.delete(it)
                        navController.popBackStack()
                    }
                },
                onSaveClick = {
                    val updatedContact = Contact(
                        id = contactID,
                        name = name,
                        phone = phone,
                        email = email
                    )
                    viewModel.insert(updatedContact)
                    navController.popBackStack()
                }
            )

            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

            ContactDetailFields(
                formState = ContactFormState(
                    name = name,
                    phone = phone,
                    email = email,
                    onNameChange = { newName ->
                        setName(newName)
                        viewModel.updateContact(name = newName, phone = phone, email = email)
                    },
                    onPhoneChange = { newPhone ->
                        setPhone(newPhone)
                        viewModel.updateContact(name = name, phone = newPhone, email = email)
                    },
                    onEmailChange = { newEmail ->
                        setEmail(newEmail)
                        viewModel.updateContact(name = name, phone = phone, email = newEmail)
                    }
                ),
                focusRequester = focusRequester
            )
        }
    }
}
@Composable
fun ContactDetailHeader(
    contactID: Int,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    // Remember the title based on contactID
    val title = if (contactID != 0) "Contact Detail" else "New Contact"

    // Create a row layout for header
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button
        IconButton(onClick = onBackClick) {
            Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
        }

        // Title text
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        // Conditional actions (Delete and Save buttons)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (contactID != 0) {
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Sharp.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                }
            }
            IconButton(onClick = onSaveClick) {
                Icon(Icons.Sharp.Done, contentDescription = "Save", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailFields(
    formState: ContactFormState,
    focusRequester: FocusRequester
) {
    OutlinedTextField(
        value = formState.name,
        onValueChange = formState.onNameChange,
        textStyle = TextStyle(fontSize = 20.sp),
        label = { Text("Name") },
        leadingIcon = { Icon(Icons.Sharp.Face, contentDescription = "Name icon") },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        colors = outlinedTextFieldColors(
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray
        )
    )
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = formState.phone,
        onValueChange = formState.onPhoneChange,
        textStyle = TextStyle(fontSize = 20.sp),
        label = { Text("Phone") },
        leadingIcon = { Icon(Icons.Sharp.Phone, contentDescription = "Phone icon") },
        modifier = Modifier.fillMaxWidth(),
        colors = outlinedTextFieldColors(
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray
        )
    )
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = formState.email,
        onValueChange = formState.onEmailChange,
        textStyle = TextStyle(fontSize = 20.sp),
        label = { Text("Email") },
        leadingIcon = { Icon(Icons.Sharp.Email, contentDescription = "Email icon") },
        modifier = Modifier.fillMaxWidth(),
        colors = outlinedTextFieldColors(
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray
        )
    )
}

