package com.joesemper.workoutnotes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.joesemper.workoutnotes.navigation.home.HOME_GRAPH
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.EXERCISE_ID
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.HOME_ROUTE
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.NEW_EXERCISE_ROUTE
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.NEW_WORKOUT_ROUTE
import com.joesemper.workoutnotes.navigation.home.HomeState

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
) = remember(navController) { AppState(navController) }

class AppState(
    val navController: NavHostController,
) : HomeState {

    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    override fun navigateHome(from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("$HOME_GRAPH/$HOME_ROUTE") {
                launchSingleTop = true
//                restoreState = true
                popUpTo(findStartDestination(navController.graph).id) {
//                    saveState = true
                }
            }
        }
    }

    override fun navigateToNewWorkout(from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("$HOME_GRAPH/$NEW_WORKOUT_ROUTE") {
                launchSingleTop = true
                popUpTo(findStartDestination(navController.graph).id) {
//                    saveState = true
                }
            }
        }
    }

    override fun navigateToNewExercise(
        from: NavBackStackEntry,
        workoutId: Long,
        exerciseSetId: Long?
    ) {
        if (from.lifecycleIsResumed()) {
            navController.navigate(
                "$HOME_GRAPH/$NEW_EXERCISE_ROUTE/$workoutId${
                    if (exerciseSetId == null) "" else "?$EXERCISE_ID=$exerciseSetId"
                }"
            ) {

            }
        }
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}