package com.mpd.pmdm.practicaroommodulos.data

import com.mpd.pmdm.practicaroommodulos.data.database.Module
import com.mpd.pmdm.practicaroommodulos.data.database.ModuleDao

class AppRepository(private val moduleDao: ModuleDao) {

    val allModules = moduleDao.getAllModules()


    suspend fun insert(module: Module) {
        moduleDao.insert(module)
    }


    suspend fun clearAll() {
        moduleDao.clearAll()
    }

}