package com.mpd.pmdm.practicaroommodulos.ui.viewmodel

import androidx.lifecycle.LiveData
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

    //ACCESO AL RESPOSITORIO DE MODULOS
    val allModules: LiveData<List<Module>> = appRepository.allModules

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

    //ACCESO AL REPOSITORIO DE PREFERENCIAS
    val preferencias = prefsRepo.userPreferencesFlow.asLiveData()

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