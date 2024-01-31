package com.mpd.pmdm.practicaroommodulos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mpd.pmdm.practicaroommodulos.data.AppRepository
import com.mpd.pmdm.practicaroommodulos.data.database.Module
import kotlinx.coroutines.launch

class ModuleViewModel(private val appRepository: AppRepository): ViewModel() {

    val allModules = appRepository.allModules

    fun insert(moduleName: String, moduleCredits: Byte){
        val newModule = Module(name = moduleName, credits = moduleCredits)
        viewModelScope.launch {
            appRepository.insert(newModule)
        }
    }

    fun clearAll(){
        viewModelScope.launch {
            appRepository.clearAll()
        }
    }
}

class ModuleViewModelFactory(private  val repository: AppRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ModuleViewModel(repository) as T
    }
}