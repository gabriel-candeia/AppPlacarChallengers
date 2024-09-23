package com.example.appplacarchallengers

//import com.example.appplacarchallengers.data.ScoreboardViewModelFactory
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appplacarchallengers.ui.ConfigurationScreen
import com.example.appplacarchallengers.ui.HistoryScreen
import com.example.appplacarchallengers.ui.HomeScreen
import com.example.appplacarchallengers.ui.ScoreboardScreen
import com.example.appplacarchallengers.ui.ScoreboardViewModel
import com.example.appplacarchallengers.ui.TimerViewModel
import com.example.compose.AppTheme

enum class ChallengersAppScreen() {
    Home,
    Configuration,
    Scoreboard,
    History,
    Detail
}

@Composable
fun ChallengersApp(
    viewModel: ScoreboardViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    Scaffold(

    ) { innerPadding ->
        val uiState by viewModel.scoreboardState.collectAsState()
        val savedScoreboardState by viewModel.allScoreboards.collectAsState()
        var timerViewModel = TimerViewModel()

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
                        viewModel.getAllScoreboards()
                        navController.navigate(ChallengersAppScreen.History.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            composable(route = ChallengersAppScreen.Configuration.name) {
                ConfigurationScreen(
                    getValue = { viewModel.getConfig(it) },
                    onValueChange = { key, value -> viewModel.setConfig(key,value) },
                    onStartMatchButton = {
                        if(viewModel.createScoreboard()) {
                            timerViewModel.setTimer(0)
                            navController.navigate(ChallengersAppScreen.Scoreboard.name)
                        }
                    },
                    getRadioButtonValue = { uiState.timer != null },
                    onRadioButton = { viewModel.toogleTimer() } ,
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = ChallengersAppScreen.Scoreboard.name) {
                ScoreboardScreen(
                    scoreboard = uiState,
                    viewModel = timerViewModel,
                    onSaveButton = { viewModel.saveScoreboard(it) },
                    onUndoButton = { viewModel.undo() },
                    onScoreButton = { viewModel.score(it) },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = ChallengersAppScreen.History.name) {
                viewModel.getAllScoreboards()
                HistoryScreen(
                    items = savedScoreboardState,
                    onEditButton = {
                        viewModel.loadScoreboard(it, timerViewModel)
                        navController.navigate(ChallengersAppScreen.Scoreboard.name)
                    },
                    onDeleteButton = {
                        viewModel.deleteScoreboard(it)
                        viewModel.getAllScoreboards()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        //ChallengersApp()
    }
}