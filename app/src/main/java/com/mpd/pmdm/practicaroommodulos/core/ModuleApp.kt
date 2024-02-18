package com.mpd.pmdm.practicaroommodulos.core

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mpd.pmdm.practicaroommodulos.data.AppRepository
import com.mpd.pmdm.practicaroommodulos.data.database.ModuleDatabase

private const val MODULE_LIST_PREFERENCES_NAME = "ModuleListPreferences"
private val Context.dataStoreListado: DataStore<Preferences> by preferencesDataStore(name = MODULE_LIST_PREFERENCES_NAME)

class ModuleApp: Application() {
    private val moduleDatabase: ModuleDatabase by lazy { ModuleDatabase.getDataBase(this) }
    val appRepository: AppRepository by lazy { AppRepository(moduleDatabase.moduleDao(), dataStoreListado)}
}