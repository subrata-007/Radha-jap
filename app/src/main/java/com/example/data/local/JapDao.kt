package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JapDao {
    @Query("SELECT * FROM daily_sadhana WHERE dateString = :dateString LIMIT 1")
    fun getDailySadhanaFlow(dateString: String): Flow<DailySadhana?>

    @Query("SELECT * FROM daily_sadhana WHERE dateString = :dateString LIMIT 1")
    suspend fun getDailySadhana(dateString: String): DailySadhana?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateDailySadhana(dailySadhana: DailySadhana)

    @Query("SELECT * FROM daily_sadhana ORDER BY dateString DESC LIMIT 30")
    fun getRecentDailySadhanaFlow(): Flow<List<DailySadhana>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJapRecord(record: JapRecord): Long

    @Query("SELECT * FROM jap_records ORDER BY timestamp DESC LIMIT 50")
    fun getRecentJapRecordsFlow(): Flow<List<JapRecord>>

    @Query("SELECT COALESCE(SUM(totalBeads), 0) FROM daily_sadhana")
    fun getTotalLifetimeBeadsFlow(): Flow<Int>

    @Query("SELECT COALESCE(SUM(totalMalas), 0) FROM daily_sadhana")
    fun getTotalLifetimeMalasFlow(): Flow<Int>

    @Query("DELETE FROM jap_records WHERE id = (SELECT MAX(id) FROM jap_records)")
    suspend fun deleteLatestRecord()

    @Query("DELETE FROM jap_records")
    suspend fun clearAllRecords()

    // --- Mantra-wise separate chanting queries ---
    @Query("SELECT * FROM mantra_sadhana WHERE dateString = :dateString ORDER BY totalBeads DESC")
    fun getTodayMantraSadhanaFlow(dateString: String): Flow<List<MantraSadhana>>

    @Query("SELECT * FROM mantra_sadhana WHERE dateString = :dateString AND mantraName = :mantraName LIMIT 1")
    fun getMantraSadhanaFlow(dateString: String, mantraName: String): Flow<MantraSadhana?>

    @Query("SELECT * FROM mantra_sadhana WHERE dateString = :dateString AND mantraName = :mantraName LIMIT 1")
    suspend fun getMantraSadhana(dateString: String, mantraName: String): MantraSadhana?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateMantraSadhana(mantraSadhana: MantraSadhana)

    @Query("""
        SELECT 
            mantraName, 
            SUM(totalBeads) as lifetimeBeads, 
            SUM(totalBeads) / 108 as lifetimeMalas 
        FROM mantra_sadhana 
        GROUP BY mantraName 
        ORDER BY lifetimeBeads DESC
    """)
    fun getLifetimeMantraBreakdownFlow(): Flow<List<LifetimeMantraSummary>>

    @Query("SELECT COUNT(*) FROM mantra_sadhana")
    suspend fun getMantraSadhanaCount(): Int

    @Query("SELECT dateString, mantraName, SUM(beadsAdded) as totalBeads FROM jap_records GROUP BY dateString, mantraName")
    suspend fun getAggregatedJapRecords(): List<JapRecordAggregated>

    @Query("DELETE FROM mantra_sadhana WHERE dateString = :dateString AND mantraName = :mantraName")
    suspend fun deleteMantraSadhana(dateString: String, mantraName: String)
}
