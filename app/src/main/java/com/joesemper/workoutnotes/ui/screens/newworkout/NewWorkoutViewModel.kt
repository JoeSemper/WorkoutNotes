package com.joesemper.workoutnotes.ui.screens.newworkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepository
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseSet
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class NewWorkoutViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<NewWorkoutUiState>(NewWorkoutUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        createNewWorkout()
    }

    private fun createNewWorkout() {
        viewModelScope.launch {
            val workoutId = repository.insertWorkout(
                DatabaseWorkout(
                    title = generateWorkoutName()
                )
            )
            repository.getWorkoutById(workoutId).collect { workout ->
                _uiState.update { state ->
                    NewWorkoutUiState.Loaded(
                        data = NewWorkoutData(
                            workout = workout,
                            sets = emptyList()
                        )
                    )
                }
            }
        }
    }

    private fun subscribeOnWorkout(id: Long) {
        viewModelScope.launch {
            repository.getSetsForWorkout(id).collect { sets ->
                _uiState.update { state ->
                    if (state is NewWorkoutUiState.Loaded) {
                        NewWorkoutUiState.Loaded(data = state.data.copy(sets = sets))
                    } else {
                        state
                    }
                }

            }
        }
    }


    fun addNewWorkoutSet() {
        viewModelScope.launch {
            when (val state = _uiState.value) {
                is NewWorkoutUiState.Loaded -> {
                    repository.insertSets(
                        listOf(
                            DatabaseSet(
                                workoutId = state.data.workout.id
                            )
                        )
                    )
                }

                else -> {}
            }

        }
    }
}


private fun generateWorkoutName(): String {
    val date = Calendar.getInstance().time
    val formatter = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT)
    return "Workout ${formatter.format(date)}"

}

sealed class NewWorkoutUiState() {
    object Loading : NewWorkoutUiState()
    class Loaded(val data: NewWorkoutData) : NewWorkoutUiState()
}

data class NewWorkoutData(
    val workout: DatabaseWorkout,
    val sets: List<DatabaseSet>
)