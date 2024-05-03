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

    override fun getAllWorkouts(): Flow<List<DatabaseWorkout>> = databaseDao.getAllWorkouts()

    override fun getAllSets(): Flow<List<DatabaseSet>> = databaseDao.getAllSets()

    override suspend fun insertProgram(program: DatabaseProgram) =
        databaseDao.insertProgram(program)

    override suspend fun insertWorkout(workout: DatabaseWorkout) =
        databaseDao.insertWorkout(workout)

    override suspend fun insertSets(sets: List<DatabaseSet>) = databaseDao.insertSets(sets)

    override suspend fun insertExercises(exercises: List<DatabaseProgram>) =
        databaseDao.insertExercises(exercises)

    override suspend fun updateProgram(program: DatabaseProgram) =
        databaseDao.updateProgram(program)

    override suspend fun updateWorkout(workout: DatabaseWorkout) =
        databaseDao.updateWorkout(workout)

    override suspend fun updateSet(set: DatabaseSet) = databaseDao.updateSet(set)

    override suspend fun updateExercise(exercise: DatabaseExercise) =
        databaseDao.updateExercise(exercise)

    override suspend fun deleteProgram(id: Int) = databaseDao.deleteProgram(id)

    override suspend fun deleteSet(id: Int) = databaseDao.deleteSet(id)

    override suspend fun deleteWorkout(id: Int) = databaseDao.deleteWorkout(id)

    override suspend fun deleteExercise(id: Int) = databaseDao.deleteExercise(id)
}