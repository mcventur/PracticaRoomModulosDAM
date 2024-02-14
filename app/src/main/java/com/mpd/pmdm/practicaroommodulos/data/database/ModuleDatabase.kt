package com.mpd.pmdm.practicaroommodulos.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Module::class, Ciclo::class],
    version = 3,
    autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true
    )
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
                    .addMigrations(MIGRATION_2_3)
                    .build()
                INSTANCE = instance

                instance
            }

        }
    }

}


val MIGRATION_2_3 = object: Migration(startVersion = 2, endVersion = 3){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `ciclo` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `abreviatura` TEXT)")
        db.execSQL("INSERT OR REPLACE INTO ciclo VALUES (1, 'Desarrollo de Aplicaciones Multiplataforma', 'DAM')")
        //SQlite no admite añadir foreign keys con alter table. Así que tenemos que crear una tabla temporal nueva, copiar todos los registros
        //borrar la antigua, y renombrar la nueva. Más info: https://www.sqlite.org/omitted.html y https://stackoverflow.com/questions/56374896/android-room-how-to-add-foreign-key-reference-to-data-migration
        db.execSQL("CREATE TABLE IF NOT EXISTS `module_temp` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `credits` INTEGER NOT NULL, `cicloId` INTEGER NOT NULL DEFAULT 1, `curso` INTEGER NOT NULL DEFAULT 1, abreviatura TEXT, FOREIGN KEY(`cicloId`) REFERENCES `ciclo`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )")
        db.execSQL("INSERT INTO `module_temp` (`id`, `name`, `credits`, `cicloId`, `curso`) " +
                "SELECT `id`, `name`, `credits`, 1, 1 FROM `module`");
        // Eliminar la tabla existente
        db.execSQL("DROP TABLE `module`");
        db.execSQL("ALTER TABLE `module_temp` RENAME TO `module`");
        //db.execSQL("UPDATE module SET cicloId = 1")
    }

}