package com.example.appplacarchallengers.data.strategy

import com.example.appplacarchallengers.data.Scoreboard

class SupertieStrategy : ScoringStrategy {
    override fun getPoints(scoreboard: Scoreboard, time: Int): String {
        return String.format("%02d",scoreboard.points[time])
    }

    override fun score(scoreboard: Scoreboard, time: Int): ScoringStrategy {
        scoreboard.points[time]++;

        // Change ends
        if ((scoreboard.points[time] + scoreboard.points[1-time])%5 == 1)
            scoreboard.switched = 1 - scoreboard.switched

        if (scoreboard.points[time] < 10 || scoreboard.points[time] - scoreboard.points[1-time] < 2)
            return this

        for(i in 0..1)
            scoreboard.setOverview[i].add(Pair<Int,Int>(scoreboard.games[i],scoreboard.points[i]))
        return EndgameStrategy()
    }
}