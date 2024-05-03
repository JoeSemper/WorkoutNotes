package com.joesemper.workoutnotes.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepository
import com.joesemper.workoutnotes.navigation.home.HOME_GRAPH
import com.joesemper.workoutnotes.navigation.home.HomeState

@Composable
fun AppNavHost(
    startDestination: String = HOME_GRAPH,
) {
    val appState = rememberAppState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = appState.navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            buildNavGraph(
                upPress = appState::upPress,
                homeState = appState as HomeState
            )
        }
    }
}