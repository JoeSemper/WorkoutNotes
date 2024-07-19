package com.joesemper.workoutnotes.di

import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepository
import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepositoryImpl
import com.joesemper.workoutnotes.data.datasource.room.dao.DatabaseDao
import com.joesemper.workoutnotes.domain.usecase.GenerateWorkoutNameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object ViewModelMovieModule {

    @Provides
    fun providesGenerateWorkoutNameUseCase(repository: WorkoutRepository): GenerateWorkoutNameUseCase =
        GenerateWorkoutNameUseCase(repository = repository)


}