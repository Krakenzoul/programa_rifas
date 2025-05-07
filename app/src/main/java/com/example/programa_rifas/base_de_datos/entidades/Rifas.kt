package com.example.programa_rifas.base_de_datos.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rifas")
data class RifaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val fecha: String
)