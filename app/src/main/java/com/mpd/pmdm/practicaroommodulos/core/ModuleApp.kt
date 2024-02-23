package com.mpd.pmdm.practicaroommodulos.core

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mpd.pmdm.practicaroommodulos.data.ModulesRepository
import com.mpd.pmdm.practicaroommodulos.data.PreferencesRepository
import com.mpd.pmdm.practicaroommodulos.data.database.ModuleDatabase

const val MODULE_PREFERENCES = "preferencias_modulos"
private val Context.dataStorePreferencias by preferencesDataStore(
    name = MODULE_PREFERENCES
)

class ModuleApp: Application() {
    private val moduleDatabase: ModuleDatabase by lazy { ModuleDatabase.getDataBase(this) }
    val appRepository: ModulesRepository by lazy { ModulesRepository(moduleDatabase.moduleDao()) }
    lateinit var dataStoreRepo: PreferencesRepository

    override fun onCreate() {
        super.onCreate()
        dataStoreRepo = PreferencesRepository(dataStorePreferencias)
    }
}