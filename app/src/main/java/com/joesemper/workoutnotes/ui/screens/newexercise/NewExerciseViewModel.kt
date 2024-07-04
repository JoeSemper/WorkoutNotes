package com.joesemper.workoutnotes.ui.screens.newexercise

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepository
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExerciseType
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExercise
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseSet
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.EXERCISE_ID
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.EXERCISE_ID_DEFAULT
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.WORKOUT_ID
import com.joesemper.workoutnotes.ui.utils.convertToInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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
    private val exerciseId: Long = checkNotNull(savedStateHandle[EXERCISE_ID])

    private val _uiState = MutableStateFlow(NewExerciseUiState())
    val uiState = _uiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val exercises = repository
        .getAllExerciseTypes()
        .mapLatest { list ->
            list.map { exercise -> exercise.title }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        if (exerciseId != EXERCISE_ID_DEFAULT) {
            loadExerciseData()
        }
    }

    private fun loadExerciseData() {
        viewModelScope.launch {
            repository.getExerciseWithExerciseType(exerciseId).collect { exercise ->
                repository.getSetsForExercise(exerciseId).collect { sets ->
                    _uiState.value.sets.apply {
                        clear()
                        addAll(sets.map { SetParameters(
                            index = it.index,
                            weight = mutableStateOf(it.weight.toString()),
                            repetitions = mutableStateOf(it.repetitions.toString()),
                            sets = mutableStateOf(it.sets.toString())
                        ) })
                    }

                    _uiState.value.selectedExercise.value = exercise.exerciseType.title
                }

            }
        }
    }

    fun addNewWeight() {
        _uiState.value.sets.add(SetParameters(index = getLastSetIndex().inc()))
    }

    fun deleteWeight() {
        _uiState.value.sets.removeIf { it.index == getLastSetIndex() }
    }

    fun saveExerciseSets() {
        viewModelScope.launch {
            saveSets(getExerciseTypeId(_uiState.value.selectedExercise.value))
        }
    }

    private suspend fun getExerciseTypeId(exerciseName: String): Long {
        return repository.getExerciseTypeByTitle(exerciseName)?.id ?: repository.insertExerciseType(
            DatabaseExerciseType(title = exerciseName)
        )
    }

    private fun saveSets(exerciseId: Long) {
        viewModelScope.launch {

            val exerciseSetId = repository.insertExercise(
                DatabaseExercise(
                    workoutId = workoutId,
                    exerciseTypeId = exerciseId,
                    indexNumber = repository.getLastExerciseSetIndex(workoutId).inc()
                )
            )

            repository.insertSets(_uiState.value.sets.map {
                DatabaseSet(
                    exerciseId = exerciseSetId,
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