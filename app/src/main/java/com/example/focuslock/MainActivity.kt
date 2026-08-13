package com.example.focuslock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.focuslock.data.AppDatabase
import com.example.focuslock.data.FocusLockRepository
import com.example.focuslock.ui.AppSelectionViewModel
import com.example.focuslock.ui.DashboardViewModel
import com.example.focuslock.ui.FocusLockNavGraph
import com.example.focuslock.ui.FocusLockViewModelFactory
import com.example.focuslock.ui.theme.FocusLockTheme
import com.example.focuslock.util.AppManager
import com.example.focuslock.util.UsageStatsProvider

class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getDatabase(this) }
    private val usageStatsProvider by lazy { UsageStatsProvider(this) }
    private val appManager by lazy { AppManager(this) }
    private val repository by lazy { 
        FocusLockRepository(
            database.trackedAppDao(),
            database.focusSessionDao(),
            database.usageRecordDao(),
            database.emergencyAccessDao(),
            usageStatsProvider
        ) 
    }

    private val dashboardViewModel: DashboardViewModel by viewModels {
        FocusLockViewModelFactory(repository, usageStatsProvider = usageStatsProvider)
    }

    private val appSelectionViewModel: AppSelectionViewModel by viewModels {
        FocusLockViewModelFactory(repository, appManager = appManager)
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.checkPermission()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FocusLockTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    FocusLockNavGraph(
                        navController = navController,
                        dashboardViewModel = dashboardViewModel,
                        appSelectionViewModel = appSelectionViewModel
                    )
                }
            }
        }
    }
}
