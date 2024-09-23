package com.example.appplacarchallengers.ui

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.SportsBaseball
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appplacarchallengers.data.DialogState
import com.example.appplacarchallengers.data.Scoreboard
import kotlin.math.max

@Composable
fun TeamField(
    playerNames: (Int) -> String,
    points: String,
    games: String,
    sets: String,
    onScoreButton: () -> Unit,
    reversed: Boolean = false,
    isServer: Boolean = false,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing)
        ),
    )

    val order = arrayOf(0,1,2)
    if(reversed)
        order.reverse()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        order.forEach {
            when(it) {
                0 -> Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ){
                            Text(text = playerNames(0), fontSize = 24.sp)
                            Text(text = playerNames(1), fontSize = 24.sp)
                        }

                        if(isServer) {
                            Icon(Icons.Filled.SportsBaseball, contentDescription = ""
                            , modifier = Modifier.rotate(rotation))
                        }
                    }
                1 -> Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                ) {
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(text = "Games", fontWeight = FontWeight.Bold, fontSize = 32.sp)
                            Text(text = games, fontWeight = FontWeight.Bold, fontSize = 32.sp)
                        }

                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(text = "Sets", fontWeight = FontWeight.Bold, fontSize = 32.sp)
                            Text(text = sets, fontWeight = FontWeight.Bold, fontSize = 32.sp)
                        }

                    }
                2 -> Text(
                        text = points,
                        fontWeight = FontWeight.Bold,
                        fontSize = 160.sp,
                        modifier = modifier
                            .clickable(onClick = onScoreButton)
                    )
            }
        }


    }
}

@Composable
fun OptionsBar(
    hasTimer: Boolean = false,
    currentTime: Long = 0,
    onSaveButton: () -> Unit,
    onTimerButton: () -> Unit,
    onUndoButton: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth()
    ) {
        Button(onClick = onSaveButton, shape = RoundedCornerShape(10)) {
            Text("Save")
        }

        //timer
        if(hasTimer) {
            Button(onClick = onTimerButton, shape = RoundedCornerShape(10)) {
                Text(text = currentTime.formatTime(), fontStyle = FontStyle.Italic)
            }
        }

        Button(onClick = onUndoButton, shape = RoundedCornerShape(10)) {
            Text("Undo")
        }
    }

}

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
    when(showDialog) {
        DialogState.NoDialog -> {}
        DialogState.ChangeEnds -> {
            viewModel.pauseTimer()
            AlertDialog(
                icon = {
                    Icon(Icons.Filled.Autorenew,"")
                },
                title = {
                    Text("change ends")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.startTimer()
                            showDialog = DialogState.NoDialog
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                onDismissRequest = {  }
            )
        }
        DialogState.Endgame -> {
            AlertDialog(
                title = {
                    Text("match has ended")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = DialogState.NoDialog;
                            onSaveButton(timerState)
                        }
                    ) {
                        Text("Save")
                    }
                },
                onDismissRequest = { }
            )
        }
    }

    Scaffold(
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
                       fontSize = 48.sp
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

fun AppDialog() {

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
/*
    ScoreboardScreen(
        matchName = scoreboard.matchName,
        getPlayerNames = {i, j -> scoreboard.playerNames[i][j]},
        getGames = {"0"},
        getSets = {"0"},
        getPoints = {"00"},
        onScoreButton = { DialogState.NoDialog },
        onSaveButton = { },
        onUndoButton = { },
        hasTimer = true,
        getCurrentTime = {100},
        modifier = Modifier.fillMaxSize(),
        onTimerButton = {}
    )*/
}