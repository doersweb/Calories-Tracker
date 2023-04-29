package com.doersweb.calorietracker.navigation

import androidx.navigation.NavController
import com.doersweb.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate){
    this.navigate(event.route)
}