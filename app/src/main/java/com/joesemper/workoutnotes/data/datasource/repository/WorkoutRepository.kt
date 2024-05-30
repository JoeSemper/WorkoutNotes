package com.joesemper.workoutnotes.data.datasource.repository

import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExercise
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseProgram
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseSet
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkout
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkoutWithSets
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getAllPrograms(): Flow<List<DatabaseProgram>>
    fun getAllExercises(): Flow<List<DatabaseExercise>>
    suspend fun getExerciseByTitle(exercise: String): DatabaseExercise?
    fun getAllWorkouts(): Flow<List<DatabaseWorkout>>
    fun getWorkoutById(workoutId: Long): Flow<DatabaseWorkout>
    fun getWorkoutWithSetsById(workoutId: Long): Flow<DatabaseWorkoutWithSets>
    fun getAllSets(): Flow<List<DatabaseSet>>
    fun getSetsForWorkout(workoutId: Long): Flow<List<DatabaseSet>>
    suspend fun insertProgram(program: DatabaseProgram)
    suspend fun insertWorkout(workout: DatabaseWorkout): Long
    suspend fun insertSets(sets: List<DatabaseSet>)
    suspend fun insertExercise(exercise: DatabaseExercise)
    suspend fun insertExercises(exercises: List<DatabaseExercise>)
    suspend fun updateProgram(program: DatabaseProgram)
    suspend fun updateWorkout(workout: DatabaseWorkout)
    suspend fun updateSet(set: DatabaseSet)
    suspend fun updateExercise(exercise: DatabaseExercise)
    suspend fun deleteProgram(id: Long)
    suspend fun deleteSet(id: Long)
    suspend fun deleteWorkout(id: Long)
    suspend fun deleteExercise(id: Long)
}