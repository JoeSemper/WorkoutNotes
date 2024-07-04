package com.joesemper.workoutnotes.data.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joesemper.workoutnotes.data.datasource.room.dao.DatabaseDao
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExerciseType
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExercise
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseProgram
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseSet
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkout

@Database(
    entities = [
        DatabaseExerciseType::class,
        DatabaseExercise::class,
        DatabaseProgram::class,
        DatabaseSet::class,
        DatabaseWorkout::class
    ],
    version = 1
)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun databaseDao() : DatabaseDao
}