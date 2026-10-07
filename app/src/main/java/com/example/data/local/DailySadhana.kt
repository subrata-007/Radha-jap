package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_sadhana")
data class DailySadhana(
    @PrimaryKey
    val dateString: String, // format "yyyy-MM-dd"
    val totalBeads: Int = 0,
    val totalMalas: Int = 0,
    val sankalpaTargetMalas: Int = 16,
    val isSankalpaCompleted: Boolean = false,
    val durationSeconds: Long = 0,
    val lastUpdatedTimestamp: Long = System.currentTimeMillis()
)
