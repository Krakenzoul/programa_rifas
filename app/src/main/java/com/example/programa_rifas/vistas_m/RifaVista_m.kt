package com.example.programa_rifas.vistas_m

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.programa_rifas.base_de_datos.Rifa_BD
import com.example.programa_rifas.base_de_datos.entidades.RifaEntity
import com.example.programa_rifas.base_de_datos.entidades.NumeroSeleccionado
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import android.util.Log

class RifaViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = Rifa_BD.getDatabase(application).rifaDao()
    private var listaNumeros = emptyList<NumeroSeleccionado>()
    val rifas = mutableStateListOf<RifaEntity>()
    val ganadorNombre = mutableStateOf<String?>(null)

    fun cargarRifas() {

        viewModelScope.launch {
            val lista = dao.getAllRifas()
            rifas.clear()
            rifas.addAll(lista)
            Log.d("Cargar_Rifa", "Lista: ${lista.joinToString()}")
        }
    }
    fun guardarRifa(rifa: RifaEntity) {
        viewModelScope.launch {
            dao.insertRifa(rifa)

            cargarRifas()
        }
    }

    fun guardarNumero(numero: NumeroSeleccionado, onGuardado: () -> Unit) {
        viewModelScope.launch {
            dao.insertNumero(numero)
            onGuardado()
        }
    }

    suspend fun obtenerNumeros(rifaId: Int): List<NumeroSeleccionado> {
        listaNumeros = dao.getNumerosPorRifa(rifaId)
        Log.d("NUMEROS", "Participantes: ${listaNumeros.map { "${it.numero} - ${it.nombreUsuario}" }}")
        return listaNumeros
    }

    fun seleccionarGanadorPorNumero(numero: Int, rifaId: Int) {
        viewModelScope.launch {
            val lista = dao.getNumerosPorRifa(rifaId)
            val numeroGanador = lista.find { it.numero == numero }
            ganadorNombre.value = numeroGanador?.nombreUsuario ?: "Número no registrado"
        }
    }

    fun buscarRifas(query: String) {
        viewModelScope.launch {
            val listaFiltrada = dao.buscarRifasPorNombre(query)
            rifas.clear()
            rifas.addAll(listaFiltrada)
        }
    }

    fun eliminarRifa(rifaId: Int) {
        viewModelScope.launch {
            dao.deleteRifa(rifaId)
            cargarRifas()
        }
    }

}

