package com.example.data.local

import androidx.room.Entity

/**
 * Stores separate daily count of beads and malas chanted for each distinct mantra.
 */
@Entity(
    tableName = "mantra_sadhana",
    primaryKeys = ["dateString", "mantraName"]
)
data class MantraSadhana(
    val dateString: String,      // format "yyyy-MM-dd"
    val mantraName: String,      // e.g. "श्री राधा", "हरे कृष्ण...", etc.
    val totalBeads: Int = 0,     // Total beads chanted for this mantra on this date
    val totalMalas: Int = 0,     // totalBeads / 108
    val lastChantedTimestamp: Long = System.currentTimeMillis()
) {
    val currentBeadInMala: Int get() = totalBeads % 108
    val completedMalas: Int get() = totalBeads / 108
}

/**
 * Summary query projection for lifetime chanting per mantra.
 */
data class LifetimeMantraSummary(
    val mantraName: String,
    val lifetimeBeads: Int,
    val lifetimeMalas: Int
)

/**
 * Aggregated projection for one-time historical backfill from jap_records if available.
 */
data class JapRecordAggregated(
    val dateString: String,
    val mantraName: String,
    val totalBeads: Int
)
