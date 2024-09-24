package com.example.appplacarchallengers.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appplacarchallengers.data.DialogState
import com.example.appplacarchallengers.data.Scoreboard
import com.example.appplacarchallengers.ui.components.GameDialog
import com.example.appplacarchallengers.ui.components.OptionsBar
import com.example.appplacarchallengers.ui.components.TeamField
import com.example.ui.theme.AppTypography

fun Long.formatTime(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    if(hours.toInt() != 0)
        return String.format("%02d : %02d:%02d", hours, minutes, seconds)
    return String.format("%02d : %02d", minutes, seconds)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreboardScreen(
    viewModel: TimerViewModel = viewModel(),
    scoreboard: Scoreboard,
    onSaveButton: (Long) -> Unit,
    onUndoButton: () -> Unit,
    onScoreButton: (Int) -> DialogState,
    modifier: Modifier
) {
    DisposableEffect(true) {
        onDispose {
            viewModel.pauseTimer()
        }
    }

    val timerState by viewModel.timer.collectAsState()
    viewModel.startTimer()

    var showDialog by rememberSaveable { mutableStateOf(DialogState.NoDialog) }
    when (showDialog) {
        DialogState.NoDialog -> {}
        DialogState.ChangeEnds -> {
            GameDialog(
                title = "Troca de lado!",
                icon = { Icon(Icons.Filled.Autorenew, contentDescription = null) },
                confirmButtonText = "Confirmar",
                onConfirm = {
                    viewModel.startTimer()
                    showDialog = DialogState.NoDialog
                },
                onDismiss = { showDialog = DialogState.NoDialog }
            )
        }
        DialogState.Endgame -> {
            GameDialog(
                title = "Fim de jogo!",
                confirmButtonText = "Salvar",
                onConfirm = {
                    showDialog = DialogState.NoDialog
                    onSaveButton(timerState)
                },
                onDismiss = { showDialog = DialogState.NoDialog }
            )
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
           TopAppBar(
               colors = topAppBarColors(
                   containerColor = MaterialTheme.colorScheme.primaryContainer,
                   titleContentColor = MaterialTheme.colorScheme.primary,
                ),
               title = {
                   Text(
                       text = scoreboard.matchName,
                       modifier = Modifier.fillMaxWidth(),
                       textAlign = TextAlign.Center,
                       style = AppTypography.displayMedium
                   )
               }
           )
        },
        modifier = Modifier.fillMaxSize()
    ){ innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(innerPadding)
        ){
            TeamField(
                playerNames = { scoreboard.playerNames[0][it] },
                points = scoreboard.getPoints(0),
                games = scoreboard.games[0].toString(),
                sets = scoreboard.sets[0].toString(),
                onScoreButton = { showDialog = onScoreButton(0) },
                isServer = scoreboard.server == 0,
                modifier = Modifier.weight(1f)
            )

            OptionsBar(
                hasTimer = scoreboard.timer!=null,
                currentTime = timerState,
                onSaveButton = { onSaveButton(timerState) },
                onUndoButton = onUndoButton,
                onTimerButton = { viewModel.toogleTimer() }
            )

            TeamField(
                playerNames = { scoreboard.playerNames[1][it] },
                points = scoreboard.getPoints(1),
                games = scoreboard.games[1].toString(),
                sets = scoreboard.sets[1].toString(),
                onScoreButton = { showDialog = onScoreButton(1) },
                isServer = scoreboard.server == 1,
                reversed = true,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun ScoreboardScreenPreview() {
    val scoreboard = Scoreboard()
    scoreboard.matchName = "semifinal"
    scoreboard.playerNames = arrayOf(arrayOf("player 1", "player 2"), arrayOf("player 3", "player 4"))

    TeamField(
        playerNames = {"player"},
        points = "00",
        games = "0",
        sets = "0",
        onScoreButton = {}
    )
}