package com.mpd.pmdm.practicaroommodulos.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.mpd.pmdm.practicaroommodulos.R
import com.mpd.pmdm.practicaroommodulos.core.MandatoryFieldException
import com.mpd.pmdm.practicaroommodulos.core.ModuleApp
import com.mpd.pmdm.practicaroommodulos.data.AppRepository
import com.mpd.pmdm.practicaroommodulos.data.database.Ciclo
import com.mpd.pmdm.practicaroommodulos.data.database.Module
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ModulosViewModel(private val appRepository: AppRepository) : ViewModel() {
    val allModules: LiveData<List<Module>> = appRepository.allModules

    val allCiclos = appRepository.allCiclos
    val allCiclosName: LiveData<List<String>> = allCiclos.map { arrayCiclos ->
        arrayCiclos.map { it.name }
    }

    //Almacenará el ciclo seleccionado en el formulario de edición de módulo
    private var selectedCiclo: Ciclo? = null

    fun updateSelectedCiclo(cicloName: String?) {
        selectedCiclo = allCiclos.value?.find {
            it.name == cicloName
        }
    }

    fun getModule(moduleId: Long) = appRepository.getModulo(moduleId)
    fun updateModulo(
        moduleId: Long,
        moduleName: String,
        moduleCredits: Byte,
        moduleCiclo: Long,
        moduleCurso: Byte,
        moduloAbreviatura: String
    ) {
        val cicloIdToSave = if (moduleCiclo != 0L) moduleCiclo else selectedCiclo?.id

        //Aún así, por si fuera null, comprobamos
        if (cicloIdToSave != null) {
            viewModelScope.launch {
                appRepository.updateModulo(moduleId, moduleName, moduleCredits, cicloIdToSave, moduleCurso, moduloAbreviatura)
            }
        } else {
            //Esto no es necesario si se hacen bien las cosas en al fragment, pero no está de más
            throw MandatoryFieldException(R.string.error_mandatory)
        }
    }

    suspend fun insertModule(
        cicloId: Long = 0L,
        moduleName: String,
        moduleCredits: Byte,
        moduleCurso: Byte,
        moduleAbreviatura: String
    ): Long {

        //El id de ciclo puede provenir de la propia llamada, o haberse seteado previamente en el formulario de edición de módulo
        //Si viene en la llamada, usamos ese
        val cicloIdToSave = if (cicloId != 0L) cicloId else selectedCiclo?.id

        //Aún así, por si fuera null, comprobamos
        if (cicloIdToSave != null) {
            return viewModelScope.async {
                val id = appRepository.insertModulo(moduleName, moduleCredits, cicloIdToSave, moduleCurso, moduleAbreviatura)
                id
            }.await()
        } else {
            //Esto no es necesario si se hacen bien las cosas en al fragment, pero no está de más
            throw MandatoryFieldException(R.string.error_mandatory)
        }

    }

    fun insertCiclo(cicloName: String, cicloAbreviatura: String) {
        //En este caso no recupero el id
        viewModelScope.launch {
            appRepository.insertCiclo(cicloName, cicloAbreviatura)
        }
    }

    fun getCiclo(cicloId: Long): LiveData<Ciclo> {
        return appRepository.getCiclo(cicloId)
    }

    fun updateCiclo(cicloId: Long, cicloName: String, cicloAbreviatura: String) {
        viewModelScope.launch {
            appRepository.updateCiclo(cicloId, cicloName, cicloAbreviatura)
        }
    }

    fun deleteCiclo(cicloId: Long) {
        viewModelScope.launch {
            appRepository.deleteCiclo(cicloId)
        }
    }

    fun getModulesOfCiclo(cicloid: Long): LiveData<List<Module>> {
        return appRepository.getModulesOfCiclo(cicloid)
    }


    fun clearAll() {
        viewModelScope.launch {
            appRepository.clearAll()
        }
    }
}

class ModulosViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val app = checkNotNull(extras[APPLICATION_KEY]) as ModuleApp
        return ModulosViewModel(app.appRepository) as T
    }
}