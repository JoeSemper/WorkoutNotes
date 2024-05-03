package com.joesemper.workoutnotes.navigation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepository
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.HOME_ROUTE
import com.joesemper.workoutnotes.ui.screens.HomeScreen

const val HOME_GRAPH = "home"

object HomeDestinations {
    const val HOME_ROUTE = "root"
}

fun NavGraphBuilder.addHomeGraph(
    homeState: HomeState,
    upPress: () -> Unit,
) {
    composable("$HOME_GRAPH/$HOME_ROUTE") { from ->
        HomeScreen()
    }
}