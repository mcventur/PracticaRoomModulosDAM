package com.mpd.pmdm.practicaroommodulos.data.database

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Ciclo::class,
            parentColumns = ["id"],
            childColumns = ["idCiclo"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )]
)
data class Module(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val credits: Byte,
    //Este añadido es válido para automigración
    @ColumnInfo(defaultValue = "1") val curso: Byte,
    @Nullable val abreviatura: String? = null,
    @ColumnInfo(defaultValue = "1") val idCiclo: Int = 1
)

@Entity
data class Ciclo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @Nullable val abreviatura: String? = null,
    val name: String
)

data class CiclosConModulos(
    @Embedded val ciclo: Ciclo,
    @Relation(
        parentColumn = "id",
        entityColumn = "idCiclo"
    )
    val modulosCiclo: List<Module>
)








