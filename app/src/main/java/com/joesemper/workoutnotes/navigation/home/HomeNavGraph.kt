package com.joesemper.workoutnotes.navigation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.EXERCISE_SET_ID
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.HOME_ROUTE
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.NEW_EXERCISE_ROUTE
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.NEW_WORKOUT_ROUTE
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.WORKOUT_ID
import com.joesemper.workoutnotes.ui.screens.home.HomeScreen
import com.joesemper.workoutnotes.ui.screens.newexercise.NewExerciseScreen
import com.joesemper.workoutnotes.ui.screens.newworkout.NewWorkoutScreen

const val HOME_GRAPH = "home"

object HomeDestinations {
    const val HOME_ROUTE = "root"
    const val NEW_WORKOUT_ROUTE = "newWorkout"
    const val WORKOUT_ID = "workoutId"
    const val EXERCISE_SET_ID = "exerciseSetId"
    const val NEW_EXERCISE_ROUTE = "newExercise"

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
            navigateHome = { homeState.navigateHome(from) },
            navigateToNewExercise = { workoutId ->
                homeState.navigateToNewExercise(from,  workoutId)
            }
        )
    }

    composable(
        "$HOME_GRAPH/$NEW_EXERCISE_ROUTE/{$WORKOUT_ID}",
        arguments = listOf(navArgument(WORKOUT_ID) { type = NavType.LongType })
    ) { from ->
        NewExerciseScreen(
            navigateBack = upPress
        )
    }

}