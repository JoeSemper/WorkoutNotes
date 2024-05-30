package com.joesemper.workoutnotes.data.datasource.repository

import com.joesemper.workoutnotes.data.datasource.room.dao.DatabaseDao
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

    override fun getAllExercises(): Flow<List<DatabaseExercise>> = databaseDao.getAllExercises()

    override suspend fun getExerciseByTitle(exercise: String) =
        databaseDao.getExerciseByTitle(exercise)

    override fun getAllWorkouts(): Flow<List<DatabaseWorkout>> = databaseDao.getAllWorkouts()

    override fun getWorkoutById(workoutId: Long) = databaseDao.getWorkoutById(workoutId)

    override fun getWorkoutWithSetsById(workoutId: Long) =
        databaseDao.getWorkoutWithSetsById(workoutId)

    override fun getAllSets(): Flow<List<DatabaseSet>> = databaseDao.getAllSets()

    override fun getSetsForWorkout(workoutId: Long) = databaseDao.getSetsForWorkout(workoutId)

    override suspend fun insertProgram(program: DatabaseProgram) =
        databaseDao.insertProgram(program)

    override suspend fun insertWorkout(workout: DatabaseWorkout) =
        databaseDao.insertWorkout(workout)

    override suspend fun insertSets(sets: List<DatabaseSet>) = databaseDao.insertSets(sets)

    override suspend fun insertExercise(exercise: DatabaseExercise) =
        databaseDao.insertExercise(exercise)

    override suspend fun insertExercises(exercises: List<DatabaseExercise>) =
        databaseDao.insertExercises(exercises)

    override suspend fun updateProgram(program: DatabaseProgram) =
        databaseDao.updateProgram(program)

    override suspend fun updateWorkout(workout: DatabaseWorkout) =
        databaseDao.updateWorkout(workout)

    override suspend fun updateSet(set: DatabaseSet) = databaseDao.updateSet(set)

    override suspend fun updateExercise(exercise: DatabaseExercise) =
        databaseDao.updateExercise(exercise)

    override suspend fun deleteProgram(id: Long) = databaseDao.deleteProgram(id)

    override suspend fun deleteSet(id: Long) = databaseDao.deleteSet(id)

    override suspend fun deleteWorkout(id: Long) = databaseDao.deleteWorkout(id)

    override suspend fun deleteExercise(id: Long) = databaseDao.deleteExercise(id)
}