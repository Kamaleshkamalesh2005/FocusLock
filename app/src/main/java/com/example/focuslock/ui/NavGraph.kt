package com.example.focuslock.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.focuslock.ui.screens.*

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object AppSelection : Screen("app_selection")
    object UsageLimits : Screen("usage_limits")
    object FocusSession : Screen("focus_session")
    object Statistics : Screen("statistics")
    object Settings : Screen("settings")
    object Permission : Screen("permission")
}

@Composable
fun FocusLockNavGraph(
    navController: NavHostController,
    dashboardViewModel: DashboardViewModel,
    appSelectionViewModel: AppSelectionViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                viewModel = dashboardViewModel,
                onSelectApps = { navController.navigate(Screen.AppSelection.route) },
                onStatistics = { navController.navigate(Screen.Statistics.route) },
                onFocusSession = { navController.navigate(Screen.FocusSession.route) },
                onSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.AppSelection.route) {
            AppSelectionScreen(
                viewModel = appSelectionViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.FocusSession.route) {
            FocusSessionScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Statistics.route) {
            StatisticsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Permission.route) {
            PermissionScreen(onPermissionGranted = { navController.popBackStack() })
        }
        // Add more routes as screens are implemented
    }
}
