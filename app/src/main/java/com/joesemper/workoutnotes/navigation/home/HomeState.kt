package com.joesemper.workoutnotes.navigation.home

import androidx.navigation.NavBackStackEntry

interface HomeState {
    fun navigateHome(from: NavBackStackEntry)
    fun navigateToNewWorkout(from: NavBackStackEntry)
    fun navigateToNewExercise(from: NavBackStackEntry, workoutId: Long, exerciseSetId: Long?)
}