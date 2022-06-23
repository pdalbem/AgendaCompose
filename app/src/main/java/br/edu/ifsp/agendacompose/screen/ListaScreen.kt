package br.edu.ifsp.agendacompose.screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.edu.ifsp.agendacompose.viewmodel.ContatoViewModel


@Composable
fun ListaScreen(navController: NavController, contatoViewModel: ContatoViewModel) {

    val lista by contatoViewModel.listaContatos.observeAsState(listOf())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("cadastro") }) {
                Icon(Icons.Filled.Person, "Adicionar")
            }
        },
        topBar = {
            TopAppBar(title = { Text("Agenda") })
        }
    ) {

        LazyColumn {
            items(items = lista) { item ->
                Card(elevation = 5.dp, modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
                    .clickable {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "contato",
                            item
                        )
                        navController.navigate("detalhe")
                    }) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = item.nome, fontSize = 30.sp)
                        Text(text = item.fone, fontSize = 20.sp)
                    }
                }

            }

        }
    }
}