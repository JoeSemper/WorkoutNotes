package com.joesemper.workoutnotes.data.datasource.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExercise
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseProgram
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseSet
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkout
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkoutWithSets
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {
    @Query("SELECT * FROM DatabaseProgram")
    fun getAllPrograms(): Flow<List<DatabaseProgram>>

    @Query("SELECT * FROM DatabaseExercise")
    fun getAllExercises(): Flow<List<DatabaseExercise>>

    @Query("SELECT * FROM DatabaseWorkout")
    fun getAllWorkouts(): Flow<List<DatabaseWorkout>>

    @Query("SELECT * FROM DatabaseWorkout WHERE id = :workoutId")
    fun getWorkoutById(workoutId: Long): Flow<DatabaseWorkout>

    @Query("SELECT * FROM DatabaseWorkout WHERE id = :workoutId")
    fun getWorkoutWithSetsById(workoutId: Long): Flow<DatabaseWorkoutWithSets>

    @Query("SELECT * FROM DatabaseSet")
    fun getAllSets(): Flow<List<DatabaseSet>>

    @Query("SELECT * FROM DatabaseSet WHERE workoutId = :workoutId")
    fun getSetsForWorkout(workoutId: Long): Flow<List<DatabaseSet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgram(program: DatabaseProgram)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: DatabaseWorkout): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSets(sets: List<DatabaseSet>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<DatabaseProgram>)

    @Update(entity = DatabaseProgram::class)
    suspend fun updateProgram(program: DatabaseProgram)

    @Update(entity = DatabaseWorkout::class)
    suspend fun updateWorkout(workout: DatabaseWorkout)

    @Update(entity = DatabaseSet::class)
    suspend fun updateSet(set: DatabaseSet)

    @Update(entity = DatabaseExercise::class)
    suspend fun updateExercise(exercise: DatabaseExercise)

    @Query("DELETE FROM DatabaseProgram WHERE id =:id")
    suspend fun deleteProgram(id: Long)

    @Query("DELETE FROM DatabaseSet WHERE id =:id")
    suspend fun deleteSet(id: Long)

    @Query("DELETE FROM DatabaseWorkout WHERE id =:id")
    suspend fun deleteWorkout(id: Long)

    @Query("DELETE FROM DatabaseExercise WHERE id =:id")
    suspend fun deleteExercise(id: Long)
}