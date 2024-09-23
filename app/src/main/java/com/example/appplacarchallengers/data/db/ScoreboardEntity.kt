package com.example.appplacarchallengers.data.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.appplacarchallengers.data.Scoreboard
import com.example.appplacarchallengers.data.strategy.ScoringState
import com.example.appplacarchallengers.data.strategy.ScoringStrategy
import com.example.appplacarchallengers.data.strategy.toState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun Scoreboard.toScoreboardEntity(): ScoreboardEntity = ScoreboardEntity(
        matchName = matchName,
        hasTimer = hasTimer,
        date = 0,
        gamesToSet = gamesToSet,
        totalSets = totalSets,
        playerNames = playerNames[0].toList() + playerNames[1].toList(),
        points = points.toList(),
        games = games.toList(),
        sets = sets.toList(),
        setOverviewA = setOverview[0] + ongoingSetOverview(0),
        setOverviewB = setOverview[1] + ongoingSetOverview(1),
        switched = switched,
        winningTeam = winningTeam,
        scoringStrategy = scoringStrategy.toState()
)

class IntListConverter {
        @TypeConverter
        fun saveIntList(list: List<Int>): String? {
                return Gson().toJson(list)
        }

        @TypeConverter
        fun getIntList(list: String): List<Int> {
                return Gson().fromJson(
                        list,
                        object : TypeToken<List<Int>>() {}.type
                )
        }
}

class StringListConverter {
        @TypeConverter
        fun saveStringList(list: List<String>): String? {
                return Gson().toJson(list)
        }

        @TypeConverter
        fun getStringList(list: String): List<String> {
                return Gson().fromJson(
                        list,
                        object : TypeToken<List<String>>() {}.type
                )
        }
}

class PairListConverter {
        @TypeConverter
        fun savePairList(list: List<Pair<Int, Int>>): String? {
                return Gson().toJson(list)
        }

        @TypeConverter
        fun getPairList(list: String): List<Pair<Int, Int>> {
                return Gson().fromJson(
                        list,
                        object : TypeToken<List<Pair<Int,Int>>>() {}.type
                )
        }
}


@Entity
data class ScoreboardEntity(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        var hasTimer: Boolean? = null,
        var date: Long? = null, // Store date as milliseconds since epoch
        var gamesToSet: Int = 6,
        var totalSets: Int = 5,
        var matchName: String = "",

        @field:TypeConverters(StringListConverter::class) var playerNames: List<String> = emptyList(),
        @field:TypeConverters(IntListConverter::class) var points: List<Int> = emptyList(),
        @field:TypeConverters(IntListConverter::class) var games: List<Int> = emptyList(),
        @field:TypeConverters(IntListConverter::class) var sets: List<Int> = emptyList(),

        @field:TypeConverters(PairListConverter::class) var setOverviewA: List<Pair<Int, Int>> = emptyList<Pair<Int, Int>>(),
        @field:TypeConverters(PairListConverter::class) var setOverviewB: List<Pair<Int, Int>> = emptyList<Pair<Int, Int>>(),

        var switched: Int = 0,
        var winningTeam: Int? = null,

        var scoringStrategy: ScoringState
)