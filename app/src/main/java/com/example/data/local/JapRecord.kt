package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jap_records")
data class JapRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dateString: String, // format "yyyy-MM-dd"
    val mantraName: String,
    val beadsAdded: Int,
    val malasCompleted: Int,
    val timestamp: Long = System.currentTimeMillis()
)
