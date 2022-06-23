package br.edu.ifsp.agendacompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.ifsp.agendacompose.screen.CadastroScreen
import br.edu.ifsp.agendacompose.screen.DetalheScreen
import br.edu.ifsp.agendacompose.screen.ListaScreen
import br.edu.ifsp.agendacompose.ui.theme.AgendaComposeTheme
import br.edu.ifsp.agendacompose.viewmodel.ContatoViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contatoViewModel = ViewModelProvider(
            this,
            ContatoViewModel.MainViewModelFactory((application as ContatoApplication).repository)
        )[ContatoViewModel::class.java]


        setContent {
            AgendaComposeTheme {
               Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                Navigation(contatoViewModel)
                  }
            }
        }
    }
}


@Composable
fun Navigation(contatoViewModel: ContatoViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "lista") {
        composable("lista") { ListaScreen(navController, contatoViewModel) }
        composable("cadastro") { CadastroScreen(navController, contatoViewModel) }
        composable("detalhe"){ DetalheScreen(navController, contatoViewModel)  }
    }
}






