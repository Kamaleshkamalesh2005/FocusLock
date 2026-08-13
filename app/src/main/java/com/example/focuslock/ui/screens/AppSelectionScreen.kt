package com.example.focuslock.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.focuslock.domain.AppInfo
import com.example.focuslock.ui.AppSelectionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSelectionScreen(
    viewModel: AppSelectionViewModel,
    onBack: () -> Unit
) {
    val apps by viewModel.installedApps.collectAsState()
    val trackedPackages by viewModel.trackedPackages.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    
    val filteredApps = if (searchQuery.isEmpty()) {
        apps
    } else {
        apps.filter { it.appName.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Apps") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search apps...") },
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredApps) { app ->
                    AppSelectionItem(
                        app = app,
                        isSelected = trackedPackages.contains(app.packageName),
                        onToggle = { isSelected ->
                            viewModel.toggleAppSelection(app, isSelected)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AppSelectionItem(
    app: AppInfo,
    isSelected: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(app.appName, style = MaterialTheme.typography.bodyLarge)
                Text(app.packageName, style = MaterialTheme.typography.bodySmall)
            }
            Checkbox(
                checked = isSelected,
                onCheckedChange = onToggle
            )
        }
    }
}
