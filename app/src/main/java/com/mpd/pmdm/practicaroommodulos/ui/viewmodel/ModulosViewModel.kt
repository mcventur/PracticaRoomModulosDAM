package com.mpd.pmdm.practicaroommodulos.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mpd.pmdm.practicaroommodulos.data.DatabaseRepository
import com.mpd.pmdm.practicaroommodulos.data.database.Module
import com.mpd.pmdm.practicaroommodulos.data.datastore.DataStoreRepository
import com.mpd.pmdm.practicaroommodulos.data.datastore.PREF_DISPLAY_ID_COLUMN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ModulosViewModel(private val appRepository: DatabaseRepository, private val listPreferences: DataStoreRepository): ViewModel() {
    val allModules: LiveData<List<Module>> = appRepository.allModules


    val displayIdPreference = viewModelScope.async {
        listPreferences.getBoolean(PREF_DISPLAY_ID_COLUMN)
    }.await()

    fun setDisplayIdPreference(displayId: Boolean){
        viewModelScope.launch(Dispatchers.IO) { appRepository.saveDisplayIdOnList(displayId) }
    }

    val sortField = appRepository.sortField
    fun setSortField(sortField: String){
        viewModelScope.launch(Dispatchers.IO) { appRepository.saveSortField(sortField) }
    }

    val getSortAsc = appRepository.sortAsc
    fun setSortAsc(sortAsc: Boolean){
        viewModelScope.launch { appRepository.saveSortAsc(sortAsc) }
    }

    suspend fun insert(moduleName: String, moduleCredits: Byte): Long{
        return viewModelScope.async {
            val module = Module(name = moduleName, credits = moduleCredits)
            val id = appRepository.insert(module)
            id
        }.await()
    }

    fun clearAll(){
        viewModelScope.launch {
            appRepository.clearAll()
        }
    }
}

class ModulosViewModelFactory(private val repository: DatabaseRepository): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ModulosViewModel::class.java))
            return ModulosViewModel(repository) as T
        throw IllegalArgumentException("Error al instanciar el ViewModel")
    }
}