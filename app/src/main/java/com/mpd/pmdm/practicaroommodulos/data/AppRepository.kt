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

class AppRepository(private val moduleDao: ModuleDao, private val dataStoreListado: DataStore<Preferences>) {

    val allModules = moduleDao.getAllModules()

    private companion object {
        //Definimos una clave que usaremos para referenciar más adelante un valor
        val DISPLAY_ID_COLUMN = booleanPreferencesKey("display_id_column")
        val SORT_FIELD = stringPreferencesKey("sort_field")
        val SORT_ASC =  booleanPreferencesKey("sort_type")
    }

    /**
     * Escribe un nuevo valor en la preferencia [DISPLAY_ID_COLUMN]
     */
    suspend fun saveDisplayIdOnList(displayId: Boolean){
        dataStoreListado.edit {preferences ->
            preferences[DISPLAY_ID_COLUMN] = displayId
        }
    }

    suspend fun saveSortField(sortField: String){
        dataStoreListado.edit {
            it[SORT_FIELD] = sortField
        }
    }

    suspend fun saveSortAsc(sortAsc: Boolean){
        dataStoreListado.edit {preferences ->
            preferences[SORT_ASC] = sortAsc
        }
    }



    val displayIdOnList: Flow<Boolean> = dataStoreListado.data
        .catch {
            if(it is IOException){
                emit(emptyPreferences())
            } else throw it
        }
        .map{
            preferences ->
            preferences[DISPLAY_ID_COLUMN] ?: true
        }

    val sortField: Flow<String> = dataStoreListado.data
        .map {
            it[SORT_FIELD] ?: "id"
        }

    val sortAsc: Flow<Boolean> = dataStoreListado.data.map { it[SORT_ASC] ?: false }

    suspend fun insert(module: Module): Long {
        return moduleDao.insert(module)
    }


    suspend fun clearAll() {
        moduleDao.clearAll()
    }

}