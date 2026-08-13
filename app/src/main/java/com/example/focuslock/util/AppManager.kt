package com.example.focuslock.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.example.focuslock.domain.AppInfo

class AppManager(private val context: Context) {

    fun getInstalledApps(): List<AppInfo> {
        val pm = context.packageManager
        val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        
        return apps.filter { 
            (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 || 
            (it.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0
        }.map { 
            AppInfo(
                packageName = it.packageName,
                appName = pm.getApplicationLabel(it).toString(),
                icon = pm.getApplicationIcon(it)
            )
        }.sortedBy { it.appName }
    }
}
