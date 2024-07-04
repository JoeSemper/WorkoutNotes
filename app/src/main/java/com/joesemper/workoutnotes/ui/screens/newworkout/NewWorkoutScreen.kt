package com.joesemper.workoutnotes.ui.screens.newworkout

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joesemper.workoutnotes.R
import com.joesemper.workoutnotes.ui.utils.isScrollingUp

@Composable
fun NewWorkoutScreen(
    viewModel: NewWorkoutViewModel = hiltViewModel(),
    navigateHome: () -> Unit,
    navigateToNewExercise: (Long, Long?) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val listState = rememberLazyListState()

    var showDialog by remember { mutableStateOf(false) }

    val currentNavigateToNewExercise by rememberUpdatedState(navigateToNewExercise)
    val currentNavigateHome by rememberUpdatedState(navigateHome)

    if (showDialog) {
        AddNewExerciseDialog(
            onDismiss = { showDialog = false }
        )
    }

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
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                floatingActionButton = {
                    AddExerciseFab(
                        modifier = Modifier.padding(bottom = 32.dp),
                        isVisibleBecauseOfScrolling = listState.isScrollingUp(),
                        onClick = {
                            navigateToNewExercise(state.data.workout.id, null)
                        }
                    )
                },
                floatingActionButtonPosition = FabPosition.End
            ) { paddingValues ->

                NewWorkoutContent(
                    modifier = Modifier.padding(paddingValues),
                    state = state.data,
                    listState = listState,
                    onExerciseClick = { navigateToNewExercise(state.data.workout.id, it) }
                )
            }

        }
    }
}

@Composable
fun AddNewExerciseDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "New exercise",
                    style = MaterialTheme.typography.labelMedium
                )
                NewWorkoutSetView(
                    onApplyClick = {},
                    onCancelClick = {}
                )
            }
        }
    }
}

@Composable
private fun AddExerciseFab(
    modifier: Modifier,
    isVisibleBecauseOfScrolling: Boolean,
    onClick: () -> Unit
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisibleBecauseOfScrolling,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        ExtendedFloatingActionButton(
            text = { Text(text = stringResource(R.string.add_exercise)) },
            onClick = onClick,
            icon = { Icon(Icons.Filled.Add, null) }
        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewWorkoutContent(
    modifier: Modifier = Modifier,
    state: NewWorkoutData,
    listState: LazyListState,
    onExerciseClick: (Long) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {

        item {
            Card(
                modifier = Modifier.fillMaxWidth()

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = state.workout.title,
                        style = MaterialTheme.typography.labelMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = state.workout.id.toString()
                    )
                }

            }
        }

        items(count = state.sets.size) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onExerciseClick(state.sets[it].exercise.id) }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = state.sets[it].exerciseType.title)
                    Text(text = state.sets[it].exercise.indexNumber.toString())
                }
            }
        }
    }
}

@Composable
fun NewWorkoutSetView(
    modifier: Modifier = Modifier,
    onApplyClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        val title = remember { mutableStateOf("") }
        val weight = remember { mutableStateOf("") }
        val sets = remember { mutableStateOf("") }
        val count = remember { mutableStateOf("") }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title.value,
            label = { Text(text = "Exercise") },
            onValueChange = { title.value = it }
        )

        Demo_ExposedDropdownMenuBox()

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = weight.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Weight") },
                onValueChange = { weight.value = it }
            )

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = count.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Count") },
                onValueChange = { count.value = it }
            )

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = sets.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Sets") },
                onValueChange = { sets.value = it }
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { onCancelClick() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Red
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = { onApplyClick() }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.Green
                )
            }
        }

        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
fun AddNewWorkoutSetView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth(),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo_ExposedDropdownMenuBox() {
    val context = LocalContext.current
    val coffeeDrinks = arrayOf(
        "Americano",
        "Cappuccino",
        "Espresso",
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                coffeeDrinks.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}


