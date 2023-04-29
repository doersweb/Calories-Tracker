package com.doersweb.onboarding_presentation.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doersweb.core.domain.preferences.Preferences
import com.doersweb.core.domain.usecases.FilterOutDecimals
import com.doersweb.core.navigation.Routes
import com.doersweb.core.util.UiEvent
import com.doersweb.onboarding_domain.usecases.ValidateNutrientPercentage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDecimals: FilterOutDecimals,
    private val validateNutrientPercentage: ValidateNutrientPercentage
): ViewModel() {

    var state by mutableStateOf<NutrientGoalState>(NutrientGoalState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        when(event){
            is NutrientGoalEvent.OnCarbEntry -> {
                state = state.copy(
                    carbsRatio = filterOutDecimals(event.ratio)
                )
            }
            is NutrientGoalEvent.OnFatEntry -> {
                state = state.copy(
                    fatRatio = filterOutDecimals(event.ratio)
                )
            }
            is NutrientGoalEvent.OnProteinEntry -> {
                state = state.copy(
                    proteinRatio = filterOutDecimals(event.ratio)
                )
            }
            is NutrientGoalEvent.OnNextClick -> {
                val result = validateNutrientPercentage(
                    carbsRatioText = state.carbsRatio,
                    proteinRatioText = state.proteinRatio,
                    fatRatioText = state.fatRatio
                )

                when(result) {
                    is ValidateNutrientPercentage.Result.Valid -> {
                        preferences.saveCarbRatio(result.carbsRatio)
                        preferences.saveFatRatio(result.fatRatio)
                        preferences.saveProteinRatio(result.proteinRatio)
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.Navigate(Routes.TRACKER_OVERVIEW))
                        }
                    }

                    is ValidateNutrientPercentage.Result.Invalid -> {
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackBar(result.message))
                        }
                    }
                }
            }
        }
    }
}