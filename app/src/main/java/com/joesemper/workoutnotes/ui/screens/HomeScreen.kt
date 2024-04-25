package com.joesemper.workoutnotes.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {

    val state = viewModel.uiState.collectAsState()

    LazyColumn() {
        items(count = state.value.size) {
            Text(
                text = state.value[it]
            )
        }
    }


}