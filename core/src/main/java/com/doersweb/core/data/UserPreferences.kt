package com.doersweb.core.data

import android.content.SharedPreferences
import com.doersweb.core.domain.model.ActivityLevel
import com.doersweb.core.domain.model.Gender
import com.doersweb.core.domain.model.GoalType
import com.doersweb.core.domain.model.UserInfo
import com.doersweb.core.domain.preferences.Preferences
import com.doersweb.core.domain.preferences.Preferences.Companion.KEY_ACTIVITY_LEVEL
import com.doersweb.core.domain.preferences.Preferences.Companion.KEY_AGE
import com.doersweb.core.domain.preferences.Preferences.Companion.KEY_CARB_RATIO
import com.doersweb.core.domain.preferences.Preferences.Companion.KEY_FAT_RATIO
import com.doersweb.core.domain.preferences.Preferences.Companion.KEY_GENDER
import com.doersweb.core.domain.preferences.Preferences.Companion.KEY_GOAL_TYPE
import com.doersweb.core.domain.preferences.Preferences.Companion.KEY_HEIGHT
import com.doersweb.core.domain.preferences.Preferences.Companion.KEY_PROTEIN_RATIO
import com.doersweb.core.domain.preferences.Preferences.Companion.KEY_SHOW_ONBOARDING
import com.doersweb.core.domain.preferences.Preferences.Companion.KEY_WEIGHT

class UserPreferences(
    private val sharedPref: SharedPreferences
): Preferences {
    override fun saveGender(gender: Gender) {
        sharedPref.edit()
            .putString(KEY_GENDER, gender.name)
            .apply()
    }

    override fun saveAge(age: Int) {
        sharedPref.edit()
            .putInt(KEY_AGE, age)
            .apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPref.edit()
            .putFloat(KEY_WEIGHT, weight)
            .apply()
    }

    override fun saveHeight(height: Int) {
        sharedPref.edit()
            .putInt(KEY_HEIGHT, height)
            .apply()
    }

    override fun saveActivityLevel(activityLevel: ActivityLevel) {
        sharedPref.edit()
            .putString(KEY_ACTIVITY_LEVEL, activityLevel.level)
            .apply()
    }

    override fun saveGoalType(goalType: GoalType) {
        sharedPref.edit()
            .putString(KEY_GOAL_TYPE, goalType.type)
            .apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(KEY_CARB_RATIO, ratio)
            .apply()
    }

    override fun saveFatRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(KEY_FAT_RATIO, ratio)
            .apply()
    }

    override fun saveProteinRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(KEY_PROTEIN_RATIO, ratio)
            .apply()
    }

    override fun loadUserInfo(): UserInfo {
        val genderS = sharedPref.getString(KEY_GENDER, null)
        val age = sharedPref.getInt(Preferences.KEY_AGE, -1)
        val height = sharedPref.getInt(Preferences.KEY_HEIGHT, -1)
        val weight = sharedPref.getFloat(Preferences.KEY_WEIGHT, -1f)
        val activityLevelString = sharedPref.getString(Preferences.KEY_ACTIVITY_LEVEL, null)
        val goalType = sharedPref.getString(Preferences.KEY_GOAL_TYPE, null)
        val carbRatio = sharedPref.getFloat(Preferences.KEY_CARB_RATIO, -1f)
        val proteinRatio = sharedPref.getFloat(Preferences.KEY_PROTEIN_RATIO, -1f)
        val fatRatio = sharedPref.getFloat(Preferences.KEY_FAT_RATIO, -1f)

        return UserInfo(
            gender = Gender.fromString(genderS ?: "male"),
            age = age,
            weight = weight,
            height = height,
            activityLevel = ActivityLevel.fromString(activityLevelString ?: "medium"),
            goalType = GoalType.fromString(goalType ?: "keep"),
            carbRatio = carbRatio,
            proteinRatio = proteinRatio,
            fatRatio = fatRatio
        )
    }

    override fun saveShouldShowOnboarding(show: Boolean) {
        sharedPref.edit()
            .putBoolean(KEY_SHOW_ONBOARDING, show)
            .apply()
    }

    override fun loadShouldShowOnboarding(): Boolean {
        return sharedPref.getBoolean(KEY_SHOW_ONBOARDING, true)
    }
}