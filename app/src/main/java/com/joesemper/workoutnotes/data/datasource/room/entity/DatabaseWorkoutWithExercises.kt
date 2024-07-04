package com.joesemper.workoutnotes.data.datasource.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DatabaseWorkoutWithExercises (
    @Embedded val workout: DatabaseWorkout,
    @Relation(
        entity = DatabaseExercise::class,
        parentColumn = "id",
        entityColumn = "workoutId"
    )
    val sets: List<DatabaseExerciseWithExerciseType>
)