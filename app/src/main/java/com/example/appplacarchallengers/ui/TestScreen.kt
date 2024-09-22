package com.example.appplacarchallengers.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appplacarchallengers.data.DataSource
import com.example.appplacarchallengers.data.Scoreboard
import com.example.appplacarchallengers.data.db.ScoreboardEntity

@Composable
fun ScoreboardCardRow(
    namePlayer1: String,
    namePlayer2: String,
    points: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = namePlayer1, textAlign = TextAlign.Center)
            Text(text = namePlayer2, textAlign = TextAlign.Center)
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = points, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun ScoreboardCard(
    item: ScoreboardEntity,
    modifier: Modifier = Modifier
) {
    Card {
        Text(text = item.matchName)
        ScoreboardCardRow(
            namePlayer1 = item.playerNames.get(0),
            namePlayer2 = item.playerNames.get(1),
            points = item.points.get(0).toString()
        )
        ScoreboardCardRow(
            namePlayer1 = item.playerNames.get(2),
            namePlayer2 = item.playerNames.get(3),
            points = item.points.get(1).toString()
        )
    }
}

@Composable
fun TestScreen(
    items: List<ScoreboardEntity>,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { Text(text = items.size.toString())  }
        items(items) { item ->
            ScoreboardCard(item, modifier)
        }
    }
}