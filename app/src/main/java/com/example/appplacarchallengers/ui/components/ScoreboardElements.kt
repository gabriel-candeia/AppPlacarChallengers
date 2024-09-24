package com.example.appplacarchallengers.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.appplacarchallengers.ui.formatTime
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material.icons.filled.SportsBaseball
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

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
        modifier = modifier.fillMaxWidth().padding(16.dp)
    ) {
        Button(onClick = onSaveButton, shape = RoundedCornerShape(10)) {
            Icon(Icons.Filled.Save, contentDescription = "Save")
            Text("Salvar")
        }

        if (hasTimer) {
            Button(onClick = onTimerButton, shape = RoundedCornerShape(10)) {
                Icon(Icons.Filled.Timer, contentDescription = "Timer")
                Text(text = currentTime.formatTime(), fontStyle = FontStyle.Italic)
            }
        }

        Button(onClick = onUndoButton, shape = RoundedCornerShape(10)) {
            Icon(Icons.Filled.Undo, contentDescription = "Undo")
            Text("")
        }
    }
}

@Composable
fun TeamField(
    playerNames: (Int) -> String,
    points: String,
    games: String,
    sets: String,
    onScoreButton: () -> Unit,
    isServer: Boolean = false,
    reversed: Boolean = false,
    modifier: Modifier = Modifier
) {
    val rotation by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 2000, easing = LinearEasing))
    )

    val playerOrder = if (reversed) listOf(1, 0) else listOf(0, 1)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                playerOrder.forEach { Text(playerNames(it), fontSize = 24.sp) }
            }

            if (isServer) {
                Icon(Icons.Filled.SportsBaseball, contentDescription = null, modifier = Modifier.rotate(rotation))
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.End) {
                Text("Games", fontWeight = FontWeight.Bold, fontSize = 32.sp)
                Text(games, fontWeight = FontWeight.Bold, fontSize = 32.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("Sets", fontWeight = FontWeight.Bold, fontSize = 32.sp)
                Text(sets, fontWeight = FontWeight.Bold, fontSize = 32.sp)
            }
        }

        Text(
            points,
            fontWeight = FontWeight.Bold,
            fontSize = 160.sp,
            modifier = Modifier.clickable(onClick = onScoreButton)
        )
    }
}