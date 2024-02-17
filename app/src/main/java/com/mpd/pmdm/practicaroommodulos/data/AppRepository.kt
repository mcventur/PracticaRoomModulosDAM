package com.mpd.pmdm.practicaroommodulos.data

import com.mpd.pmdm.practicaroommodulos.data.database.Module
import com.mpd.pmdm.practicaroommodulos.data.database.ModuleDao
import com.mpd.pmdm.practicaroommodulos.data.database.ModuleDatabase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class AppRepository @Inject constructor(private val moduleDatabase: ModuleDatabase) {

    private val moduleDao = moduleDatabase.moduleDao()
    val allModules = moduleDao.getAllModules()


    suspend fun insert(module: Module): Long {
        return moduleDao.insert(module)
    }


    suspend fun clearAll() {
        moduleDao.clearAll()
    }

}