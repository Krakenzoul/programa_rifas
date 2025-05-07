package com.example.programa_rifas.base_de_datos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.programa_rifas.base_de_datos.entidades.NumeroSeleccionado
import com.example.programa_rifas.base_de_datos.entidades.RifaEntity

@Database(entities = [NumeroSeleccionado::class, RifaEntity::class], version = 1)
abstract class Rifa_BD : RoomDatabase() {
    abstract fun rifaDao(): RifaDao
    companion object {
        @Volatile private var INSTANCE: Rifa_BD? = null

        fun getDatabase(context: Context): Rifa_BD {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Rifa_BD::class.java,
                    "rifa_bd"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
