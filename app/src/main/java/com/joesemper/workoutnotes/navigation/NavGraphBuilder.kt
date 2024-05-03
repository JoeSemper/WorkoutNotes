package com.joesemper.workoutnotes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepository
import com.joesemper.workoutnotes.navigation.home.HOME_GRAPH
import com.joesemper.workoutnotes.navigation.home.HomeDestinations.HOME_ROUTE
import com.joesemper.workoutnotes.navigation.home.HomeState
import com.joesemper.workoutnotes.navigation.home.addHomeGraph

fun NavGraphBuilder.buildNavGraph(
    upPress: () -> Unit,
    homeState: HomeState,
) {
    navigation(
        route = HOME_GRAPH,
        startDestination = "$HOME_GRAPH/$HOME_ROUTE"
    ) {
        addHomeGraph(
            upPress = upPress,
            homeState = homeState,
        )
    }
}