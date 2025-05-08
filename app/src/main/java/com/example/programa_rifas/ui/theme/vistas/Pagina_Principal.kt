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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel




@Composable
fun Pagina_Principal(navController: NavController, viewModel: RifaViewModel = viewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    val rifas = viewModel.rifas

    LaunchedEffect(Unit) {
        viewModel.cargarRifas()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8EFEA)) // Diseño de color beige
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(45.dp))
        Text(
            "Mis Rifas",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF6D4C41) // Color para el título Mis Rifas

        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.buscarRifas(it)
            },
            label = { Text("Buscar rifa...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.navigate("create") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD7CCC8)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear Nueva Rifa", color = Color(0xFF3E2723), fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(rifas) { rifa ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier
                            .weight(1f)
                            .clickable { navController.navigate("detail/${rifa.id}") }
                        ) {
                            Text(rifa.nombre, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                            Text("Fecha: ${rifa.fecha}", fontSize = 14.sp, color = Color.Gray)
                        }

                        IconButton(onClick = { viewModel.eliminarRifa(rifa.id) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar rifa",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}

