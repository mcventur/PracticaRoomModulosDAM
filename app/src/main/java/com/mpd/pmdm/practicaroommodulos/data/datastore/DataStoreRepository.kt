package com.mpd.pmdm.practicaroommodulos.data.datastore

/**
 * Interfaz genérica que abstrae las operaciones generales de escribir y leer distintos tipos de datos
 * en un Preference Datastore
 */
interface DataStoreRepository {
    suspend fun putString(key: String, value: String)
    suspend fun putInt(key: String, value: Int)
    suspend fun getString(key: String): String?
    suspend fun getInt(key: String): Int?
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String): Boolean?
}