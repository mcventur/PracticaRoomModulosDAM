package com.mpd.pmdm.practicaroommodulos.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mpd.pmdm.practicaroommodulos.data.AppRepository
import com.mpd.pmdm.practicaroommodulos.data.database.Module
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ModulosViewModel(private val appRepository: AppRepository): ViewModel() {
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
}

class ModulosViewModelFactory(private val repository: AppRepository): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ModulosViewModel::class.java))
            return ModulosViewModel(repository) as T
        throw IllegalArgumentException("Error al instanciar el ViewModel")
    }
}