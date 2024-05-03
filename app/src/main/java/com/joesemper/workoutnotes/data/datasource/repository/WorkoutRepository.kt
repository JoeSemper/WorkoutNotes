package com.joesemper.workoutnotes.data.datasource.repository

import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExercise
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseProgram
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseSet
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getAllPrograms(): Flow<List<DatabaseProgram>>
    fun getAllExercises(): Flow<List<DatabaseExercise>>
    fun getAllWorkouts(): Flow<List<DatabaseWorkout>>
    fun getAllSets(): Flow<List<DatabaseSet>>
    suspend fun insertProgram(program: DatabaseProgram)
    suspend fun insertWorkout(workout: DatabaseWorkout)
    suspend fun insertSets(sets: List<DatabaseSet>)
    suspend fun insertExercises(exercises: List<DatabaseProgram>)
    suspend fun updateProgram(program: DatabaseProgram)
    suspend fun updateWorkout(workout: DatabaseWorkout)
    suspend fun updateSet(set: DatabaseSet)
    suspend fun updateExercise(exercise: DatabaseExercise)
    suspend fun deleteProgram(id: Int)
    suspend fun deleteSet(id: Int)
    suspend fun deleteWorkout(id: Int)
    suspend fun deleteExercise(id: Int)
}