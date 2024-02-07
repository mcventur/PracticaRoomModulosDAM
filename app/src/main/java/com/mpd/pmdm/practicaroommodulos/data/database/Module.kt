package com.mpd.pmdm.practicaroommodulos.data.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "module",
    foreignKeys = [
        ForeignKey(
            entity = Ciclo::class,
            parentColumns = ["id"],
            childColumns = ["cicloId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Module(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val credits: Byte,
    //añadimos dos campos a la tabla
    @ColumnInfo(defaultValue = "1") val cicloId: Long = 1,
    @ColumnInfo(defaultValue = "1") val curso: Byte = 1
)

@Entity(tableName = "ciclo")
data class Ciclo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)

/*
Curso-Módulos es una relación 1:N
Por tanto, aplicando lo que sabemos de bases de datos, incluimos el id de la tabla de cardinalidad 1
en la tabla de cardinalidad n

Para representar esto en Room, se crea una dataClass intermedia, pero no será una tabla (@Entity)
de Room.

Más info: https://developer.android.com/training/data-storage/room/relationships#one-to-many
 */
data class CiclosWithModules(
    //@Embebed significa que incluirá en esta clase todos los campos de la clase embebida
    @Embedded val ciclo: Ciclo,

    //Añadimos un campo que contendrá la lista de Modulos asociados al Ciclo
    //Si la relación fuese 1:1, en lugar de una List, sería un único registro
    @Relation(
        parentColumn = "id", //El id de ciclo de la tabla Module
        entityColumn = "cicloId" //El campo dentro de Module al que corresponde el parentColumn
    )
    val modulesList: List<Module>
)


