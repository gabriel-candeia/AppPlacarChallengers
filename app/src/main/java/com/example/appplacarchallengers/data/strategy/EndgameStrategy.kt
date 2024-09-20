package com.example.appplacarchallengers.data.strategy

import com.example.appplacarchallengers.data.Scoreboard

class EndgameStrategy : ScoringStrategy {
    override fun getPoints(scoreboard: Scoreboard, time: Int): String {
        return ""
    }

    override fun score(scoreboard: Scoreboard, time: Int): ScoringStrategy {
        return this
    }
}