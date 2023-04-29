package com.doersweb.onboarding_presentation.weight

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun WeightScreen(
    snackBarState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    weightViewModel: WeightViewModel= hiltViewModel()
){
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        weightViewModel.uiEvent.collect { event ->
            when(event) {
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
                text = stringResource(id = R.string.whats_your_weight),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(value = weightViewModel.weight, onValueChanged = weightViewModel::onWeightEnter, unit = stringResource(
                id = R.string.unit_kgs
            ))
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { weightViewModel.onNextClick() },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}