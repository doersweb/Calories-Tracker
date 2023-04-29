package com.doersweb.onboarding_presentation.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.doersweb.core.util.UiEvent
import com.doersweb.core_ui.LocalSpacing
import com.doersweb.core.R
import com.doersweb.core.domain.model.ActivityLevel
import com.doersweb.core.domain.model.Gender
import com.doersweb.onboarding_presentation.shared_composables.ActionButton
import com.doersweb.onboarding_presentation.shared_composables.SelectableButton
import kotlinx.coroutines.flow.collect

@Composable
fun ActivityLevelScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    activityViewModel: ActivityLevelViewModel = hiltViewModel()
){
    val spacing = LocalSpacing.current
    LaunchedEffect(key1 = true) {
        activityViewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
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
                text = stringResource(id = R.string.whats_your_activity_level),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row() {
                SelectableButton(
                    text = stringResource(id = R.string.low),
                    isSelected = activityViewModel.selectedActivityLevel is ActivityLevel.Low,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    selectedTextColor = Color.White,
                    onClick = {
                        activityViewModel.onActivityLevelSelected(ActivityLevel.Low)
                    }
                )
                
                Spacer(modifier = Modifier.width(spacing.spaceSmall))

                SelectableButton(
                    text = stringResource(id = R.string.medium),
                    isSelected = activityViewModel.selectedActivityLevel is ActivityLevel.Medium,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    selectedTextColor = Color.White,
                    onClick = {
                        activityViewModel.onActivityLevelSelected(ActivityLevel.Medium)
                    }
                )

                Spacer(modifier = Modifier.width(spacing.spaceSmall))

                SelectableButton(
                    text = stringResource(id = R.string.high),
                    isSelected = activityViewModel.selectedActivityLevel is ActivityLevel.High,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    selectedTextColor = Color.White,
                    onClick = {
                        activityViewModel.onActivityLevelSelected(ActivityLevel.High)
                    }
                )
            }
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { activityViewModel.onNextClick() },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}