package com.doersweb.tracker_presentation.search

import com.doersweb.tracker_domain.model.TrackableFood

data class TrackableFoodUiState(
    val food: TrackableFood,
    val isExpanded: Boolean = false,
    val amount: String=""
)
