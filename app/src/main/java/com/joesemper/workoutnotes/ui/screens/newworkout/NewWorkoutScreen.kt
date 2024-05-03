package com.joesemper.workoutnotes.ui.screens.newworkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NewWorkoutScreen(
    viewModel: NewWorkoutViewModel = hiltViewModel(),
    navigateHome: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        when (val state = uiState) {
            NewWorkoutUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }

            is NewWorkoutUiState.Loaded -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    item {
                        Card(
                            modifier = Modifier
                                .clickable { navigateHome() }
                                .padding(8.dp)
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Home")
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(state.data.workout.title)
                            Text(state.data.workout.description)
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier
                                .clickable { viewModel.addNewWorkoutSet() }
                                .padding(8.dp)
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Icon(
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = null
                                )
                            }
                        }
                    }

                    items(count = state.data.sets.size) {
                        Text(state.data.sets[it].id.toString())
                    }
                }
            }

        }


    }


}