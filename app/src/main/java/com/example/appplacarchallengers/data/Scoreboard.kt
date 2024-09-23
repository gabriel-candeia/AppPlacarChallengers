package com.example.appplacarchallengers.data

import com.example.appplacarchallengers.data.strategy.EndgameStrategy
import com.example.appplacarchallengers.data.strategy.NormalStrategy
import com.example.appplacarchallengers.data.strategy.ScoringStrategy
import com.example.appplacarchallengers.data.strategy.SupertieStrategy
import com.example.appplacarchallengers.data.strategy.TiebreakerStrategy
import java.io.Serializable
import java.util.Date

class Scoreboard(var timer: Long? = null, var date: Date? = null, var gamesToSet: Int = 6, var totalSets: Int = 5) : Serializable {
    var matchName: String = ""
    var scoringStrategy: ScoringStrategy = NormalStrategy()
    var teamNames: Array<String> = arrayOf("","")
    var playerNames: Array<Array<String>> = arrayOf(arrayOf("", ""), arrayOf("", ""))

    var points: Array<Int> = arrayOf(0, 0)
    var games: Array<Int> = arrayOf(0, 0)
    var sets: Array<Int> = arrayOf(0, 0)
    var setOverview: Array<MutableList<Pair<Int, Int>>> = arrayOf(mutableListOf<Pair<Int,Int>>(),mutableListOf<Pair<Int,Int>>())

    var switched: Int = 0
    var server: Int = 0
    var winningTeam: Int? = null


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
            winningTeam = time
        }
    }

    fun ongoingSetOverview(team: Int) : Pair<Int,Int> {
        return when(scoringStrategy) {
            is NormalStrategy -> Pair<Int,Int>(games[team],points[team])
            is TiebreakerStrategy -> Pair<Int,Int>(games[team],points[team])
            is SupertieStrategy -> Pair<Int,Int>(games[team],points[team])
            else -> Pair<Int,Int>(-1,-1)
        }
    }

    fun copy() : Scoreboard {
        var answ: Scoreboard = Scoreboard(timer, date, gamesToSet, totalSets)
        answ.matchName = matchName
        answ.scoringStrategy = scoringStrategy
        answ.points = points.copyOf()
        answ.playerNames = playerNames.copyOf()
        answ.games = games.copyOf()
        answ.sets = sets.copyOf()
        answ.setOverview = arrayOf(setOverview[0].toMutableList(),setOverview[1].toMutableList())
        answ.switched = switched
        answ.winningTeam = winningTeam
        answ.server = server
        return answ
    }

}
