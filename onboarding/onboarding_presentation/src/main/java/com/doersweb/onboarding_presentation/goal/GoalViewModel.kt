package com.doersweb.onboarding_presentation.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doersweb.core.domain.model.ActivityLevel
import com.doersweb.core.domain.model.GoalType
import com.doersweb.core.domain.preferences.Preferences
import com.doersweb.core.navigation.Routes
import com.doersweb.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val preferences: Preferences
): ViewModel() {

    var selectedGoalLevel by mutableStateOf<GoalType>(GoalType.KeepWeight)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGoalSelected(goal: GoalType) {
        selectedGoalLevel = goal
    }

    fun onNextClick() {
        viewModelScope.launch {
            preferences.saveGoalType(selectedGoalLevel)
            _uiEvent.send(UiEvent.Navigate(Routes.NUTRIENT_GOAL))
        }
    }
}