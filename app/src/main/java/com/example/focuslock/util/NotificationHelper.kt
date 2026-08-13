package com.example.focuslock.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.focuslock.R

class NotificationHelper(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val CHANNEL_ID = "focuslock_notifications"
        const val CHANNEL_NAME = "FocusLock Alerts"
        const val NOTIFICATION_ID_LIMIT = 101
        const val NOTIFICATION_ID_FOCUS = 102
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for app usage limits and focus sessions"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showLimitWarning(appName: String, usedMinutes: Int, limitMinutes: Int) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Use default for now
            .setContentTitle("Usage Limit Warning")
            .setContentText("You have used $usedMinutes of $limitMinutes minutes for $appName.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(appName.hashCode(), builder.build())
    }

    fun showLimitReached(appName: String) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Limit Reached")
            .setContentText("Your daily limit for $appName has been reached.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(appName.hashCode(), builder.build())
    }
}
