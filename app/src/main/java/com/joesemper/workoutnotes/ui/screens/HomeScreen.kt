package com.joesemper.workoutnotes.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel



@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val state = viewModel.uiState.collectAsState()

    LazyColumn() {

        item {
            Card(
                modifier = Modifier
                    .clickable { viewModel.createProgram() }
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "New program"
                )
            }
        }

        items(count = state.value.size) {
            Card(
                modifier = Modifier
                    .clickable { viewModel.deleteProgram(state.value[it].id) }
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = state.value[it].id.toString()
                )
                Text(
                    text = state.value[it].title
                )
            }

        }

    }
}