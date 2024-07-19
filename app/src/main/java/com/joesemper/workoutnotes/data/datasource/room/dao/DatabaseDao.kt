package com.joesemper.workoutnotes.data.datasource.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExerciseType
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExercise
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExerciseWithExerciseType
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseProgram
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseSet
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkout
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkoutWithExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {
    @Query("SELECT * FROM DatabaseProgram")
    fun getAllPrograms(): Flow<List<DatabaseProgram>>

    @Query("SELECT * FROM DatabaseExerciseType")
    fun getAllExerciseTypes(): Flow<List<DatabaseExerciseType>>

    @Query("SELECT * FROM DatabaseExerciseType WHERE title = :exercise")
    suspend fun getExerciseTypeByTitle(exercise: String): DatabaseExerciseType?

    @Query("SELECT * FROM DatabaseExercise WHERE id = :exerciseId")
    suspend fun getExerciseById(exerciseId: Long): DatabaseExercise?

    @Query("SELECT indexNumber FROM DatabaseExercise WHERE id = :exerciseId")
    suspend fun getExerciseIndexById(exerciseId: Long): Int?

    @Query("SELECT * FROM DatabaseWorkout")
    fun getAllWorkouts(): Flow<List<DatabaseWorkout>>

    @Query("SELECT * FROM DatabaseWorkout WHERE id = :workoutId")
    fun getWorkoutById(workoutId: Long): Flow<DatabaseWorkout>

    @Query("SELECT * FROM DatabaseWorkout WHERE id = :workoutId")
    fun getWorkoutWithExercisesById(workoutId: Long): Flow<DatabaseWorkoutWithExercises>

    @Query("SELECT * FROM DatabaseExercise WHERE id = :exerciseId")
    fun getExerciseWithExerciseType(exerciseId: Long): Flow<DatabaseExerciseWithExerciseType?>

    @Query("SELECT * FROM DatabaseSet WHERE exerciseId = :exerciseId")
    fun getSetsForExercise(exerciseId: Long): Flow<List<DatabaseSet>>

    @Query("SELECT * FROM DatabaseSet")
    fun getAllSets(): Flow<List<DatabaseSet>>

    @Query("SELECT * FROM DatabaseExercise WHERE workoutId = :workoutId")
    fun getExercisesForWorkout(workoutId: Long): Flow<List<DatabaseExercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgram(program: DatabaseProgram)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: DatabaseWorkout): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSets(sets: List<DatabaseSet>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseType(exerciseType: DatabaseExerciseType): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: DatabaseExercise): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseTypes(exerciseTypes: List<DatabaseExerciseType>)

    @Update(entity = DatabaseProgram::class)
    suspend fun updateProgram(program: DatabaseProgram)

    @Update(entity = DatabaseWorkout::class)
    suspend fun updateWorkout(workout: DatabaseWorkout)

    @Update(entity = DatabaseSet::class)
    suspend fun updateSet(set: DatabaseSet)

    @Update(entity = DatabaseExerciseType::class)
    suspend fun updateExerciseType(exerciseType: DatabaseExerciseType)

    @Update(entity = DatabaseExercise::class)
    suspend fun updateExercise(exercise: DatabaseExercise)

    @Query("DELETE FROM DatabaseProgram WHERE id =:id")
    suspend fun deleteProgram(id: Long)

    @Query("DELETE FROM DatabaseSet WHERE id =:id")
    suspend fun deleteSet(id: Long)

    @Query("DELETE FROM DatabaseWorkout WHERE id =:id")
    suspend fun deleteWorkout(id: Long)

    @Query("DELETE FROM DatabaseExerciseType WHERE id =:id")
    suspend fun deleteExerciseType(id: Long)

    @Query("DELETE FROM DatabaseExercise WHERE id =:id")
    suspend fun deleteExerciseSet(id: Long)

    @Query("SELECT MAX(indexNumber) FROM DatabaseExercise WHERE workoutId =:workoutId")
    suspend fun getLastExerciseIndex(workoutId: Long): Int?

    @Query("SELECT MAX(indexNumber) FROM DatabaseSet WHERE exerciseId =:exerciseId")
    suspend fun getLastSetIndexInExercise(exerciseId: Long): Int?

    @Query("DELETE FROM DatabaseSet WHERE exerciseId =:exerciseId AND indexNumber = (SELECT MAX(indexNumber) FROM DatabaseSet)")
    suspend fun deleteLastSetInExercise(exerciseId: Long)

    @Query("DELETE FROM DatabaseSet WHERE exerciseId =:exerciseId")
    suspend fun deleteAllSetsInExercise(exerciseId: Long)

    @Query("SELECT title FROM DatabaseWorkout")
    suspend fun getAllWorkoutNames(): List<String>
}