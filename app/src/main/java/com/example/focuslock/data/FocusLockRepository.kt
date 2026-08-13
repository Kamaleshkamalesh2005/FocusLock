package com.example.focuslock.data

import com.example.focuslock.domain.AppUsageInfo
import com.example.focuslock.util.UsageStatsProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.*

class FocusLockRepository(
    private val trackedAppDao: TrackedAppDao,
    private val focusSessionDao: FocusSessionDao,
    private val usageRecordDao: UsageRecordDao,
    private val emergencyAccessDao: EmergencyAccessDao,
    private val usageStatsProvider: UsageStatsProvider
) {
    val allTrackedApps = trackedAppDao.getAllTrackedApps()
    val allSessions = focusSessionDao.getAllSessions()
    val completedSessionsCount = focusSessionDao.getCompletedSessionsCount()

    suspend fun addTrackedApp(packageName: String, appName: String, limitMinutes: Int) {
        trackedAppDao.insertTrackedApp(TrackedAppEntity(packageName, appName, limitMinutes))
    }

    suspend fun removeTrackedApp(packageName: String) {
        trackedAppDao.deleteTrackedAppByPackageName(packageName)
    }

    suspend fun startFocusSession(durationMinutes: Int): Long {
        val session = FocusSessionEntity(
            startTime = System.currentTimeMillis(),
            endTime = 0L,
            durationMinutes = durationMinutes
        )
        return focusSessionDao.insertSession(session)
    }

    suspend fun endFocusSession(sessionId: Long, completed: Boolean) {
        val sessions = allSessions.first()
        val session = sessions.find { it.id == sessionId }
        if (session != null) {
            focusSessionDao.updateSession(session.copy(endTime = System.currentTimeMillis(), isCompleted = completed))
        }
    }

    fun getTodayAppUsage(): Flow<List<AppUsageInfo>> {
        return allTrackedApps.map { apps ->
            apps.map { app ->
                val usageMillis = usageStatsProvider.getTodayUsage(app.packageName)
                AppUsageInfo(
                    packageName = app.packageName,
                    appName = app.appName,
                    usageMinutes = (usageMillis / (1000 * 60)).toInt(),
                    dailyLimitMinutes = app.dailyLimitMinutes
                )
            }
        }
    }

    fun getTotalTodayUsageMinutes(): Flow<Int> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return usageRecordDao.getTotalUsageForDate(calendar.timeInMillis)
    }
}
