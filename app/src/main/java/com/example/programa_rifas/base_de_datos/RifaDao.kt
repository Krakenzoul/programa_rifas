package com.example.programa_rifas.base_de_datos

import androidx.room.*
import com.example.programa_rifas.base_de_datos.entidades.NumeroSeleccionado
import com.example.programa_rifas.base_de_datos.entidades.RifaEntity

@Dao
interface RifaDao {
    @Insert
    suspend fun insertRifa(rifa: RifaEntity)

    @Query("SELECT * FROM rifas")
    suspend fun getAllRifas(): List<RifaEntity>

    @Insert
    suspend fun insertNumero(numero: NumeroSeleccionado)

    @Query("SELECT * FROM numeros WHERE rifaId = :rifaId")
    suspend fun getNumerosPorRifa(rifaId: Int): List<NumeroSeleccionado>
}