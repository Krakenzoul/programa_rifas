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
import androidx.compose.ui.text.font.FontWeight

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
            .background(Color(0xFFFFF3E0)) // Fondo crema suave
            .padding(35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD7CCC8)),
            modifier = Modifier
                .padding(bottom = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                "Números de la Rifa",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF4E342E),
                modifier = Modifier.padding(5.dp)
            )
        }

        OutlinedTextField(
            value = numeroGanadorTexto,
            onValueChange = { numeroGanadorTexto = it },
            label = { Text("Número ganador") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val numero = numeroGanadorTexto.text.toIntOrNull()
                numero?.let { viewModel.seleccionarGanadorPorNumero(it, rifaId) }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8D6E63))
        ) {
            Text("Mostrar Ganador", color = Color.White) //Botón que al clickear muestra el ganador
        }

        ganadorNombre?.let { // Esta parte muestra el ganador de la rifa
            Spacer(modifier = Modifier.height(12.dp))
            Text("🎉 Ganador: $it 🎉", style = MaterialTheme.typography.titleMedium, color = Color(0xFF4E342E))
        }

        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(8), modifier = Modifier.weight(1f)) {
            items(100) { i ->
                val ocupado = numerosSeleccionados.contains(i)
                Box(  //Esto es código que modifica el estilo de las celdas cuidado con darle numeros grandes
                    modifier = Modifier
                        .padding(2.dp)
                        .background(
                            color = if (ocupado) Color(0xFFD7CCC8) else Color(0xFF81C784),
                            shape = MaterialTheme.shapes.medium
                        )
                        .clickable(enabled = !ocupado) {
                            numeroSeleccionado.intValue = i
                            showDialog.value = true
                        }
                        .size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        if (i < 10) "0$i" else "$i",
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D4C41))
        ) {
            Text("Volver", color = Color.White)
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Registrar número ${numeroSeleccionado.intValue}") },
            text = {
                OutlinedTextField(
                    value = nombreUsuario.value,
                    onValueChange = { nombreUsuario.value = it },
                    label = { Text("Nombre del usuario") },
                    modifier = Modifier.fillMaxWidth()
                )
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
