package com.joesemper.workoutnotes.navigation.home

import androidx.navigation.NavBackStackEntry

interface HomeState {
    fun navigateHome(from: NavBackStackEntry)
}