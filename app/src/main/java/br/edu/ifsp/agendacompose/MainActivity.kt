package br.edu.ifsp.agendacompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Surface

import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.ifsp.agendacompose.presentation.contacts_details.ContactDetailScreen
import br.edu.ifsp.agendacompose.presentation.contacts_list.ContactsListScreen
import br.edu.ifsp.agendacompose.presentation.util.Screen

import br.edu.ifsp.agendacompose.ui.theme.AgendaComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  enableEdgeToEdge()
        setContent {
            AgendaComposeTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background){
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ContactsListScreen.route
                    ){
                            composable(route = Screen.ContactsListScreen.route) {
                            ContactsListScreen(navController = navController)
                        }
                            composable(route = Screen.ContactDetailScreen.route) {
                            ContactDetailScreen(navController = navController)
                        }
                    }

                }
            }
        }
    }
}


