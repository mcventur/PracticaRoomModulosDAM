package com.mpd.pmdm.practicaroommodulos.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Module(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val credits: Byte,
    //Este añadido es válido para automigración
    @ColumnInfo(defaultValue = "1") val curso: Byte
)
