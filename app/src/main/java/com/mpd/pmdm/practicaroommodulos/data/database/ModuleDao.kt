package com.mpd.pmdm.practicaroommodulos.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ModuleDao {
    @Query("SELECT * FROM module order by id desc")
    fun getAllModules(): LiveData<List<Module>>

    @Query("DELETE FROM module")
    suspend fun clearAll()

    //TODO: pROBAR A VER SI DEVUELVE EL ID INSERTADO COMO DICE LA DOCUMENTACION
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(module: Module)
}