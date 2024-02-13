package com.mpd.pmdm.practicaroommodulos.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Module::class, Ciclo::class],
    version = 2,
    exportSchema = true
)
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
        db.execSQL("CREATE TABLE IF NOT EXISTS `ciclo` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL)")
        db.execSQL("INSERT OR REPLACE INTO ciclo VALUES (1, 'Desarrollo de Aplicaciones Multiplataforma')")
        //SQlite no admite añadir foreign keys con alter table. Así que tenemos que crear una tabla temporal nueva, copiar todos los registros
        //borrar la antigua, y renombrar la nueva. Más info: https://www.sqlite.org/omitted.html y https://stackoverflow.com/questions/56374896/android-room-how-to-add-foreign-key-reference-to-data-migration
        db.execSQL("CREATE TABLE IF NOT EXISTS `module_temp` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `credits` INTEGER NOT NULL, `cicloId` INTEGER NOT NULL DEFAULT 1, `curso` INTEGER NOT NULL DEFAULT 1, FOREIGN KEY(`cicloId`) REFERENCES `ciclo`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )")
        db.execSQL("INSERT INTO `module_temp` (`id`, `name`, `credits`, `cicloId`, `curso`) " +
                "SELECT `id`, `name`, `credits`, 1, 1 FROM `module`");
        // Eliminar la tabla existente
        db.execSQL("DROP TABLE `module`");
        db.execSQL("ALTER TABLE `module_temp` RENAME TO `module`");
        db.execSQL("UPDATE module SET cicloId = 1")
    }

}