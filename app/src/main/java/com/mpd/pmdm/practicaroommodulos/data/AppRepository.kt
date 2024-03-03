package com.mpd.pmdm.practicaroommodulos.data

import androidx.lifecycle.LiveData
import com.mpd.pmdm.practicaroommodulos.data.database.Ciclo
import com.mpd.pmdm.practicaroommodulos.data.database.Module
import com.mpd.pmdm.practicaroommodulos.data.database.ModuleDao

class AppRepository(private val moduleDao: ModuleDao) {

    val allModules = moduleDao.getAllModules()
    val allCiclos = moduleDao.getAllCiclos()


    suspend fun insertModulo(moduleName: String, moduleCredits: Byte, cicloId: Long, curso: Byte, abreviatura: String): Long {
        val module = Module(name = moduleName, credits = moduleCredits, cicloId = cicloId, curso = curso, abreviatura = abreviatura)
        return moduleDao.insert(module)
    }

    suspend fun updateModulo(
        moduleId: Long,
        moduleName: String,
        moduleCredits: Byte,
        cicloId: Long,
        curso: Byte,
        abreviatura: String
    ) {
        val module = Module(
            id = moduleId,
            name = moduleName,
            credits = moduleCredits,
            cicloId = cicloId,
            curso = curso,
            abreviatura = abreviatura
        )
        moduleDao.insert(module)
    }

    fun getModulo(moduleId: Long): LiveData<Module> = moduleDao.getModule(moduleId)


    suspend fun clearAll() {
        moduleDao.clearAll()
    }

    suspend fun insertCiclo(cicloName: String, cicloAbreviatura: String) {
        val ciclo = Ciclo(name = cicloName, abreviatura = cicloAbreviatura)
        moduleDao.insertCiclo(ciclo)
    }

    fun getCiclo(cicloId: Long) = moduleDao.getCiclo(cicloId)
    fun getModulesOfCiclo(cicloId: Long) = moduleDao.getModulesOfCiclo(cicloId)
    suspend fun updateCiclo(cicloId: Long, cicloName: String, cicloAbreviatura: String) {
        val ciclo = Ciclo(cicloId, cicloName, cicloAbreviatura)
        moduleDao.updateCiclo(ciclo)
    }

    //Realmente sólo necesitamos el id, que es lo que buscará Room
    //Me invento los otros datos
    suspend fun deleteCiclo(cicloId: Long) {
        val ciclo = Ciclo(cicloId, "", "")
        moduleDao.deleteCiclo(ciclo)
    }

}