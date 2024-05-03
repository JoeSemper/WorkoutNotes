package com.joesemper.workoutnotes.data.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseProgram(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo val title: String = "",
    @ColumnInfo val description: String = ""
)