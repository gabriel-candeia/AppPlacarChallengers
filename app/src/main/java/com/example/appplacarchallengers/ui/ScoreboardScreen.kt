package com.example.appplacarchallengers.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appplacarchallengers.data.Scoreboard

@Composable
fun TeamField(
    playerNames: (Int) -> String,
    points: String,
    games: String,
    sets: String,
    onScoreButton: () -> Unit,
    reversed: Boolean = false,
    modifier: Modifier = Modifier
) {
    val order = arrayOf(0,1,2)
    if(reversed)
        order.reverse()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxWidth().padding(16.dp)
    ) {
        order.forEach {
            when(it) {
                0 -> Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ){
                            Text(text = playerNames(0), fontSize = 24.sp)
                            Text(text = playerNames(1), fontSize = 24.sp)
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
    onSaveButton: () -> Unit,
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

        Button(onClick = onUndoButton, shape = RoundedCornerShape(10)) {
            Text("Undo")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreboardScreen(
    matchName: String,
    getPlayerNames: (Int, Int) -> String,
    getPoints: (Int) -> String,
    getGames: (Int) -> String,
    getSets: (Int) -> String,
    onSaveButton: () -> Unit,
    onUndoButton: () -> Unit,
    onScoreButton: (Int) -> Boolean,
    modifier: Modifier
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    if(showDialog) {
        AlertDialog(
            icon = {
                Icon(Icons.Filled.Autorenew,"")
            },
            title = {
                Text("change ends")
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Confirm")
                }
            },
            onDismissRequest = {  }
        )
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
                       text = matchName,
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
                playerNames = { getPlayerNames(0,it) },
                points = getPoints(0),
                games = getGames(0),
                sets = getSets(0),
                onScoreButton = { showDialog = onScoreButton(0) },
                modifier = Modifier.weight(1f)
            )

            OptionsBar(
                onSaveButton = onSaveButton,
                onUndoButton = onUndoButton
            )

            TeamField(
                playerNames = { getPlayerNames(1,it) },
                points = getPoints(1),
                games = getGames(1),
                sets = getSets(1),
                onScoreButton = { showDialog = onScoreButton(1) },
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

    ScoreboardScreen(
        matchName = scoreboard.matchName,
        getPlayerNames = {i, j -> scoreboard.playerNames[i][j]},
        getGames = {"0"},
        getSets = {"0"},
        getPoints = {"00"},
        onScoreButton = { false },
        onSaveButton = { },
        onUndoButton = { },
        modifier = Modifier.fillMaxSize()
    )
}