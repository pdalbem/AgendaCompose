package br.edu.ifsp.agendacompose.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
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
fun DetalheScreen(navController: NavController, contatoViewModel: ContatoViewModel){
    val contato = navController.previousBackStackEntry?.savedStateHandle?.get<Contato>("contato")
    if (contato!=null) {
        val id by rememberSaveable {mutableStateOf(contato.id)}
        var nome by rememberSaveable { mutableStateOf(contato.nome) }
        var fone by rememberSaveable { mutableStateOf(contato.fone) }
        var email by rememberSaveable { mutableStateOf(contato.email) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Detalhes") },
                    actions = {
                        IconButton(onClick = {
                             val c = Contato(id, nome, fone, email)
                             contatoViewModel.delete(c)
                            navController.popBackStack()

                        }) {
                            Icon(Icons.Outlined.Delete, "Apagar")
                        }

                        IconButton(onClick = {
                            val c = Contato(id, nome, fone, email)
                             contatoViewModel.update(c)
                            navController.popBackStack()

                        }) {
                            Icon(Icons.Outlined.Done, "Atualizar")
                        }

                    }
                )
            }
        ) {
            Column(modifier = Modifier.padding(it)) {

                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    textStyle = TextStyle(fontSize = 30.sp),
                    label = { Text("Nome") },
                    modifier = Modifier
                        .fillMaxWidth()
                )



                OutlinedTextField(
                    value = fone,
                    onValueChange = { fone = it },
                    textStyle = TextStyle(fontSize = 30.sp),
                    label = { Text("Telefone") },
                    modifier = Modifier
                             .fillMaxWidth()
                )


                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    textStyle = TextStyle(fontSize = 30.sp),
                    label = { Text("Email") },
                    modifier = Modifier
                          .fillMaxWidth()
                )

            }

        }


    }
}