package com.example.programa_rifas.ui.theme.vistas

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.programa_rifas.vistas_m.RifaViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Pagina_Principal(navController: NavController, viewModel: RifaViewModel = viewModel()) {
    val rifas = viewModel.rifas
    var textoBusqueda by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.cargarRifas()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Rifas", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        // Campo de búsqueda
        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = {
                textoBusqueda = it
                viewModel.buscarRifas(it)
            },
            label = { Text("Buscar rifa") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("create") }) {
            Text("Nueva Rifa")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(rifas) { rifa ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { navController.navigate("detail/${rifa.id}") }
                        ) {
                            Text(rifa.nombre, style = MaterialTheme.typography.titleMedium)
                            Text("Fecha: ${rifa.fecha}", style = MaterialTheme.typography.bodySmall)
                        }

                        IconButton(onClick = { viewModel.eliminarRifa(rifa.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}

