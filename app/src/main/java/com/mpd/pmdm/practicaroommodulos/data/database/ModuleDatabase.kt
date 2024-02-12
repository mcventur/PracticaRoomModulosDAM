package com.mpd.pmdm.practicaroommodulos.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Module::class], version = 1, exportSchema = true)
abstract class ModuleDatabase: RoomDatabase() {

    abstract fun moduleDao(): ModuleDao

    companion object{
        //Marcamos como volatile para que cualquier hilo vea el valor actual del dato
        @Volatile
        private var INSTANCE: ModuleDatabase? = null

        //Nótese el uso del operador Elvis
        fun getDataBase(context: Context): ModuleDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = databaseBuilder(
                    context,
                    ModuleDatabase::class.java,
                    "app_database")
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}