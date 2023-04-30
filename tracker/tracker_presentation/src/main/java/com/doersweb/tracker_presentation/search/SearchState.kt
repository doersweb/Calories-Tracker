package com.doersweb.tracker_presentation.search

import com.doersweb.tracker_domain.model.TrackableFood

data class SearchState(
    val query: String ="",
    val isHintVisible: Boolean = false,
    val isSearching: Boolean = false,
    val trackableFoods: List<TrackableFoodUiState> = emptyList()
)
