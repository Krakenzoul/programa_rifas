package com.example.programa_rifas.ui.theme.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.programa_rifas.vistas_m.RifaViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.programa_rifas.base_de_datos.entidades.RifaEntity
import java.util.Calendar
import android.app.DatePickerDialog
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable

@Composable
fun Pagina_CreacionRifa(navController: NavController, viewModel: RifaViewModel = viewModel()) {
    var nombre by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendario = Calendar.getInstance()
    val fechaSeleccionada = remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val fecha = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
            fechaSeleccionada.value = fecha
        },
        calendario.get(Calendar.YEAR),
        calendario.get(Calendar.MONTH),
        calendario.get(Calendar.DAY_OF_MONTH)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Crear nueva Rifa", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") })
        Box(//sin eso no funciona al darle a la cajita fecha
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
        ) {
            OutlinedTextField(
                value = fechaSeleccionada.value,
                onValueChange = {},
                label = { Text("Fecha") },
                readOnly = true,
                enabled = false, // evita que el usuario intente escribir
                modifier = Modifier.fillMaxWidth()
            )
        }
        Button(onClick = { datePickerDialog.show() }) {
            Text("Probar calendario")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (nombre.isNotBlank() && fechaSeleccionada.value.isNotBlank()) {
                viewModel.guardarRifa(
                    RifaEntity(nombre = nombre, fecha = fechaSeleccionada.value)
                )
                navController.popBackStack()
            }
        }) {
            Text("Guardar")
        }
    }
}