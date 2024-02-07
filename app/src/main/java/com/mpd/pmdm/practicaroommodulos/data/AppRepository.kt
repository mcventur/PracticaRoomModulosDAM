package com.mpd.pmdm.practicaroommodulos.data

import com.mpd.pmdm.practicaroommodulos.data.database.Module
import com.mpd.pmdm.practicaroommodulos.data.database.ModuleDao

class AppRepository(private val moduleDao: ModuleDao) {

    val allModules = moduleDao.getAllModules()
    val allCiclos = moduleDao.getAllCiclos()


    suspend fun insert(module: Module): Long {
        return moduleDao.insert(module)
    }


    suspend fun clearAll() {
        moduleDao.clearAll()
    }

}