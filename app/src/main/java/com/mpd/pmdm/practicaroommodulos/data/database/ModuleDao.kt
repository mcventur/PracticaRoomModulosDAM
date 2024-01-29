package com.mpd.pmdm.practicaroommodulos.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ModuleDao {
    @Query("SELECT * FROM module")
    fun getAllModules(): LiveData<List<Module>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(module: Module)

    @Query("DELETE FROM module")
    suspend fun clearAll()
}