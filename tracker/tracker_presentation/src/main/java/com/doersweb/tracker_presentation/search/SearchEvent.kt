package com.doersweb.tracker_presentation.search

import com.doersweb.tracker_domain.model.MealType
import com.doersweb.tracker_domain.model.TrackableFood
import java.time.LocalDate

sealed class SearchEvent {
    data class OnQueryChange(val query: String) : SearchEvent()
    object OnSearch : SearchEvent()
    data class OnToggleTrackableFood(val food: TrackableFood) : SearchEvent()
    data class OnAmountChangeForTrackableFood(
        val food: TrackableFood,
        val amount: String
    ) : SearchEvent()
    data class OnTrackedFoodClick(
        val food: TrackableFood,
        val mealType: MealType,
        val date: LocalDate
    ) : SearchEvent()
    data class OnSearchFocusChange(val isFocused: Boolean) : SearchEvent()
}