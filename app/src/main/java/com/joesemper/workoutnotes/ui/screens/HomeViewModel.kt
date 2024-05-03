package com.joesemper.workoutnotes.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepository
import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepositoryImpl
import com.joesemper.workoutnotes.data.datasource.room.entity.DatabaseProgram
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {

    val uiState = MutableStateFlow(listOf<DatabaseProgram>())

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getAllPrograms().collect {
                uiState.value = it
            }
        }
    }

    fun createProgram() {
        viewModelScope.launch {
            val date = Calendar.getInstance().time.time
            repository.insertProgram(
                DatabaseProgram(
                    id = Random(date).nextInt(),
                    title = Random(date).nextInt().toString()
                )
            )
        }
    }

    fun deleteProgram(id: Int) {
        viewModelScope.launch {
            repository.deleteProgram(id)
        }
    }

}