package com.example.dogorow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dogorow.ui.theme.DogoRowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogoRowTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DogoRowApp()
                }
            }
        }
    }
}

@Composable
fun DogoRowApp() {
    val navController = rememberNavController()
    val dogoViewModel: DogoViewModel = viewModel()

    NavHost(navController = navController, startDestination = "dogoList") {
        composable("dogoList") {
            DogoListScreen(
                viewModel = dogoViewModel,
                onDogoClick = { dogoId ->
                    navController.navigate("dogoDetail/$dogoId")
                },
                onSettingsClick = {
                    navController.navigate("settings")
                }
            )
        }
        composable(
            route = "dogoDetail/{dogoId}",
            arguments = listOf(navArgument("dogoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val dogoId = backStackEntry.arguments?.getInt("dogoId") ?: 0
            DogoDetailScreen(
                dogoId = dogoId,
                viewModel = dogoViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("settings") {
            SettingsScreen(
                onBackClick = { navController.popBackStack() },
                onProfileClick = { navController.navigate("profile") }
            )
        }
        composable("profile") {
            ProfileScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}