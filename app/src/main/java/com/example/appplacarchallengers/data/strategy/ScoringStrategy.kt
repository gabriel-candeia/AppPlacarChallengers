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

enum class ScoringState {
    Normal,
    Tiebreaker,
    Supertiebreaker,
    Endgame
}


fun ScoringState.toStrategy() : ScoringStrategy {
    return when (this) {
        ScoringState.Normal -> NormalStrategy()
        ScoringState.Tiebreaker -> TiebreakerStrategy()
        ScoringState.Supertiebreaker -> SupertieStrategy()
        else -> EndgameStrategy()
    }
}

fun ScoringStrategy.toState() : ScoringState {
    return when (this) {
        is NormalStrategy -> ScoringState.Normal
        is TiebreakerStrategy -> ScoringState.Tiebreaker
        is SupertieStrategy -> ScoringState.Supertiebreaker
        else -> ScoringState.Endgame
    }
}

interface ScoringStrategy : Serializable {
    fun getPoints(scoreboard: Scoreboard, time: Int): String
    fun score(scoreboard: Scoreboard, time: Int): ScoringStrategy
}

