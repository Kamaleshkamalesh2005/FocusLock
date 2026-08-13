# FocusLock

FocusLock is a productivity application designed to help users manage their social media usage through tracking, daily limits, and focus sessions.

## Features

- **App Usage Monitoring**: Track time spent on selected applications.
- **Daily Limits**: Set maximum usage time for distracting apps.
- **Focus Sessions**: Dedicated blocks of time for deep work.
- **Statistics**: View daily and weekly usage trends.
- **Local Privacy**: All usage data is processed and stored on-device.

## Technology Stack

- **Kotlin**: Modern and concise language for Android development.
- **Jetpack Compose**: Declarative UI toolkit.
- **Room Database**: Local persistence.
- **MVVM**: Clean and scalable architecture.
- **UsageStatsManager**: Android system API for usage tracking.

## Android Permissions

- `PACKAGE_USAGE_STATS`: Required to monitor app usage.
- `POST_NOTIFICATIONS`: Required for usage alerts on Android 13+.
- `QUERY_ALL_PACKAGES`: Required to list installed apps for selection.

## How it Works

FocusLock uses the Android `UsageStatsManager` to query the foreground time of selected packages within specified intervals (e.g., daily). This data is stored locally in a Room database to provide statistics and trigger notifications when limits are approached.

## Limitations

- **Platform Restrictions**: Android prevents apps from forcibly killing or blocking other apps directly for security. FocusLock uses strong notifications and warnings as alternatives.
- **Battery Optimization**: Background tracking may be affected by device-specific power saving modes.

## How to Run

1. Clone the repository.
2. Open in Android Studio.
3. Sync Gradle and Build.
4. Run on a physical device (Usage statistics are best tested on real devices).
