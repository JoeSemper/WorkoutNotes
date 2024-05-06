package com.joesemper.workoutnotes.data.datasource.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DatabaseWorkoutWithSets (
    @Embedded val workout: DatabaseWorkout,
    @Relation(
        parentColumn = "id",
        entityColumn = "workoutId"
    )
    val sets: List<DatabaseSet>
)