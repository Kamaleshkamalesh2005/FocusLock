package com.example.focuslock.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.focuslock.data.FocusLockRepository
import com.example.focuslock.util.AppManager
import com.example.focuslock.util.UsageStatsProvider

class FocusLockViewModelFactory(
    private val repository: FocusLockRepository,
    private val appManager: AppManager? = null,
    private val usageStatsProvider: UsageStatsProvider? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(repository, usageStatsProvider!!) as T
            }
            modelClass.isAssignableFrom(AppSelectionViewModel::class.java) -> {
                AppSelectionViewModel(repository, appManager!!) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
