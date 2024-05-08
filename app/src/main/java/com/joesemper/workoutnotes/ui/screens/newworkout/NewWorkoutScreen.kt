package com.joesemper.workoutnotes.ui.screens.newworkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
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
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    FullScreenLoadingView()
                }
            }

            is NewWorkoutUiState.Loaded -> {
                NewWorkoutContent(
                    modifier = Modifier.padding(paddingValues),
                    state = state.data,
                    onNewSetClick = { viewModel.addNewWorkoutSet() }
                )
            }

        }

    }
}

@Composable
fun FullScreenLoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun NewWorkoutContent(
    modifier: Modifier = Modifier,
    state: NewWorkoutData,
    onNewSetClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = state.workout.title,
                    style = MaterialTheme.typography.labelMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = state.workout.description
                )
            }
        }

        items(count = state.sets.size) {
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                val title = remember { mutableStateOf("") }
                val weight = remember { mutableStateOf("") }
                val sets = remember { mutableStateOf("") }
                val count = remember { mutableStateOf("") }

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title.value,
                    label = { Text(text = "Exercise") },
                    onValueChange = { title.value = it }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = weight.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text(text = "Weight") },
                        onValueChange = { weight.value = it }
                    )

                    TextField(
                        modifier = Modifier.weight(1f),
                        value = sets.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text(text = "Sets") },
                        onValueChange = { sets.value = it }
                    )

                    TextField(
                        modifier = Modifier.weight(1f),
                        value = count.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text(text = "Count") },
                        onValueChange = { count.value = it }
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .clickable { onNewSetClick() }
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Add exercise"
                    )

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        }
    }
}


