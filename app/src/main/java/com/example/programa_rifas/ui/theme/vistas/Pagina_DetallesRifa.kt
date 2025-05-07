package com.example.programa_rifas.ui.theme.vistas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.programa_rifas.base_de_datos.entidades.NumeroSeleccionado
import com.example.programa_rifas.vistas_m.RifaViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Pagina_DetallesRifa(navController: NavController, rifaId: Int) {
    val numerosSeleccionados = remember { mutableStateListOf<Int>() }
    val showDialog = remember { mutableStateOf(false) }
    val numeroSeleccionado = remember { mutableIntStateOf(-1) }
    val nombreUsuario = remember { mutableStateOf(TextFieldValue()) }
    val viewModel: RifaViewModel = viewModel()
    var numeroGanadorTexto by remember { mutableStateOf(TextFieldValue()) }
    val ganadorNombre = viewModel.ganadorNombre.value


    LaunchedEffect(Unit) {
        val yaOcupados = viewModel.obtenerNumeros(rifaId).map { it.numero }
        numerosSeleccionados.addAll(yaOcupados)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Números Disponibles", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(
            value = numeroGanadorTexto,
            onValueChange = { numeroGanadorTexto = it },
            label = { Text("Número ganador") }
        )
        Button(onClick = {
            val numero = numeroGanadorTexto.text.toIntOrNull()
            numero?.let { viewModel.seleccionarGanadorPorNumero(it, rifaId) }
        }) {
            Text("Mostrar Ganador")
        }
        ganadorNombre?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text("🎉 Ganador: $it 🎉", style = MaterialTheme.typography.titleMedium)
        }


        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(8), modifier = Modifier.weight(1f)) {
            items(100) { i ->
                val ocupado = numerosSeleccionados.contains(i)
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .background(if (ocupado) Color.LightGray else Color.Green)
                        .clickable(enabled = !ocupado) {
                            numeroSeleccionado.intValue = i
                            showDialog.value = true
                        }
                        .size(36.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(if (i < 10) "0$i" else "$i", color = Color.White)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Registrar número ${numeroSeleccionado.intValue}") },
            text = {
                Column {
                    OutlinedTextField(
                        value = nombreUsuario.value,
                        onValueChange = { nombreUsuario.value = it },
                        label = { Text("Nombre del usuario") }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    viewModel.guardarNumero(
                        NumeroSeleccionado(
                            numero = numeroSeleccionado.intValue,
                            nombreUsuario = nombreUsuario.value.text,
                            fecha = fechaActual,
                            rifaId = rifaId
                        )
                    ) {
                        numerosSeleccionados.add(numeroSeleccionado.intValue)
                        showDialog.value = false
                    }
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
