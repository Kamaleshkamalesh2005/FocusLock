package com.example.focuslock.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focuslock.data.FocusLockRepository
import com.example.focuslock.domain.AppInfo
import com.example.focuslock.util.AppManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AppSelectionViewModel(
    private val repository: FocusLockRepository,
    private val appManager: AppManager
) : ViewModel() {

    private val _installedApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val installedApps: StateFlow<List<AppInfo>> = _installedApps.asStateFlow()

    val trackedPackages: StateFlow<Set<String>> = repository.allTrackedApps
        .map { apps -> apps.map { it.packageName }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    init {
        loadInstalledApps()
    }

    private fun loadInstalledApps() {
        viewModelScope.launch {
            _installedApps.value = appManager.getInstalledApps()
        }
    }

    fun toggleAppSelection(app: AppInfo, isSelected: Boolean, limitMinutes: Int = 30) {
        viewModelScope.launch {
            if (isSelected) {
                repository.addTrackedApp(app.packageName, app.appName, limitMinutes)
            } else {
                repository.removeTrackedApp(app.packageName)
            }
        }
    }
}
