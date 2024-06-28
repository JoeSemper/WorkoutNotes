package com.joesemper.workoutnotes.data.datasource.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DatabaseExerciseSetWithExercise (
    @Embedded val exerciseSet: DatabaseExerciseSet,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "id"
    )
    val exercise: DatabaseExercise
)