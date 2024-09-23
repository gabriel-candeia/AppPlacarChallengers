package com.example.appplacarchallengers.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TeamField(
    playerNames: (Int) -> String,
    points: String,
    games: String,
    sets: String,
    onScoreButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Column {
            Text(text = playerNames(0))
            Text(text = playerNames(1))
        }

        Row {
            Column {
                Text(text = "Games")
                Text(text = games)
            }

            Column {
                Text(text = "Sets")
                Text(text = sets)
            }
        }

        Text(
            text = points,
            modifier = modifier
                .clickable(onClick = onScoreButton)
        )
    }
}

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

    Column {
        Text(text = matchName)

        TeamField(
            playerNames = { getPlayerNames(0,it) },
            points = getPoints(0),
            games = getGames(0),
            sets = getSets(0),
            onScoreButton = { showDialog = onScoreButton(0) }
        )

        Row {
            Button(onClick = onSaveButton) {
                Text("Save")
            }

            //timer

            Button(onClick = onUndoButton) {
                Text("Undo")
            }
        }

        TeamField(
            playerNames = { getPlayerNames(1,it) },
            points = getPoints(1),
            games = getGames(1),
            sets = getSets(1),
            onScoreButton = { showDialog = onScoreButton(1) }
        )
    }
}