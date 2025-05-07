package com.example.programa_rifas.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.programa_rifas.ui.theme.vistas.Pagina_CreacionRifa
import com.example.programa_rifas.ui.theme.vistas.Pagina_Principal
import com.example.programa_rifas.ui.theme.vistas.Pagina_DetallesRifa

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Pagina_Principal(navController)
        }
        composable("create") {
            Pagina_CreacionRifa(navController)
        }
        composable("detail/{rifaId}") { backStackEntry ->
            val rifaId = backStackEntry.arguments?.getString("rifaId")?.toIntOrNull() ?: 0
            Pagina_DetallesRifa(navController = navController, rifaId = rifaId)
        }
    }
}
