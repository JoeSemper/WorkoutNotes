package com.joesemper.workoutnotes.data.datasource.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DatabaseExerciseWithExerciseType (
    @Embedded val exercise: DatabaseExercise,
    @Relation(
        parentColumn = "exerciseTypeId",
        entityColumn = "id"
    )
    val exerciseType: DatabaseExerciseType
)