package com.mpd.pmdm.practicaroommodulos

import android.app.Application
import com.mpd.pmdm.practicaroommodulos.data.AppRepository
import com.mpd.pmdm.practicaroommodulos.data.database.ModuleDatabase

class ModulesApp: Application() {
    private val moduleDatabase: ModuleDatabase by lazy{
        ModuleDatabase.getDatabase(this)
    }

    val appRepository: AppRepository by lazy{
        AppRepository(moduleDatabase.moduleDao())
    }
}