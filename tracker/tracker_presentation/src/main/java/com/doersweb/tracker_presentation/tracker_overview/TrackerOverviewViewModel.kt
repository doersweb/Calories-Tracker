package com.doersweb.tracker_presentation.tracker_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doersweb.core.domain.preferences.Preferences
import com.doersweb.core.navigation.Routes
import com.doersweb.core.util.UiEvent
import com.doersweb.tracker_domain.usecase.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases
) : ViewModel() {

    private var getFoodsForDateJob: Job? = null

    var state by mutableStateOf<TrackerOverviewState>(TrackerOverviewState())

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        refreshFoods()
        preferences.saveShouldShowOnboarding(false)
    }

    fun onEvent(event: TrackerOverViewEvent) {
        when (event) {
            is TrackerOverViewEvent.OnAddFoodClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.Navigate(
                            route = Routes.SEARCH
                                    + "/${event.meal.mealType.name}"
                                    + "/${state.date.dayOfMonth}"
                                    + "/${state.date.monthValue}"
                                    + "/${state.date.year}"
                        )
                    )
                }
            }

            is TrackerOverViewEvent.OnNextDayClick -> {
                state = state.copy(
                    date = state.date.plusDays(1)
                )
                refreshFoods()
            }

            is TrackerOverViewEvent.OnPreviousDayClick -> {
                state = state.copy(
                    date = state.date.minusDays(1)
                )
                refreshFoods()
            }

            is TrackerOverViewEvent.OnDeleteTrackedFood -> {
                viewModelScope.launch {
                    trackerUseCases.deleteTrackedFood(event.trackedFood)
                    refreshFoods()
                }
            }

            is TrackerOverViewEvent.OnToggleMealClick -> {
                state = state.copy(
                    meals = state.meals.map {
                            if(it.name == event.meal.name) {
                                it.copy(isExpanded = !it.isExpanded)
                            } else it
                    }
                )
            }
        }
    }

    private fun refreshFoods() {
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = trackerUseCases.getFoodsForDate(state.date).onEach { foods ->
            val resultantNutrient = trackerUseCases.calculateMealNutrients(foods)
            state = state.copy(
                totalCarbs = resultantNutrient.totalCarbs,
                totalCalories = resultantNutrient.totalCalories,
                totalFat = resultantNutrient.totalFat,
                totalProtein = resultantNutrient.totalProtein,
                carbsGoal = resultantNutrient.carbsGoal,
                fatGoal = resultantNutrient.fatGoal,
                proteinGoal = resultantNutrient.proteinGoal,
                caloriesGoal = resultantNutrient.caloriesGoal,
                trackedFoods = foods,
                meals = state.meals.map {
                    val nutrientsForMeal = resultantNutrient.mealNutrients[it.mealType] ?: return@map it.copy(
                        carbs = 0,
                        protein = 0,
                        fat = 0,
                        calories = 0
                    )
                    it.copy(
                        carbs = nutrientsForMeal.carbs,
                        protein = nutrientsForMeal.protein,
                        fat = nutrientsForMeal.fat,
                        calories = nutrientsForMeal.calories
                    )
                }
            )
        }.launchIn(viewModelScope)
    }

}