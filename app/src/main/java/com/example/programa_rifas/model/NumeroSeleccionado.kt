package com.example.programa_rifas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NumeroSeleccionado(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val numero: Int,
    val nombreUsuario: String,
    val fecha: String,
    val rifaId: Int
)