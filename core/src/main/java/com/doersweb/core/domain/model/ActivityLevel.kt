package com.doersweb.core.domain.model

sealed class ActivityLevel(val level: String){
    object Low: ActivityLevel("low")
    object Medium: ActivityLevel("medium")
    object High: ActivityLevel("high")

    companion object {
        fun fromString(level: String) : ActivityLevel {
            return when(level) {
                "low" -> Low
                "medium" -> Medium
                "high" -> High
                else -> Medium
            }
        }
    }
}
