package com.mpd.pmdm.practicaroommodulos.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mpd.pmdm.practicaroommodulos.data.database.Module
import com.mpd.pmdm.practicaroommodulos.data.database.ModuleDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DatabaseRepository(private val moduleDao: ModuleDao) {

    val allModules = moduleDao.getAllModules()

    private companion object {
        //Definimos una clave que usaremos para referenciar más adelante un valor
        val DISPLAY_ID_COLUMN = booleanPreferencesKey("display_id_column")
        val SORT_FIELD = stringPreferencesKey("sort_field")
        val SORT_ASC =  booleanPreferencesKey("sort_type")
    }


    suspend fun clearAll() {
        moduleDao.clearAll()
    }

}