package com.doersweb.core.domain.preferences

import com.doersweb.core.domain.model.ActivityLevel
import com.doersweb.core.domain.model.Gender
import com.doersweb.core.domain.model.GoalType
import com.doersweb.core.domain.model.UserInfo

interface Preferences {

    fun saveGender(gender: Gender)
    fun saveAge(age: Int)
    fun saveWeight(weight: Float)
    fun saveHeight(height: Int)
    fun saveActivityLevel(level: ActivityLevel)
    fun saveGoalType(type: GoalType)
    fun saveCarbRatio(ratio: Float)
    fun saveFatRatio(ratio: Float)
    fun saveProteinRatio(ratio: Float)

    fun loadUserInfo() : UserInfo

    fun saveShouldShowOnboarding(show: Boolean)
    fun loadShouldShowOnboarding(): Boolean
    companion object {
        const val KEY_GENDER = "gender"
        const val KEY_AGE = "age"
        const val KEY_WEIGHT = "weight"
        const val KEY_HEIGHT = "height"
        const val KEY_ACTIVITY_LEVEL = "activity_level"
        const val KEY_GOAL_TYPE = "goal_type"
        const val KEY_CARB_RATIO = "carb_ratio"
        const val KEY_PROTEIN_RATIO = "protein_ratio"
        const val KEY_FAT_RATIO = "fat_ratio"
        const val KEY_SHOW_ONBOARDING = "show_onboarding"
    }
}