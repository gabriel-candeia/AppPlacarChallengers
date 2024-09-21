package com.example.appplacarchallengers.data

import com.example.appplacarchallengers.data.strategy.EndgameStrategy
import com.example.appplacarchallengers.data.strategy.NormalStrategy
import com.example.appplacarchallengers.data.strategy.ScoringStrategy
import java.io.Serializable
import java.util.Date

data class Scoreboard(var matchName: String? = null, var hasTimer: Boolean? = null, var date: Date? = null, var gamesToSet: Int = 6, var totalSets: Int = 5) : Serializable {
    var scoringStrategy: ScoringStrategy = NormalStrategy()
    var teamNames: Array<String> = arrayOf("","")
    var playerNames: Array<Array<String>> = arrayOf(arrayOf("", ""), arrayOf("", ""))

    var points: Array<Int> = arrayOf(0, 0)
    var games: Array<Int> = arrayOf(0, 0)
    var sets: Array<Int> = arrayOf(0, 0)
    var setOverview: Array<Pair<Array<Int>, Array<Int>>> = arrayOf()

    var switched: Int = 0
    var winningTeam: String = ""

    fun gameEnded(): Boolean {
        return scoringStrategy is EndgameStrategy
    }

    fun getPoints(time: Int): String {
        return scoringStrategy.getPoints(this, time)
    }

    fun score(time: Int) {
        if(gameEnded()) return
        scoringStrategy = scoringStrategy.score(this, time)
        if(gameEnded()) {
            winningTeam = playerNames[time][0] + " e " + playerNames[time][1]
        }
    }

    fun copy() : Scoreboard {
        var answ: Scoreboard = Scoreboard(matchName, hasTimer, date, gamesToSet, totalSets)
        answ.scoringStrategy = scoringStrategy
        answ.points = points.copyOf()
        answ.playerNames = playerNames.copyOf()
        answ.games = games.copyOf()
        answ.sets = sets.copyOf()
        answ.switched = switched
        return answ
    }

}
