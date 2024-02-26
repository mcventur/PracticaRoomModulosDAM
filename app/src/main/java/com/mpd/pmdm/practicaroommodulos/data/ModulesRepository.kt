package com.mpd.pmdm.practicaroommodulos.data

import androidx.sqlite.db.SimpleSQLiteQuery
import com.mpd.pmdm.practicaroommodulos.data.database.Module
import com.mpd.pmdm.practicaroommodulos.data.database.ModuleDao
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.SortFields


class ModulesRepository(private val moduleDao: ModuleDao) {

    val allModules = moduleDao.getAllModules()


    suspend fun insert(module: Module): Long {
        return moduleDao.insert(module)
    }


    suspend fun clearAll() {
        moduleDao.clearAll()
    }

    fun getModulesSorted(sortFields: SortFields, sortAsc: Boolean){
        var query = "SELECT * FROM module ORDER BY ${sortFields.toString()} "
        if(sortAsc) query += "ASC" else query += "DESC"
        moduleDao.getAllModulesSorted(SimpleSQLiteQuery(query))
    }

}