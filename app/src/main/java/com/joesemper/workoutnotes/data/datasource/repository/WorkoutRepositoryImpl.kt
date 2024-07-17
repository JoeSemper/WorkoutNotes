package com.joesemper.workoutnotes.data.datasource.repository

import com.joesemper.workoutnotes.data.datasource.room.dao.DatabaseDao
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExerciseType
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExercise
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseProgram
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseSet
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkout
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val databaseDao: DatabaseDao
) : WorkoutRepository {

    override fun getAllPrograms(): Flow<List<DatabaseProgram>> = databaseDao.getAllPrograms()

    override fun getAllExerciseTypes(): Flow<List<DatabaseExerciseType>> =
        databaseDao.getAllExerciseTypes()

    override suspend fun getExerciseTypeByTitle(exerciseType: String) =
        databaseDao.getExerciseTypeByTitle(exerciseType)

    override fun getAllWorkouts(): Flow<List<DatabaseWorkout>> = databaseDao.getAllWorkouts()

    override fun getWorkoutById(workoutId: Long) = databaseDao.getWorkoutById(workoutId)

    override fun getWorkoutWithExercisesById(workoutId: Long) =
        databaseDao.getWorkoutWithExercisesById(workoutId)

    override fun getExerciseWithExerciseType(exerciseId: Long) =
        databaseDao.getExerciseWithExerciseType(exerciseId)

    override fun getSetsForExercise(exerciseId: Long) = databaseDao.getSetsForExercise(exerciseId)

    override fun getAllSets(): Flow<List<DatabaseSet>> = databaseDao.getAllSets()

    override fun getExercisesForWorkout(workoutId: Long) =
        databaseDao.getExercisesForWorkout(workoutId)

    override suspend fun getExerciseById(exerciseId: Long) =
        databaseDao.getExerciseById(exerciseId)

    override suspend fun getExerciseIndexById(exerciseId: Long): Int =
        databaseDao.getExerciseIndexById(exerciseId) ?: 0

    override suspend fun insertProgram(program: DatabaseProgram) =
        databaseDao.insertProgram(program)

    override suspend fun insertWorkout(workout: DatabaseWorkout) =
        databaseDao.insertWorkout(workout)

    override suspend fun insertSets(sets: List<DatabaseSet>) = databaseDao.insertSets(sets)

    override suspend fun insertExerciseType(exerciseType: DatabaseExerciseType) =
        databaseDao.insertExerciseType(exerciseType)

    override suspend fun insertExerciseTypes(exerciseTypes: List<DatabaseExerciseType>) =
        databaseDao.insertExerciseTypes(exerciseTypes)

    override suspend fun insertExercise(exercise: DatabaseExercise) =
        databaseDao.insertExercise(exercise)

    override suspend fun updateProgram(program: DatabaseProgram) =
        databaseDao.updateProgram(program)

    override suspend fun updateWorkout(workout: DatabaseWorkout) =
        databaseDao.updateWorkout(workout)

    override suspend fun updateSet(set: DatabaseSet) = databaseDao.updateSet(set)

    override suspend fun updateExerciseType(exerciseType: DatabaseExerciseType) =
        databaseDao.updateExerciseType(exerciseType)

    override suspend fun updateExercise(exercise: DatabaseExercise) =
        databaseDao.updateExercise(exercise)

    override suspend fun deleteProgram(id: Long) = databaseDao.deleteProgram(id)

    override suspend fun deleteSet(id: Long) = databaseDao.deleteSet(id)

    override suspend fun deleteWorkout(id: Long) = databaseDao.deleteWorkout(id)

    override suspend fun deleteExerciseType(id: Long) = databaseDao.deleteExerciseType(id)

    override suspend fun deleteExerciseSet(id: Long) = databaseDao.deleteExerciseSet(id)

    override suspend fun getLastExerciseIndex(workoutId: Long) =
        databaseDao.getLastExerciseIndex(workoutId) ?: 0

    override suspend fun getLastSetIndexInExercise(exerciseId: Long) =
        databaseDao.getLastSetIndexInExercise(exerciseId) ?: 0

    override suspend fun deleteLastSetInExercise(exerciseId: Long) =
        databaseDao.deleteLastSetInExercise(exerciseId)

    override suspend fun deleteAllSetsInExercise(exerciseId: Long) =
        databaseDao.deleteAllSetsInExercise(exerciseId)
}