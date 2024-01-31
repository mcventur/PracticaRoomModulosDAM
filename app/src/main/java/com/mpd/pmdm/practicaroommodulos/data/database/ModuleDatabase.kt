package com.mpd.pmdm.practicaroommodulos.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Module::class], version = 1)
abstract class ModuleDatabase: RoomDatabase() {
    abstract fun moduleDao(): ModuleDao

    companion object {
        @Volatile
        private var INSTANCE: ModuleDatabase? = null

        fun getDatabase(context: Context): ModuleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ModuleDatabase::class.java,
                    "dam_modules_database"
                )
                    .build()
                INSTANCE = instance

                instance
            }

        }
    }

}