package com.joesemper.workoutnotes.data.datasource.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExercise
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExerciseSet
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseProgram
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseSet
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkout
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkoutWithExerciseSets
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {
    @Query("SELECT * FROM DatabaseProgram")
    fun getAllPrograms(): Flow<List<DatabaseProgram>>

    @Query("SELECT * FROM DatabaseExercise")
    fun getAllExercises(): Flow<List<DatabaseExercise>>

    @Query("SELECT * FROM DatabaseExercise WHERE title = :exercise")
    suspend fun getExerciseByTitle(exercise: String): DatabaseExercise?

    @Query("SELECT * FROM DatabaseExerciseSet WHERE id = :exerciseSetId")
    suspend fun getExerciseSetById(exerciseSetId: Long): DatabaseExerciseSet?

    @Query("SELECT * FROM DatabaseWorkout")
    fun getAllWorkouts(): Flow<List<DatabaseWorkout>>

    @Query("SELECT * FROM DatabaseWorkout WHERE id = :workoutId")
    fun getWorkoutById(workoutId: Long): Flow<DatabaseWorkout>

    @Query("SELECT * FROM DatabaseWorkout WHERE id = :workoutId")
    fun getWorkoutWithExerciseSetsById(workoutId: Long): Flow<DatabaseWorkoutWithExerciseSets>

    @Query("SELECT * FROM DatabaseSet")
    fun getAllSets(): Flow<List<DatabaseSet>>

    @Query("SELECT * FROM DatabaseExerciseSet WHERE workoutId = :workoutId")
    fun getExerciseSetsForWorkout(workoutId: Long): Flow<List<DatabaseExerciseSet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgram(program: DatabaseProgram)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: DatabaseWorkout): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSets(sets: List<DatabaseSet>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: DatabaseExercise): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseSet(exerciseSet: DatabaseExerciseSet): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<DatabaseExercise>)

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

    @Query("DELETE FROM DatabaseExerciseSet WHERE id =:id")
    suspend fun deleteExerciseSet(id: Long)
}