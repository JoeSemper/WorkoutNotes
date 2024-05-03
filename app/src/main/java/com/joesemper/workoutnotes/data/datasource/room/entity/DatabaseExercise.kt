package com.joesemper.workoutnotes.data.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseExercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val title: String = ""
)