package com.doersweb.onboarding_presentation.age

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
class AgeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDecimals: FilterOutDecimals
): ViewModel() {

    var age by mutableStateOf("20")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeEnter(age: String){
        if(age.length<=3) {
            this.age = filterOutDecimals(age)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val ageNumber = age.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        UiText.StringResource(R.string.error_age_cannot_be_empty)
                    )
                )
                return@launch
            }

            preferences.saveAge(ageNumber)
            _uiEvent.send(UiEvent.Navigate(Routes.HEIGHT))
        }
    }
}