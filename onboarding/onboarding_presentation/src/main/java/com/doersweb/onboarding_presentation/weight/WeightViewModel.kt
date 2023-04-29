package com.doersweb.onboarding_presentation.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doersweb.core.domain.preferences.Preferences
import com.doersweb.core.domain.usecases.FilterOutDecimals
import com.doersweb.core.util.UiEvent
import com.doersweb.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.doersweb.core.R
import com.doersweb.core.navigation.Routes

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val preferences: Preferences
): ViewModel() {

    var weight by mutableStateOf("50.0")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onWeightEnter(weight: String){
        if(weight.length<=5) {
            this.weight = weight
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val weightNumber = weight.toFloatOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        UiText.StringResource(R.string.error_weight_cannot_be_empty)
                    )
                )
                return@launch
            }

            preferences.saveWeight(weightNumber)
            _uiEvent.send(UiEvent.Navigate(Routes.ACTIVITY))
        }
    }
}