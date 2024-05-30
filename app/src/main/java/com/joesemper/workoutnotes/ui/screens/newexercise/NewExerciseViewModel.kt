package com.joesemper.workoutnotes.ui.screens.newexercise

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepository
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExercise
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.WORKOUT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewExerciseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: WorkoutRepository
) : ViewModel() {
    private val workoutId: Long = checkNotNull(savedStateHandle[WORKOUT_ID])

    @OptIn(ExperimentalCoroutinesApi::class)
    val exercises = repository
        .getAllExercises()
        .mapLatest { list ->
            list.map { exercise -> exercise.title }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun createExercise(exercise: String) {
        viewModelScope.launch {
            if (repository.getExerciseByTitle(exercise) == null) {
                repository.insertExercise(DatabaseExercise(title = exercise))
            }

        }
    }


}