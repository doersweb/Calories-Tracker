package com.doersweb.tracker_presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doersweb.core.domain.usecases.FilterOutDecimals
import com.doersweb.core.util.UiEvent
import com.doersweb.core.util.UiText
import com.doersweb.tracker_domain.usecase.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.doersweb.core.R

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterOutDecimals: FilterOutDecimals
): ViewModel() {
    var state by mutableStateOf(SearchState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent){
        when(event){
            is SearchEvent.OnSearch -> {
                viewModelScope.launch {
                    state = state.copy(
                        isSearching = true,
                        trackableFoods = emptyList()
                    )
                    trackerUseCases.searchFood(
                        query = state.query
                    ).onSuccess {foods->
                        state = state.copy(
                            trackableFoods = foods.map {
                                TrackableFoodUiState(
                                    it
                                )
                            },
                            isSearching = false,
                            query = ""
                        )
                    }.onFailure {
                        state = state.copy(
                            isSearching = false
                        )
                        _uiEvent.send(
                            UiEvent.ShowSnackBar(
                                UiText.StringResource(R.string.error_sometging_wrong)
                            )
                        )
                    }
                }
            }

            is SearchEvent.OnSearchFocusChange -> {
                state = state.copy(
                    isHintVisible = !event.isFocused && state.query.isBlank()
                )
            }

            is SearchEvent.OnQueryChange -> {
                state = state.copy(
                    query = event.query
                )
            }

            is SearchEvent.OnToggleTrackableFood -> {
                state = state.copy(
                    trackableFoods = state.trackableFoods.map {
                        if(it.food == event.food) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else  it
                    }
                )
            }

            is SearchEvent.OnTrackedFoodClick -> {
                viewModelScope.launch {
                    val uiState = state.trackableFoods.find { it.food == event.food }
                    trackerUseCases.trackFood(
                        food = uiState?.food ?: return@launch,
                        amount = uiState    .amount.toIntOrNull() ?: return@launch,
                        mealType = event.mealType,
                        date = event.date
                    )
                    _uiEvent.send(UiEvent.NavigateUp)
                }
            }

            is SearchEvent.OnAmountChangeForTrackableFood -> {
                state = state.copy(
                    trackableFoods = state.trackableFoods.map {
                        if(it.food == event.food) {
                            it.copy(amount = filterOutDecimals(event.amount))
                        } else  it
                    }
                )
            }
        }
    }
}