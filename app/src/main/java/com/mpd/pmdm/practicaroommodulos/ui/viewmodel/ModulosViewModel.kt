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
): ViewModel() {

    val allModules: LiveData<List<Module>> = appRepository.allModules
    val preferencias: LiveData<UserPreferences> = prefsRepo.userPreferencesFlow.asLiveData()

    //Este LiveData se actualizará cuando cambie allModules o cuando cambie alguna de las
    //preferencias de ordenación
    private val _allModulesSorted = MediatorLiveData<List<Module>>()
    val allModulesSorted: LiveData<List<Module>> = _allModulesSorted

    init{
        _allModulesSorted.addSource(allModules){
            updateSortedList()
        }

        _allModulesSorted.addSource(preferencias){
            updateSortedList()
        }
    }

    private fun updateSortedList() {
        _allModulesSorted.value = allModules.value

        _allModulesSorted.value = when(preferencias.value?.sortField){
            SortFields.NAME.toString() ->  _allModulesSorted.value?.sortedByDescending { it.name }
            SortFields.CREDITS.toString() -> _allModulesSorted.value?.sortedByDescending { it.credits }
            else -> _allModulesSorted.value?.sortedByDescending { it.id }
        }

        if(preferencias.value?.sortAsc == true){
            _allModulesSorted.value = _allModulesSorted.value?.reversed()
        }

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

    fun setDisplayIdPref(displayIdPref: Boolean){
        viewModelScope.launch {
            prefsRepo.setDisplayIdField(displayIdPref)
        }
    }

    fun toogleNightMode(){
        viewModelScope.launch {
            prefsRepo.toogleNightMode()
        }
    }

    fun setSortField(sortFields: SortFields){
        viewModelScope.launch {
            prefsRepo.setSortField(sortFields)
        }
    }

    fun setSortAsc(sortAsc: Boolean){
        viewModelScope.launch {
            prefsRepo.setSortAsc(sortAsc)
        }
    }


}

class ModulosViewModelFactory(): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
/*    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ModulosViewModel::class.java))
            return ModulosViewModel(repository) as T
        throw IllegalArgumentException("Error al instanciar el ViewModel")
    }*/

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val app = checkNotNull(extras[APPLICATION_KEY]) as ModuleApp

        if(modelClass.isAssignableFrom(ModulosViewModel::class.java))
            return ModulosViewModel(app.appRepository, app.dataStoreRepo) as T
        throw IllegalArgumentException("Error al instanciar el ViewModel")
    }
}