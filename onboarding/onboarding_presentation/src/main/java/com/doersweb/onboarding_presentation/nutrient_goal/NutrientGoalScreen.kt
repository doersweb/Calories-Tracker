package com.doersweb.onboarding_presentation.nutrient_goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.doersweb.core.R
import com.doersweb.core.util.UiEvent
import com.doersweb.core_ui.LocalSpacing
import com.doersweb.onboarding_presentation.shared_composables.ActionButton
import com.doersweb.onboarding_presentation.shared_composables.UnitTextField

@Composable
fun NutrientGoalScreen(
    snackBarState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    nutrientGoalViewModel: NutrientGoalViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        nutrientGoalViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackBar -> {
                    snackBarState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }

                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.what_are_your_nutrient_goals),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = nutrientGoalViewModel.state.carbsRatio, onValueChanged = {
                    nutrientGoalViewModel.onEvent(NutrientGoalEvent.OnCarbEntry(it))
                }, unit = stringResource(
                    id = R.string.percentage_carbs
                )
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = nutrientGoalViewModel.state.proteinRatio,
                onValueChanged ={
                    nutrientGoalViewModel.onEvent(NutrientGoalEvent.OnProteinEntry(it))
                },
                unit = stringResource(
                    id = R.string.percentage_protein
                )
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = nutrientGoalViewModel.state.fatRatio,
                onValueChanged ={
                    nutrientGoalViewModel.onEvent(NutrientGoalEvent.OnFatEntry(it))
                },
                unit = stringResource(
                    id = R.string.percentage_fat
                )
            )
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { nutrientGoalViewModel.onEvent(NutrientGoalEvent.OnNextClick) },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}