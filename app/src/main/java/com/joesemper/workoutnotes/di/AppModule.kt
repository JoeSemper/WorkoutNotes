package com.joesemper.workoutnotes.di

import android.content.Context
import androidx.room.Room
import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepository
import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepositoryImpl
import com.joesemper.workoutnotes.data.datasource.room.WorkoutDatabase
import com.joesemper.workoutnotes.data.datasource.room.dao.DatabaseDao
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExercise
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseProgram
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseSet
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkout
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow


@InstallIn(SingletonComponent::class)
@Module
internal object AppModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): WorkoutDatabase =
        Room.databaseBuilder(context, WorkoutDatabase::class.java, "workoutDatabase")
            .build()

    @Singleton
    @Provides
    fun providesDatabaseDao(workoutDatabase: WorkoutDatabase): DatabaseDao =
        workoutDatabase.databaseDao()

    @Provides
    fun providesWorkoutRepository(databaseDao: DatabaseDao): WorkoutRepository =
        WorkoutRepositoryImpl(databaseDao)


}
