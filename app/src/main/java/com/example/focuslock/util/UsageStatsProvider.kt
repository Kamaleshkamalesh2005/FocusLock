package com.example.focuslock.util

import android.app.usage.UsageStatsManager
import android.content.Context
import java.util.*

class UsageStatsProvider(private val context: Context) {

    private val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    fun getAppUsage(packageName: String, startTime: Long, endTime: Long): Long {
        val stats = usageStatsManager.queryAndAggregateUsageStats(startTime, endTime)
        return stats[packageName]?.totalTimeInForeground ?: 0L
    }

    fun getTodayUsage(packageName: String): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis
        val now = System.currentTimeMillis()
        
        return getAppUsage(packageName, startOfDay, now)
    }

    fun hasUsageAccessPermission(): Boolean {
        val stats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            System.currentTimeMillis() - 1000 * 60,
            System.currentTimeMillis()
        )
        return stats.isNotEmpty()
    }
}
