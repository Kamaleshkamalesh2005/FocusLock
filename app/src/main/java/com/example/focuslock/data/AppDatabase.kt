package com.example.focuslock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        TrackedAppEntity::class,
        FocusSessionEntity::class,
        UsageRecordEntity::class,
        EmergencyAccessEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackedAppDao(): TrackedAppDao
    abstract fun focusSessionDao(): FocusSessionDao
    abstract fun usageRecordDao(): UsageRecordDao
    abstract fun emergencyAccessDao(): EmergencyAccessDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "focuslock_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
