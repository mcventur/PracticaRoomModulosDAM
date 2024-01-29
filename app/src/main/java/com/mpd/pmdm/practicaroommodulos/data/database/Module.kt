package com.mpd.pmdm.practicaroommodulos.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "module")
data class Module(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val credits: Byte
)
