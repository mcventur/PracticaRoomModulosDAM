package com.mpd.pmdm.practicaroommodulos.core

import android.app.Application
import com.mpd.pmdm.practicaroommodulos.data.AppRepository
import com.mpd.pmdm.practicaroommodulos.data.database.ModuleDatabase

class ModuleApp: Application() {
    private val moduleDatabase: ModuleDatabase by lazy { ModuleDatabase.getDataBase(this) }
    val appRepository: AppRepository by lazy { AppRepository(moduleDatabase.moduleDao()) }
}