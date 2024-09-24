package com.example.appplacarchallengers.data.strategy

import com.example.appplacarchallengers.data.Scoreboard

fun Boolean.toInt() : Int {
    return if(this==false)  0 else 1
}

class TiebreakerStrategy : ScoringStrategy {
    override fun getPoints(scoreboard: Scoreboard, time: Int): String {
        return String.format("%02d",scoreboard.points[time])
    }

    override fun score(scoreboard: Scoreboard, time: Int): ScoringStrategy {
        scoreboard.points[time]++;

        // Change ends
        if ((scoreboard.points[time] + scoreboard.points[1-time])%4 == 1)
            scoreboard.switched = 1 - scoreboard.switched

        // Change server
        if ((scoreboard.points[time] + scoreboard.points[1-time])%2 == 1)
            scoreboard.server = 1 - scoreboard.server

        if (scoreboard.points[time] < 7 || scoreboard.points[time] - scoreboard.points[1-time] < 2) return this

        for(i in 0..1)
            scoreboard.setOverview[i].add(Pair<Int,Int>(scoreboard.games[i] + (i==time).toInt(),scoreboard.points[i]))
        return updateSets(scoreboard, time)
    }
}