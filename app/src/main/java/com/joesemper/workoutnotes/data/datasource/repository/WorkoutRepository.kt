package com.joesemper.workoutnotes.data.datasource.repository

import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExerciseType
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExercise
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseProgram
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseSet
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkout
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkoutWithExercises
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getAllPrograms(): Flow<List<DatabaseProgram>>
    fun getAllExerciseTypes(): Flow<List<DatabaseExerciseType>>
    suspend fun getExerciseTypeByTitle(exerciseType: String): DatabaseExerciseType?
    fun getAllWorkouts(): Flow<List<DatabaseWorkout>>
    fun getWorkoutById(workoutId: Long): Flow<DatabaseWorkout>
    fun getWorkoutWithExercisesById(workoutId: Long): Flow<DatabaseWorkoutWithExercises>
    fun getAllSets(): Flow<List<DatabaseSet>>
    fun getExercisesForWorkout(workoutId: Long): Flow<List<DatabaseExercise>>
    suspend fun insertExercise(exercise: DatabaseExercise): Long
    suspend fun getExerciseById(exerciseId: Long): DatabaseExercise?
    suspend fun insertProgram(program: DatabaseProgram)
    suspend fun insertWorkout(workout: DatabaseWorkout): Long
    suspend fun insertSets(sets: List<DatabaseSet>)
    suspend fun insertExerciseType(exerciseType: DatabaseExerciseType): Long
    suspend fun insertExerciseTypes(exerciseTypes: List<DatabaseExerciseType>)
    suspend fun updateProgram(program: DatabaseProgram)
    suspend fun updateWorkout(workout: DatabaseWorkout)
    suspend fun updateSet(set: DatabaseSet)
    suspend fun updateExerciseType(exerciseType: DatabaseExerciseType)
    suspend fun deleteProgram(id: Long)
    suspend fun deleteSet(id: Long)
    suspend fun deleteWorkout(id: Long)
    suspend fun deleteExerciseType(id: Long)
    suspend fun deleteExerciseSet(id: Long)
    suspend fun getLastExerciseSetIndex(workoutId: Long): Int
}