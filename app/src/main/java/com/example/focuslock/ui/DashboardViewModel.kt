package com.example.focuslock.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focuslock.data.FocusLockRepository
import com.example.focuslock.domain.AppUsageInfo
import com.example.focuslock.util.UsageStatsProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel(
    private val repository: FocusLockRepository,
    private val usageStatsProvider: UsageStatsProvider
) : ViewModel() {

    private val _hasPermission = MutableStateFlow(usageStatsProvider.hasUsageAccessPermission())
    val hasPermission: StateFlow<Boolean> = _hasPermission.asStateFlow()

    fun checkPermission() {
        _hasPermission.value = usageStatsProvider.hasUsageAccessPermission()
    }

    val appUsageList: StateFlow<List<AppUsageInfo>> = repository.getTodayAppUsage()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalUsageMinutes: StateFlow<Int> = repository.getTotalTodayUsageMinutes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val completedSessionsCount: StateFlow<Int> = repository.completedSessionsCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
}
