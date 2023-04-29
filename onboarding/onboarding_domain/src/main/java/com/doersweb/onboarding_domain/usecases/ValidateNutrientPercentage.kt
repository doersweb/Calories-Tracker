package com.doersweb.onboarding_domain.usecases

import com.doersweb.core.util.UiText
import com.doersweb.core.R

class ValidateNutrientPercentage {
    operator fun invoke(
        carbsRatioText: String,
        proteinRatioText: String,
        fatRatioText: String
    ): Result {
        val carbsRatio = carbsRatioText.toIntOrNull()
        val proteinRatio = proteinRatioText.toIntOrNull()
        val fatRatio = fatRatioText.toIntOrNull()
        if(carbsRatio == null || proteinRatio == null || fatRatio == null) {
            return Result.Invalid(
                message = UiText.StringResource(R.string.error_invalid_values)
            )
        }
        if(carbsRatio + proteinRatio + fatRatio != 100) {
            return Result.Invalid(
                message = UiText.StringResource(R.string.error_not_100_percent)
            )
        }
        return Result.Valid(
            carbsRatio / 100f,
            proteinRatio / 100f,
            fatRatio / 100f
        )
    }

    sealed class Result{
        data class Valid(
            val carbsRatio: Float,
            val proteinRatio: Float,
            val fatRatio: Float
        ): Result()

        data class Invalid(val message: UiText): Result()
    }
}