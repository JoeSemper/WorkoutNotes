package com.joesemper.workoutnotes.data.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
//        ForeignKey(
//            entity = DatabaseExercise::class,
//            childColumns = ["exerciseId"],
//            parentColumns = ["id"]
//        ),
        ForeignKey(
            entity = DatabaseWorkout::class,
            childColumns = ["workoutId"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class DatabaseSet(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo val exerciseId: Long = 0,
    @ColumnInfo val workoutId: Long = 0,
    @ColumnInfo val weight: Int = 0,
    @ColumnInfo val repetitions: Int = 0,
    @ColumnInfo val sets: Int = 0
)