package com.joesemper.workoutnotes.navigation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.HOME_ROUTE
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.NEW_WORKOUT_ROUTE
import com.joesemper.workoutnotes.ui.screens.home.HomeScreen
import com.joesemper.workoutnotes.ui.screens.newworkout.NewWorkoutScreen

const val HOME_GRAPH = "home"

object HomeDestinations {
    const val HOME_ROUTE = "root"
    const val NEW_WORKOUT_ROUTE = "newWorkout"
}

fun NavGraphBuilder.addHomeGraph(
    homeState: HomeState,
    upPress: () -> Unit,
) {
    composable("$HOME_GRAPH/$HOME_ROUTE") { from ->
        HomeScreen(
            navigateToNewWorkout = { homeState.navigateToNewWorkout(from) }
        )
    }

    composable("$HOME_GRAPH/$NEW_WORKOUT_ROUTE") { from ->
        NewWorkoutScreen(
            navigateHome = { homeState.navigateHome(from) }
        )
    }
}