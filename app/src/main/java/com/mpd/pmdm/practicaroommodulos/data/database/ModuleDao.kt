package com.mpd.pmdm.practicaroommodulos.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ModuleDao {
    @Query("SELECT * FROM module")
    fun getAllModules(): LiveData<List<Module>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(module: Module): Long

    @Query("DELETE FROM module")
    suspend fun clearAll()

    /**
     * DAO para recuperar todos los Ciclos y Módulos relacionados
     */
    /*
    Anotamos con @Transaction porque intermanete se ejecutará otra consulta
    que se infiere de la anotación @Relation de la clase intermedia
    SELECT * FROM module
        WHERE cicloId IN (id1, id2, …)
    */
    @Transaction
    @Query("SELECT * FROM ciclo")
    fun getAllCiclosWithModules(): LiveData<List<CiclosWithModules>>

    /**
     * Devuelve los Módulos de un ÚNICO ciclo específico
     */
    @Query("SELECT * FROM module WHERE cicloId = :cicloId")
    fun getModulesOfCiclo(cicloId: Long): LiveData<List<Module>>

    /**
     * Devuelve el Ciclo de un Módulo
     */
    @Transaction
    @Query("SELECT ciclo.* FROM module JOIN ciclo ON (ciclo.id = module.cicloId) " +
            "WHERE module.cicloId = :moduleId")
    fun getCicloOfModule(moduleId: Long): CiclosWithModules

    @Query("SELECT * FROM ciclo")
    fun getAllCiclos(): LiveData<Array<Ciclo>>
}