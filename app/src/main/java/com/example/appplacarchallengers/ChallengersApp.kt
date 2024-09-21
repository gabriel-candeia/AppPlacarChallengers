package com.example.appplacarchallengers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appplacarchallengers.ui.HomeScreen
import com.example.appplacarchallengers.ui.ScoreboardViewModel
import com.example.appplacarchallengers.ui.theme.AppPlacarChallengersTheme

enum class ChallengersAppScreen() {
    Home,
    Configuration,
    Scoreboard,
    History,
    Detail
}

@Composable
fun ChallengersApp(
    viewModel: ScoreboardViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    Scaffold(

    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = ChallengersAppScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ChallengersAppScreen.Home.name) {
                HomeScreen(
                    onNewMatchButton = {
                        navController.navigate(ChallengersAppScreen.Configuration.name)
                    },
                    onPastMatchesButton = {
                        navController.navigate(ChallengersAppScreen.History.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppPlacarChallengersTheme {
        ChallengersApp()
    }
}