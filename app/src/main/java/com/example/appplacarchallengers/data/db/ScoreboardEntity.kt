package com.example.appplacarchallengers.data.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.appplacarchallengers.data.Scoreboard

fun Scoreboard.toScoreboardEntity(): ScoreboardEntity = ScoreboardEntity(
        matchName = matchName,
        hasTimer = hasTimer,
        date = 0,
        gamesToSet = gamesToSet,
        totalSets = totalSets,
        /*playerNames = listOf(playerNames[0].toList(), playerNames[1].toList()),
        points = points.toList(),
        games = games.toList(),
        sets = sets.toList(),
        switched = switched,
        winningTeam = winningTeam*/
)

@Entity
data class ScoreboardEntity(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        var hasTimer: Boolean? = null,
        var date: Long? = null, // Store date as milliseconds since epoch
        var gamesToSet: Int = 6,
        var totalSets: Int = 5,
        var matchName: String = "",

        /*var playerNames: List<List<String>> = emptyList(),
        var points: List<Int> = emptyList(),
        var games: List<Int> = emptyList(),
        var sets: List<Int> = emptyList(),
        var setOverview: List<Pair<List<Int>, List<Int>>> = emptyList<Pair<List<Int>, List<Int>>>(),

        var switched: Int = 0,
        var winningTeam: String? = null*/
)