package com.example.focuslock.domain

import android.graphics.drawable.Drawable

data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable? = null
)

data class AppUsageInfo(
    val packageName: String,
    val appName: String,
    val usageMinutes: Int,
    val dailyLimitMinutes: Int,
    val icon: Drawable? = null
) {
    val progress: Float
        get() = if (dailyLimitMinutes > 0) (usageMinutes.toFloat() / dailyLimitMinutes).coerceAtMost(1f) else 0f
    
    val isLimitExceeded: Boolean
        get() = usageMinutes >= dailyLimitMinutes && dailyLimitMinutes > 0
}
