package com.mpd.pmdm.practicaroommodulos.core

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mpd.pmdm.practicaroommodulos.data.DatabaseRepository
import com.mpd.pmdm.practicaroommodulos.data.ListModulesPreferencesRepository
import com.mpd.pmdm.practicaroommodulos.data.database.ModuleDatabase



class ModuleApp: Application() {
    private val moduleDatabase: ModuleDatabase by lazy { ModuleDatabase.getDataBase(this) }
    val appRepository: DatabaseRepository by lazy { DatabaseRepository(moduleDatabase.moduleDao())}
    val listPreferences = ListModulesPreferencesRepository(this)
}