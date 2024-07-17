package com.joesemper.workoutnotes.ui.screens.newexercise

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepository
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExercise
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExerciseType
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseExerciseWithExerciseType
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseSet
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.EXERCISE_ID
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.EXERCISE_ID_DEFAULT
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.WORKOUT_ID
import com.joesemper.workoutnotes.ui.utils.convertToInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewExerciseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: WorkoutRepository
) : ViewModel() {
    private val workoutId: Long = checkNotNull(savedStateHandle[WORKOUT_ID])
    private val savedExerciseId: Long = checkNotNull(savedStateHandle[EXERCISE_ID])

    private val _uiState = MutableNewExerciseUiState()
    val uiState: NewExerciseUiState = _uiState

    init {
        loadExerciseVariants()
        loadInitialData()

        _uiState.isLoading = false
    }

    private fun loadExerciseVariants() {
        viewModelScope.launch {
            repository.getAllExerciseTypes().collectLatest { variants ->
                _uiState.exerciseTypeState.exerciseTypeVariants.clear()
                _uiState.exerciseTypeState.exerciseTypeVariants.addAll(variants.map { it.title })
            }
        }
    }

    private fun loadInitialData() {
        if (savedExerciseId != EXERCISE_ID_DEFAULT) {
            loadSavedExerciseData(savedExerciseId)
        } else {
            _uiState.addNewWeight()
        }
    }

    private fun loadSavedExerciseData(exerciseId: Long) {
        viewModelScope.launch {
            repository.getExerciseWithExerciseType(exerciseId).collectLatest { exercise ->
                exercise?.let {
                    updateLoadedExerciseData(exercise)
                    loadAndUpdateSetsData(exercise.exercise.id)
                }
            }
        }
    }

    private fun updateLoadedExerciseData(exercise: DatabaseExerciseWithExerciseType) {
        _uiState.id = exercise.exercise.id
        _uiState.exerciseTypeState.selectedExerciseType = exercise.exerciseType.title
    }

    private fun loadAndUpdateSetsData(exerciseId: Long) {
        viewModelScope.launch {
            repository.getSetsForExercise(exerciseId).collectLatest { sets ->
                _uiState.exerciseSetsState.clear()
                _uiState.exerciseSetsState.addAll(
                    sets.map { databaseSet ->
                        MutableSetUiState(
                            index = databaseSet.indexNumber,
                            initialWeight = databaseSet.weight.toString(),
                            initialRepetitions = databaseSet.repetitions.toString(),
                            initialSets = databaseSet.sets.toString()
                        )
                    }
                )
            }
        }
    }

    fun saveExercise() {
        viewModelScope.launch {
            if (savedExerciseId != EXERCISE_ID_DEFAULT) {
                updateExistingExercise()
                saveSets(savedExerciseId)
            } else {
                val id = createNewExerciseAndGetId()
                saveSets(id)
            }
        }
        _uiState.isSaved = true
    }

    private suspend fun updateExistingExercise() {
        repository.updateExercise(
            DatabaseExercise(
                id = savedExerciseId,
                workoutId = workoutId,
                exerciseTypeId = getExerciseTypeId(_uiState.exerciseTypeState.selectedExerciseType),
                indexNumber = repository.getExerciseIndexById(savedExerciseId)
            )
        )
    }

    private suspend fun createNewExerciseAndGetId(): Long {
        return repository.insertExercise(
            DatabaseExercise(
                workoutId = workoutId,
                exerciseTypeId = getExerciseTypeId(_uiState.exerciseTypeState.selectedExerciseType),
                indexNumber = repository.getLastExerciseIndex(workoutId).inc()
            )
        )
    }

    private suspend fun saveSets(exerciseId: Long) {

        _uiState.isLoading = true

        repository.deleteAllSetsInExercise(exerciseId)

        repository.insertSets(_uiState.exerciseSetsState.map {
            DatabaseSet(
                exerciseId = exerciseId,
                indexNumber = it.index,
                weight = it.weight.convertToInt(),
                repetitions = it.repetitions.convertToInt(),
                sets = it.sets.convertToInt()
            )
        })

    }

    private suspend fun getExerciseTypeId(exerciseName: String): Long {
        return repository.getExerciseTypeByTitle(exerciseName)?.id ?: repository.insertExerciseType(
            DatabaseExerciseType(title = exerciseName)
        )
    }

}

@Stable
interface NewExerciseUiState {
    val id: Long
    val isLoading: Boolean
    val isSaved: Boolean
    val exerciseTypeState: ExerciseTypeUiState
    val exerciseSetsState: List<SetUiState>
    val addNewWeight: () -> Unit
    val deleteWeight: () -> Unit
}

class MutableNewExerciseUiState(
) : NewExerciseUiState {

    override var id: Long by mutableStateOf(EXERCISE_ID_DEFAULT)

    override var isLoading: Boolean by mutableStateOf(true)

    override var isSaved: Boolean by mutableStateOf(false)

    override val exerciseTypeState = MutableExerciseTypeUiState()

    override val exerciseSetsState: MutableList<SetUiState> = mutableStateListOf()

    override val addNewWeight: () -> Unit = {
        exerciseSetsState.add(MutableSetUiState(findLastSetIndex().inc()))
    }
    override val deleteWeight: () -> Unit = {
        exerciseSetsState.removeIf { it.index == findLastSetIndex() }
    }

    private fun findLastSetIndex() = exerciseSetsState.maxByOrNull { it.index }?.index ?: -1
}

@Stable
interface ExerciseTypeUiState {
    val exerciseTypeVariants: List<String>
    val selectedExerciseType: String
    val updateSelectedExercise: (String) -> Unit
}

class MutableExerciseTypeUiState() : ExerciseTypeUiState {
    override val exerciseTypeVariants: MutableList<String> = mutableStateListOf()
    override var selectedExerciseType: String by mutableStateOf("")

    override val updateSelectedExercise: (String) -> Unit = { selectedExerciseType = it}
}

@Stable
interface SetUiState {
    val index: Int
    val weight: String
    val repetitions: String
    val sets: String
    val updateWeight: (String) -> Unit
    val updateRepetitions: (String) -> Unit
    val updateSets: (String) -> Unit
}

class MutableSetUiState(
    override val index: Int,
    initialWeight: String = "",
    initialRepetitions: String = "",
    initialSets: String = ""
) : SetUiState {
    override var weight: String by mutableStateOf(initialWeight)
    override var repetitions: String by mutableStateOf(initialRepetitions)
    override var sets: String by mutableStateOf(initialSets)
    override val updateWeight: (String) -> Unit = { weight = it }
    override val updateRepetitions: (String) -> Unit = { repetitions = it }
    override val updateSets: (String) -> Unit = { sets = it }
}
