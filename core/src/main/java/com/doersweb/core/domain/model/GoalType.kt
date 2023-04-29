package com.doersweb.core.domain.model

sealed class GoalType(val type: String){
    object GainWeight: GoalType("gain")
    object KeepWeight: GoalType("keep")
    object LoseWeight: GoalType("lose")


    companion object {
        fun fromString(type: String) : GoalType {
            return when(type) {
                "gain" -> GainWeight
                "lose" -> LoseWeight
                "keep" -> KeepWeight
                else -> LoseWeight
            }
        }
    }
}
