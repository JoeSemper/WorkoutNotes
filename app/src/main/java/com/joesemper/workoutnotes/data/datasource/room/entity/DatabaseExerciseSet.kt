package com.joesemper.workoutnotes.data.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
//    foreignKeys = [
//        ForeignKey(
//            entity = DatabaseWorkout::class,
//            childColumns = ["workoutId"],
//            parentColumns = ["id"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
)
data class DatabaseExerciseSet(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo val workoutId: Long = 0,
    @ColumnInfo val exerciseId: Long = 0
)