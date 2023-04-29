package com.doersweb.onboarding_presentation.age

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.doersweb.core.R
import com.doersweb.core.util.UiEvent
import com.doersweb.core_ui.LocalSpacing
import com.doersweb.onboarding_presentation.shared_composables.ActionButton
import com.doersweb.onboarding_presentation.shared_composables.UnitTextField

@Composable
fun AgeScreen(
    snackBarState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    ageViewModel: AgeViewModel = hiltViewModel()
){
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        ageViewModel.uiEvent.collect { event ->
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
                text = stringResource(id = R.string.whats_your_age),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(value = ageViewModel.age, onValueChanged = ageViewModel::onAgeEnter, unit = stringResource(
                id = R.string.unit_years
            ))
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { ageViewModel.onNextClick() },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}