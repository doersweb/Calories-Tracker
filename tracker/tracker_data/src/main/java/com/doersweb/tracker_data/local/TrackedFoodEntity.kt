package com.doersweb.tracker_data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trackedfoodentity")
data class TrackedFoodEntity(
    val name: String,
    val carbs: Int,
    val protein: Int,
    val fat: Int,
    val imageUrl: String?,
    val type: String,
    val amount: Int,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    val calories: Int,
    @PrimaryKey val id: Int? = null
)