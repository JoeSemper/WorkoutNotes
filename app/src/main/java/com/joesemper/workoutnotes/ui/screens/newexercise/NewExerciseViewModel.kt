package com.joesemper.workoutnotes.ui.screens.newexercise

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepository
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExercise
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExerciseSet
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseSet
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.WORKOUT_ID
import com.joesemper.workoutnotes.ui.utils.convertToInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
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

    private val _uiState = MutableStateFlow(NewExerciseUiState())
    val uiState = _uiState.asStateFlow()

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

    fun addNewWeight() {
        _uiState.value.sets.add(SetParameters(index = getLastSetIndex().inc()))
    }

    fun saveExerciseSets() {
        viewModelScope.launch {
            saveSets(getOrCreateExerciseId(_uiState.value.selectedExercise.value))
        }
    }

    private suspend fun getOrCreateExerciseId(exerciseName: String): Long {
        return repository.getExerciseByTitle(exerciseName)?.id ?: repository.insertExercise(
            DatabaseExercise(title = exerciseName)
        )
    }

    private fun saveSets(exerciseId: Long) {
        viewModelScope.launch {

            val exerciseSetId = repository.insertExerciseSet(
                DatabaseExerciseSet(
                    workoutId = workoutId,
                    exerciseId = exerciseId,
                    indexNumber = repository.getLastExerciseSetIndex(workoutId).inc()
                )
            )

            repository.insertSets(_uiState.value.sets.map {
                DatabaseSet(
                    exerciseSetId = exerciseSetId,
                    index = it.index,
                    weight = it.weight.value.convertToInt(),
                    repetitions = it.repetitions.value.convertToInt(),
                    sets = it.sets.value.convertToInt()
                )
            })
        }
    }

    private fun getLastSetIndex() = _uiState.value.sets.maxBy { it.index }.index

}


data class NewExerciseUiState(
    val selectedExercise: MutableState<String> = mutableStateOf(""),
    val sets: SnapshotStateList<SetParameters> = mutableStateListOf(SetParameters())
) {
    val isError: Boolean
        get() = selectedExercise.value.isNotEmpty()
}

data class SetParameters(
    val index: Int = 0,
    val weight: MutableState<String> = mutableStateOf(""),
    val repetitions: MutableState<String> = mutableStateOf(""),
    val sets: MutableState<String> = mutableStateOf("")
)