package com.example.appplacarchallengers.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appplacarchallengers.data.db.ScoreboardEntity

val superscript = SpanStyle(
    baselineShift = BaselineShift.Superscript,
    fontSize = 16.sp,
)

@Composable
fun Overtext(
    firstText: String,
    secondText: String?,
    modifier: Modifier = Modifier
) {

    Text(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        text = buildAnnotatedString {
            append(firstText)
            if(secondText != null) {
                withStyle(superscript) {
                    append(secondText)
                }
            }
        }
    )

}

@Composable
fun ScoreboardCardRow(
    namePlayer1: String,
    namePlayer2: String,
    setOverview: List<Pair<Int,Int>>,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(3f)
        ) {
            Text(text = namePlayer1, textAlign = TextAlign.Center)
            Text(text = namePlayer2, textAlign = TextAlign.Center)
        }
        setOverview.forEach {
            var x: Int = it.second.toInt()
            var second: String? = if(x>=0) x.toString() else null
            if(it.first != -1)
                Overtext(it.first.toString(), second, Modifier.weight(1f))
        }
    }
}

@Composable
fun ScoreboardCard(
    item: ScoreboardEntity,
    onEditButton: () -> Unit,
    onDeleteButton: () -> Unit,
    modifier: Modifier = Modifier
) { 
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .animateContentSize()
            .clickable { isExpanded = !isExpanded }
    ) {
        Text(
            text = item.matchName,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth().padding(16.dp)
        )

        ScoreboardCardRow(
            namePlayer1 = item.playerNames.get(0),
            namePlayer2 = item.playerNames.get(1),
            setOverview = item.setOverviewA
        )

        ScoreboardCardRow(
            namePlayer1 = item.playerNames.get(2),
            namePlayer2 = item.playerNames.get(3),
            setOverview = item.setOverviewB
        )

        if (isExpanded) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ){
                IconButton(
                    onClick = onDeleteButton,
                    modifier = modifier
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete"
                    )
                }

                IconButton(
                    onClick = onEditButton,
                    modifier = modifier
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Load"
                    )
                }
            }
        }
        else {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TestScreen(
    items: List<ScoreboardEntity>,
    onEditButton: (ScoreboardEntity) -> Unit,
    onDeleteButton: (ScoreboardEntity) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier
            .animateContentSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Text(text = items.size.toString())  }
        items(items) { item ->
            ScoreboardCard(
                item = item,
                onEditButton = { onEditButton(item) },
                onDeleteButton = { onDeleteButton(item) })
        }
    }
}