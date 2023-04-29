package com.doersweb.onboarding_presentation.nutrient_goal

sealed class NutrientGoalEvent {
    data class OnCarbEntry(val ratio: String): NutrientGoalEvent()
    data class OnProteinEntry(val ratio: String): NutrientGoalEvent()
    data class OnFatEntry(val ratio: String): NutrientGoalEvent()

    object OnNextClick: NutrientGoalEvent()
}
