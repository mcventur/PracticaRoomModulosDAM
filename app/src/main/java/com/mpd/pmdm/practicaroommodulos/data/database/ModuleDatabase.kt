package com.mpd.pmdm.practicaroommodulos.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Module::class, Ciclo::class], version = 2)
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
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}

/**
 * Para hacer una migración manual
 * Extendemos la clase abstracta Migration, indicando las operaciones a realizar
 */
val MIGRATION_1_2 = object : Migration(1, 2){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE ciclo (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)")
        db.execSQL("INSERT INTO ciclo VALUES (1, 'Desarrollo de Aplicaciones Multiplataforma')")
        db.execSQL("INSERT INTO ciclo VALUES (1, 'Desarrollo de Aplicaciones Multiplataforma')")
        db.execSQL("ALTER TABLE module ADD COLUMN cicloId INTEGER NOT NULL DEFAULT 1")
        db.execSQL("ALTER TABLE module ADD COLUMN curso INTEGER NOT NULL DEFAULT 1")
        db.execSQL("UPDATE module SET cicloId = 1")
    }

}