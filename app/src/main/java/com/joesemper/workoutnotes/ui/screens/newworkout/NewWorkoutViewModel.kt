package com.joesemper.workoutnotes.ui.screens.newworkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepository
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExerciseWithExerciseType
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseWorkout
import com.joesemper.workoutnotes.domain.usecase.GenerateWorkoutNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class NewWorkoutViewModel @Inject constructor(
    private val repository: WorkoutRepository,
    private val generateWorkoutName: GenerateWorkoutNameUseCase
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

            repository.getWorkoutWithExercisesById(workoutId).collect { workoutWithSets ->

                _uiState.update { state ->
                    NewWorkoutUiState.Loaded(
                        data = NewWorkoutData(
                            workout = workoutWithSets.workout,
                            sets = workoutWithSets.sets
                        )
                    )
                }

            }
        }
    }

}

//private fun generateWorkoutName(): String {
//    val date = Calendar.getInstance().time
//    val formatter = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT)
//    return "Workout ${formatter.format(date)}"
//
//}

sealed class NewWorkoutUiState() {
    object Loading : NewWorkoutUiState()
    class Loaded(val data: NewWorkoutData) : NewWorkoutUiState()
}

data class NewWorkoutData(
    val workout: DatabaseWorkout,
    val sets: List<DatabaseExerciseWithExerciseType>
)