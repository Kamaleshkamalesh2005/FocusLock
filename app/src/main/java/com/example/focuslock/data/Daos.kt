package com.example.focuslock.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackedAppDao {
    @Query("SELECT * FROM tracked_apps")
    fun getAllTrackedApps(): Flow<List<TrackedAppEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackedApp(app: TrackedAppEntity)

    @Delete
    suspend fun deleteTrackedApp(app: TrackedAppEntity)

    @Query("SELECT * FROM tracked_apps WHERE packageName = :packageName")
    suspend fun getTrackedApp(packageName: String): TrackedAppEntity?

    @Query("DELETE FROM tracked_apps WHERE packageName = :packageName")
    suspend fun deleteTrackedAppByPackageName(packageName: String)
}

@Dao
interface FocusSessionDao {
    @Query("SELECT * FROM focus_sessions ORDER BY startTime DESC")
    fun getAllSessions(): Flow<List<FocusSessionEntity>>

    @Insert
    suspend fun insertSession(session: FocusSessionEntity): Long

    @Update
    suspend fun updateSession(session: FocusSessionEntity)

    @Query("SELECT COUNT(*) FROM focus_sessions WHERE isCompleted = 1")
    fun getCompletedSessionsCount(): Flow<Int>
}

@Dao
interface UsageRecordDao {
    @Query("SELECT * FROM usage_records WHERE date = :date")
    fun getUsageForDate(date: Long): Flow<List<UsageRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsageRecord(record: UsageRecordEntity)

    @Query("SELECT COALESCE(SUM(usageMinutes), 0) FROM usage_records WHERE date = :date")
    fun getTotalUsageForDate(date: Long): Flow<Int>
}

@Dao
interface EmergencyAccessDao {
    @Insert
    suspend fun insertEmergencyAccess(access: EmergencyAccessEntity)

    @Query("SELECT * FROM emergency_access ORDER BY timestamp DESC")
    fun getAllEmergencyAccess(): Flow<List<EmergencyAccessEntity>>
}
