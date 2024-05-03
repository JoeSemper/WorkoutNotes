package com.joesemper.workoutnotes.data.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
//    foreignKeys = [
//        ForeignKey(
//            entity = DatabaseProgram::class,
//            childColumns = ["programId"],
//            parentColumns = ["id"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
)

data class DatabaseWorkout(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo val programId: Long = 0,
    @ColumnInfo val title: String = "",
    @ColumnInfo val description: String = "",
)