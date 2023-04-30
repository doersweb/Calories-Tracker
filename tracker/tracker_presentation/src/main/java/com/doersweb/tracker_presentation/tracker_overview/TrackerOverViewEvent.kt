package com.doersweb.tracker_presentation.tracker_overview

import androidx.annotation.DrawableRes
import com.doersweb.core.util.UiText
import com.doersweb.tracker_domain.model.MealType
import com.doersweb.tracker_domain.model.TrackedFood
import com.doersweb.core.R

sealed class TrackerOverViewEvent{
    object OnNextDayClick: TrackerOverViewEvent()
    object OnPreviousDayClick: TrackerOverViewEvent()
    data class OnToggleMealClick(val meal: Meal): TrackerOverViewEvent()
    data class OnDeleteTrackedFood(val trackedFood: TrackedFood): TrackerOverViewEvent()
    data class OnAddFoodClick(val meal: Meal): TrackerOverViewEvent()
}

data class Meal(
    val name: UiText,
    @DrawableRes val drawableRes: Int,
    val mealType: MealType,
    val carbs: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val calories: Int = 0,
    val isExpanded: Boolean = false
)

val defaultMeals = listOf(
    Meal(
        name = UiText.StringResource(R.string.breakfast),
        drawableRes = R.drawable.ic_breakfast,
        mealType = MealType.Breakfast
    ),
    Meal(
        name = UiText.StringResource(R.string.lunch),
        drawableRes = R.drawable.ic_lunch,
        mealType = MealType.Lunch
    ),
    Meal(
        name = UiText.StringResource(R.string.dinner),
        drawableRes = R.drawable.ic_dinner,
        mealType = MealType.Dinner
    ),
    Meal(
        name = UiText.StringResource(R.string.snacks),
        drawableRes = R.drawable.ic_snack,
        mealType = MealType.Snack
    )
)

