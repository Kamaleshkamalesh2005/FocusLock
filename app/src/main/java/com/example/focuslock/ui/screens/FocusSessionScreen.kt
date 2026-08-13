package com.example.focuslock.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusSessionScreen(onBack: () -> Unit) {
    var timerSeconds by remember { mutableStateOf(25 * 60) }
    var isActive by remember { mutableStateOf(false) }

    LaunchedEffect(isActive) {
        while (isActive && timerSeconds > 0) {
            delay(1000)
            timerSeconds--
        }
        if (timerSeconds == 0) isActive = false
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Focus Session") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Timer,
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            val minutes = timerSeconds / 60
            val seconds = timerSeconds % 60
            Text(
                text = String.format("%02d:%02d", minutes, seconds),
                style = MaterialTheme.typography.displayLarge
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Button(
                onClick = { isActive = !isActive },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isActive) "Pause" else "Start Session")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("End Session")
            }
        }
    }
}
