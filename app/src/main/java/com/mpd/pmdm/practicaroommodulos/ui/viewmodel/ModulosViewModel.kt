package com.mpd.pmdm.practicaroommodulos.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.mpd.pmdm.practicaroommodulos.core.ModuleApp
import com.mpd.pmdm.practicaroommodulos.data.ModulesRepository
import com.mpd.pmdm.practicaroommodulos.data.PreferencesRepository
import com.mpd.pmdm.practicaroommodulos.data.database.Module
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ModulosViewModel(
    private val appRepository: ModulesRepository,
    private val prefsRepo: PreferencesRepository
) : ViewModel() {

    //Getters del repositorio de módulos
    val allModules: LiveData<List<Module>> = appRepository.allModules

    //Getters de preferencias
    val preferencias = prefsRepo.userPreferencesFlow.asLiveData()

    //Preferencias específicas que necesitamos para la ordenación de la lista
    val sortField = prefsRepo.sortField.asLiveData()
    val sortAsc = prefsRepo.sortAsc.asLiveData()

    //Versión de allModules ordenada
    val allModulesSorted = MediatorLiveData<List<Module>>()

    init {
        allModulesSorted.addSource(allModules) { updateListaModulos() }
        allModulesSorted.addSource(sortField) { updateListaModulos() }
        allModulesSorted.addSource(sortAsc) { updateListaModulos() }
    }

    /**
     * Actualiza el mediatorLiveData allModulesSorted cada vez que cambia la lista o alguna preferencia de ordenación
     */
    private fun updateListaModulos() {
        allModulesSorted.value = allModules.value
        when (sortField.value) {
            SortFields.NAME.toString() -> allModulesSorted.value =
                allModulesSorted.value?.sortedByDescending { it.name }

            SortFields.CREDITS.toString() -> allModulesSorted.value =
                allModulesSorted.value?.sortedByDescending { it.credits }

            else -> allModulesSorted.value = allModulesSorted.value?.sortedByDescending { it.id }
        }
        if (sortAsc.value == true) allModulesSorted.value = allModulesSorted.value?.reversed()

    }


    suspend fun insert(moduleName: String, moduleCredits: Byte): Long {
        return viewModelScope.async {
            val module = Module(name = moduleName, credits = moduleCredits)
            val id = appRepository.insert(module)
            id
        }.await()

    }

    fun clearAll() {
        viewModelScope.launch {
            appRepository.clearAll()
        }
    }


    fun setDisplayIdPref(displayIdPref: Boolean) {
        viewModelScope.launch {
            prefsRepo.setDisplayIdField(displayIdPref)
        }
    }

    fun toogleNightMode() {
        viewModelScope.launch {
            prefsRepo.toogleNightMode()
        }
    }

    fun setSortField(sortField: SortFields) {
        viewModelScope.launch {
            prefsRepo.setSortField(sortField)
        }
    }

    fun setSortAsc(sortAsc: Boolean) {
        viewModelScope.launch {
            prefsRepo.setSortAsc(sortAsc)
        }
    }


}

class ModulosViewModelFactory() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    /*    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ModulosViewModel::class.java))
                return ModulosViewModel(repository) as T
            throw IllegalArgumentException("Error al instanciar el ViewModel")
        }*/

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val app = checkNotNull(extras[APPLICATION_KEY]) as ModuleApp

        if (modelClass.isAssignableFrom(ModulosViewModel::class.java))
            return ModulosViewModel(app.appRepository, app.dataStoreRepo) as T
        throw IllegalArgumentException("Error al instanciar el ViewModel")
    }
}