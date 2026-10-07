package com.example.data.repository

import com.example.data.local.DailySadhana
import com.example.data.local.JapDao
import com.example.data.local.JapRecord
import com.example.data.local.LifetimeMantraSummary
import com.example.data.local.MantraSadhana
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class JapRepository(private val japDao: JapDao) {

    fun getTodayDateString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getDailySadhanaFlow(dateString: String): Flow<DailySadhana?> {
        return japDao.getDailySadhanaFlow(dateString)
    }

    fun getRecentDailySadhanaFlow(): Flow<List<DailySadhana>> {
        return japDao.getRecentDailySadhanaFlow()
    }

    fun getRecentJapRecordsFlow(): Flow<List<JapRecord>> {
        return japDao.getRecentJapRecordsFlow()
    }

    fun getTotalLifetimeBeadsFlow(): Flow<Int> {
        return japDao.getTotalLifetimeBeadsFlow()
    }

    fun getTotalLifetimeMalasFlow(): Flow<Int> {
        return japDao.getTotalLifetimeMalasFlow()
    }

    // --- Mantra-wise separate tracking flows ---
    fun getTodayMantraSadhanaFlow(dateString: String): Flow<List<MantraSadhana>> {
        return japDao.getTodayMantraSadhanaFlow(dateString)
    }

    fun getMantraSadhanaFlow(dateString: String, mantraName: String): Flow<MantraSadhana?> {
        return japDao.getMantraSadhanaFlow(dateString, mantraName)
    }

    fun getLifetimeMantraBreakdownFlow(): Flow<List<LifetimeMantraSummary>> {
        return japDao.getLifetimeMantraBreakdownFlow()
    }

    suspend fun getDailySadhana(dateString: String): DailySadhana? {
        return japDao.getDailySadhana(dateString)
    }

    suspend fun getMantraSadhana(dateString: String, mantraName: String): MantraSadhana? {
        return japDao.getMantraSadhana(dateString, mantraName)
    }

    suspend fun addJapCount(
        dateString: String,
        mantraName: String,
        beadsCount: Int,
        targetMalas: Int
    ) {
        // 1. Update Daily Overall Sadhana
        val existing = japDao.getDailySadhana(dateString) ?: DailySadhana(
            dateString = dateString,
            totalBeads = 0,
            totalMalas = 0,
            sankalpaTargetMalas = targetMalas
        )

        val newTotalBeads = (existing.totalBeads + beadsCount).coerceAtLeast(0)
        val newTotalMalas = newTotalBeads / 108
        val isCompleted = newTotalMalas >= existing.sankalpaTargetMalas

        val updated = existing.copy(
            totalBeads = newTotalBeads,
            totalMalas = newTotalMalas,
            isSankalpaCompleted = isCompleted,
            lastUpdatedTimestamp = System.currentTimeMillis()
        )
        japDao.insertOrUpdateDailySadhana(updated)

        // 2. Update Mantra-Specific Sadhana (Separate beads & malas for this specific mantra)
        val existingMantra = japDao.getMantraSadhana(dateString, mantraName) ?: MantraSadhana(
            dateString = dateString,
            mantraName = mantraName,
            totalBeads = 0,
            totalMalas = 0
        )
        val newMantraBeads = (existingMantra.totalBeads + beadsCount).coerceAtLeast(0)
        val newMantraMalas = newMantraBeads / 108

        japDao.insertOrUpdateMantraSadhana(
            existingMantra.copy(
                totalBeads = newMantraBeads,
                totalMalas = newMantraMalas,
                lastChantedTimestamp = System.currentTimeMillis()
            )
        )

        // 3. Insert Timeline Record
        japDao.insertJapRecord(
            JapRecord(
                dateString = dateString,
                mantraName = mantraName,
                beadsAdded = beadsCount,
                malasCompleted = newMantraMalas
            )
        )
    }

    suspend fun backfillMantraSadhanaIfNeeded() {
        val count = japDao.getMantraSadhanaCount()
        if (count == 0) {
            val aggregated = japDao.getAggregatedJapRecords()
            for (rec in aggregated) {
                if (rec.totalBeads > 0) {
                    japDao.insertOrUpdateMantraSadhana(
                        MantraSadhana(
                            dateString = rec.dateString,
                            mantraName = rec.mantraName,
                            totalBeads = rec.totalBeads,
                            totalMalas = rec.totalBeads / 108
                        )
                    )
                }
            }
        }
    }

    suspend fun updateSankalpaTarget(dateString: String, targetMalas: Int) {
        val existing = japDao.getDailySadhana(dateString) ?: DailySadhana(
            dateString = dateString,
            totalBeads = 0,
            totalMalas = 0,
            sankalpaTargetMalas = targetMalas
        )
        val isCompleted = existing.totalMalas >= targetMalas
        japDao.insertOrUpdateDailySadhana(
            existing.copy(
                sankalpaTargetMalas = targetMalas,
                isSankalpaCompleted = isCompleted
            )
        )
    }

    suspend fun resetTodaySadhana(dateString: String, targetMalas: Int) {
        val reset = DailySadhana(
            dateString = dateString,
            totalBeads = 0,
            totalMalas = 0,
            sankalpaTargetMalas = targetMalas,
            isSankalpaCompleted = false,
            lastUpdatedTimestamp = System.currentTimeMillis()
        )
        japDao.insertOrUpdateDailySadhana(reset)
    }

    suspend fun deleteMantraSadhana(dateString: String, mantraName: String) {
        japDao.deleteMantraSadhana(dateString, mantraName)
    }
}
