package com.mpd.pmdm.practicaroommodulos.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Module::class], version = 1, exportSchema = true)
abstract class ModuleDatabase: RoomDatabase() {

    abstract fun moduleDao(): ModuleDao
}