package com.mpd.pmdm.practicaroommodulos.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.mpd.pmdm.practicaroommodulos.R
import com.mpd.pmdm.practicaroommodulos.core.MandatoryFieldException
import com.mpd.pmdm.practicaroommodulos.data.AppRepository
import com.mpd.pmdm.practicaroommodulos.data.database.Ciclo
import com.mpd.pmdm.practicaroommodulos.data.database.Module
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ModulosViewModel(private val appRepository: AppRepository): ViewModel() {
    val allModules: LiveData<List<Module>> = appRepository.allModules

    val allCiclos = appRepository.allCiclos
    val allCiclosName: LiveData<List<String>> = allCiclos.map { arrayCiclos ->
        arrayCiclos.map { it.name }
    }
    var selectedCiclo: Ciclo? = null

    fun updateSelectedCiclo(cicloName: String?){
        selectedCiclo = allCiclos.value?.find {
            it.name == cicloName
        }
    }

    suspend fun insert(moduleName: String, moduleCredits: Byte): Long{
        if(selectedCiclo != null){
            return viewModelScope.async {
                val module = Module(name = moduleName, credits = moduleCredits, cicloId = selectedCiclo!!.id)
                val id = appRepository.insert(module)
                id
            }.await()
        }
        else {
            //Esto no es necesario si se hacen bien las cosas en al fragment, pero no está de más
            throw MandatoryFieldException(R.string.error_mandatory)
        }

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