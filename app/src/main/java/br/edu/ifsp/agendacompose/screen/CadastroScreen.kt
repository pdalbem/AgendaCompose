package br.edu.ifsp.agendacompose.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.edu.ifsp.agendacompose.data.Contato
import br.edu.ifsp.agendacompose.viewmodel.ContatoViewModel

@Composable
fun CadastroScreen(navController: NavController, contatoViewModel: ContatoViewModel){
    var nome by rememberSaveable { mutableStateOf("") }
    var fone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Contato") },
                actions = {
                    IconButton(onClick = {
                       val contato= Contato(0,nome,fone,email)
                        contatoViewModel.insert(contato)
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Outlined.Done, "Adicionar")
                    }
                }
            )
        }
    ){
        Column( modifier = Modifier.padding(it)) {

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                textStyle = TextStyle(fontSize = 30.sp),
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )



        OutlinedTextField(
            value = fone,
            onValueChange = { fone = it },
            textStyle = TextStyle(fontSize = 30.sp),
            label = { Text("Telefone") },
            modifier = Modifier.fillMaxWidth()
        )


            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                textStyle = TextStyle(fontSize = 30.sp),
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

    }

        }



}