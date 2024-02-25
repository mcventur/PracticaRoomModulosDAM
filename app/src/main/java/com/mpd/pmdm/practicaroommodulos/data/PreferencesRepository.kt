package com.mpd.pmdm.practicaroommodulos.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class PreferencesRepository(val dataStore: DataStore<Preferences>) {

    //Definimos nuestras claves tipadas en un objeto privado
    private object PreferencesKeys {
        val DISPLAY_ID_FIELD = booleanPreferencesKey("display_id_field")
        val NIGHT_MODE = booleanPreferencesKey("night_mode")
    }

    companion object {
        val TAG = "PreferencesRepository"
    }

    //LECTURA DE DATOS DEL DATASTORE
    //Exponemos un flow de una instancia personalizada que encapsula todas nuestras preferencias
    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                exception.printStackTrace()
            }
        }
        .map { preferences ->
            val displayIdField = preferences[PreferencesKeys.DISPLAY_ID_FIELD] ?: true
            val nightMode = preferences[PreferencesKeys.NIGHT_MODE] ?: false
            UserPreferences(displayIdField, nightMode)
        }

    //Exponemos un flow con el valor de una clave específica (en este caso, display_id_field)
    val displayIdFieldFlow: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                exception.printStackTrace()
            }
        }
        .map { preferencias ->
            preferencias[PreferencesKeys.DISPLAY_ID_FIELD] ?: true
        }

    //ESCRITURA DE DATOS DEL DATASTORE
    suspend fun setDisplayIdField(displayIdField: Boolean) {
        try {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.DISPLAY_ID_FIELD] = displayIdField
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //En este caso, simplemenmte cambia el valor por el valor contrario
    suspend fun toogleNightMode() {
        try {
            dataStore.edit { preferences ->
                val valorActual = preferences[PreferencesKeys.NIGHT_MODE] ?: false
                Log.d(TAG, "Conmutando Night Mode. Valor actual $valorActual")
                preferences[PreferencesKeys.NIGHT_MODE] = !valorActual
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}