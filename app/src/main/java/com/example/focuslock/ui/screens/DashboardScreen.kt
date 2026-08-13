package com.example.focuslock.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.focuslock.domain.AppUsageInfo
import com.example.focuslock.ui.DashboardViewModel
import com.example.focuslock.ui.screens.PermissionScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onSelectApps: () -> Unit,
    onStatistics: () -> Unit,
    onFocusSession: () -> Unit,
    onSettings: () -> Unit
) {
    val appUsageList by viewModel.appUsageList.collectAsState()
    val totalUsage by viewModel.totalUsageMinutes.collectAsState()
    val completedSessions by viewModel.completedSessionsCount.collectAsState()
    val hasPermission by viewModel.hasPermission.collectAsState()

    if (!hasPermission) {
        PermissionScreen(onPermissionGranted = { viewModel.checkPermission() })
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FocusLock Dashboard") },
                actions = {
                    IconButton(onClick = onSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SummaryCard(totalUsage, completedSessions)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tracked Apps", style = MaterialTheme.typography.titleLarge)
                TextButton(onClick = onSelectApps) {
                    Text("Select Apps")
                }
            }
            
            if (appUsageList.isEmpty()) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text("No apps selected yet", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(appUsageList) { app ->
                        AppUsageItem(app)
                    }
                }
            }
            
            Button(
                onClick = onFocusSession,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                Text("Start Focus Session")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedButton(
                onClick = onStatistics,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                Text("View Statistics")
            }
        }
    }
}

@Composable
fun SummaryCard(totalUsage: Int, completedSessions: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Today's Summary", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                SummaryItem("Total Usage", "${totalUsage}m")
                SummaryItem("Focus Sessions", "$completedSessions")
            }
        }
    }
}

@Composable
fun SummaryItem(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.bodySmall)
        Text(value, style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun AppUsageItem(app: AppUsageInfo) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Text(app.appName, style = MaterialTheme.typography.bodyLarge)
                Text("${app.usageMinutes} / ${app.dailyLimitMinutes} min", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = app.progress,
                modifier = Modifier.fillMaxWidth(),
                color = if (app.isLimitExceeded) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        }
    }
}
