package com.joesemper.workoutnotes.ui.screens.newexercise

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import com.joesemper.workoutnotes.R
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewExerciseScreen(
    viewModel: NewExerciseViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {

    val uiState = viewModel.uiState
    val exerciseTypeVariants = uiState.exerciseTypeState.exerciseTypeVariants

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val currentNavigateBack by rememberUpdatedState(navigateBack)

    LaunchedEffect(uiState.isSaved, lifecycle) {
        snapshotFlow { uiState }
            .filter { it.isSaved }
            .flowWithLifecycle(lifecycle)
            .collect {
                currentNavigateBack()
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.new_exercise))
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (uiState.isLoading) {
                LoadingView()
            } else {
                NewExerciseContentView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .fillMaxHeight(),
                    exerciseList = exerciseTypeVariants,
                    selectedExercise = uiState.exerciseTypeState.selectedExerciseType,
                    onExerciseTextChange = { uiState.exerciseTypeState.updateSelectedExercise(it) },
                    setsList = uiState.exerciseSetsState,
                    onAddNewWeight = uiState.addNewWeight,
                    onDeleteWeight = uiState.deleteWeight
                )

                BottomButtonsView(
                    modifier = Modifier.fillMaxWidth(),
                    onCancelClick = {
                        navigateBack()
                    },
                    onApplyClick = {
                        viewModel.saveExercise()
                    }
                )
            }
        }
    }
}

@Composable fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewExerciseContentView(
    modifier: Modifier = Modifier,
    exerciseList: List<String> = emptyList(),
    selectedExercise: String,
    onExerciseTextChange: (String) -> Unit,
    setsList: List<SetUiState>,
    onAddNewWeight: () -> Unit,
    onDeleteWeight: () -> Unit

) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ExerciseInputView(
                suggestions = exerciseList,
                selectedText = selectedExercise,
                onTextChange = onExerciseTextChange
            )
        }

        items(
            count = setsList.size,
            key = { setsList[it].index }
        ) {
            SetAdjustmentView(
                modifier = Modifier.animateItemPlacement(),
                state = setsList[it]
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AssistChip(
                    onClick = onDeleteWeight,
                    enabled = setsList.size > 1,
                    label = { Text(text = stringResource(R.string.delete_weight)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null
                        )
                    }
                )

                AssistChip(
                    onClick = onAddNewWeight,
                    label = { Text(text = stringResource(R.string.add_weight)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun BottomButtonsView(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onApplyClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(onClick = onCancelClick) {
            Text(text = stringResource(R.string.cancel))
        }

        Button(onClick = onApplyClick) {
            Text(text = stringResource(R.string.save_exercise))
        }
    }
}

@Composable
fun SetAdjustmentView(
    modifier: Modifier = Modifier,
    state: SetUiState,
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.weight) + " " + "${state.index + 1}",
                style = MaterialTheme.typography.titleSmall
            )
            HorizontalDivider(
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.weight),
                    style = MaterialTheme.typography.labelMedium
                )

                OutlinedTextField(
                    value = state.weight,
                    onValueChange = { state.updateWeight(it) },
                    trailingIcon = { Text(text = stringResource(R.string.kg)) },
                    placeholder = { Text(text = stringResource(R.string.set_parameters_placeholder)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(R.string.reps),
                    style = MaterialTheme.typography.labelMedium
                )

                OutlinedTextField(
                    value = state.repetitions,
                    onValueChange = { state.updateRepetitions(it) },
                    placeholder = { Text(text = stringResource(R.string.set_parameters_placeholder)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.sets),
                    style = MaterialTheme.typography.labelMedium
                )

                OutlinedTextField(
                    value = state.sets,
                    onValueChange = { state.updateSets(it) },
                    placeholder = { Text(text = stringResource(R.string.set_parameters_placeholder)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseInputView(
    modifier: Modifier = Modifier,
    suggestions: List<String> = emptyList(),
    selectedText: String,
    onTextChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { onTextChange(it) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                modifier = modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                suggestions.filter {
                    it.startsWith(selectedText)
                }.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            onTextChange(item)
                            expanded = false
                        }
                    )
                }
            }
        }
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