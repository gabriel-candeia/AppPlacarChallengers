package com.example.appplacarchallengers.data.strategy

import com.example.appplacarchallengers.data.Scoreboard
import java.io.Serializable

fun updateSets(scoreboard: Scoreboard, time: Int): ScoringStrategy {
    scoreboard.points = arrayOf(0, 0)
    scoreboard.games = arrayOf(0, 0)
    scoreboard.sets[time]++

    when { // Endgame
        (scoreboard.sets[time] > scoreboard.totalSets - scoreboard.sets[time]) -> {
            return EndgameStrategy()
        } // Supertiebreaker
        (scoreboard.sets[time]+scoreboard.sets[1-time] == scoreboard.totalSets-1) -> {
            return SupertieStrategy()
        } // Just scored a set
        else -> {
            return  NormalStrategy()
        }
    }
}

interface ScoringStrategy : Serializable {
    fun getPoints(scoreboard: Scoreboard, time: Int): String
    fun score(scoreboard: Scoreboard, time: Int): ScoringStrategy
}

