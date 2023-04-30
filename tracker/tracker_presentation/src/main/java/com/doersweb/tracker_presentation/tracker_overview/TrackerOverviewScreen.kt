package com.doersweb.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.doersweb.core.util.UiEvent
import com.doersweb.core_ui.LocalSpacing
import com.doersweb.tracker_presentation.tracker_overview.uicomponents.ExpandableMeal
import com.doersweb.tracker_presentation.tracker_overview.uicomponents.TrackedFoodItem
import com.doersweb.tracker_presentation.uicomponents.DaySelector
import com.doersweb.core.R

@Composable
fun TrackerOverviewScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ) {
        item {
            NutrientsHeader(state = state)
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            DaySelector(
                date = state.date,
                onPreviousDayClick = {
                    viewModel.onEvent(TrackerOverViewEvent.OnPreviousDayClick)
                },
                onNextDayClick = {
                    viewModel.onEvent(TrackerOverViewEvent.OnNextDayClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }

        items(state.meals) { meal ->
            ExpandableMeal(
                meal = meal,
                onToggleClick = {
                    viewModel.onEvent(TrackerOverViewEvent.OnToggleMealClick(meal))
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.spaceSmall)
                    ) {
                        state.trackedFoods.forEach {
                            TrackedFoodItem(trackedFood = it, onDeleteClick = {
                                viewModel.onEvent(TrackerOverViewEvent.OnDeleteTrackedFood(it))
                            })
                            Spacer(modifier = Modifier.height(spacing.spaceMedium))
                        }
                    }
                    AddButton(
                        text = stringResource(
                            id = R.string.add_meal,
                            meal.name.asString(context)
                        ),
                        onClick = {
                            viewModel.onEvent(TrackerOverViewEvent.OnAddFoodClick(meal))
                        },
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = spacing.spaceSmall)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }


    }
}