package com.mpd.pmdm.practicaroommodulos.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mpd.pmdm.practicaroommodulos.data.AppRepository
import com.mpd.pmdm.practicaroommodulos.data.database.Module
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModulosViewModel @Inject constructor(private val appRepository: AppRepository): ViewModel() {
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