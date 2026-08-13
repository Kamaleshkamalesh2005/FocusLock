package com.example.focuslock.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracked_apps")
data class TrackedAppEntity(
    @PrimaryKey val packageName: String,
    val appName: String,
    val dailyLimitMinutes: Int,
    val isEnabled: Boolean = true
)

@Entity(tableName = "focus_sessions")
data class FocusSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val startTime: Long,
    val endTime: Long, // Use 0 for active
    val durationMinutes: Int,
    val isCompleted: Boolean = false
)

@Entity(tableName = "usage_records")
data class UsageRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val packageName: String,
    val date: Long, // Start of day timestamp
    val usageMinutes: Int
)

@Entity(tableName = "emergency_access")
data class EmergencyAccessEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val packageName: String,
    val timestamp: Long,
    val durationMinutes: Int = 5
)
