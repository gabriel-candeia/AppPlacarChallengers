package com.example.appplacarchallengers.data.strategy

import com.example.appplacarchallengers.data.Scoreboard

class NormalStrategy : ScoringStrategy {
    override fun getPoints(scoreboard: Scoreboard, time: Int): String {
        return when(scoreboard.points[time]){
            0 -> "00"
            1 -> "15"
            2 -> "30"
            3 -> "40"
            else -> ""
        }
    }

    override fun score(scoreboard: Scoreboard, time: Int) : ScoringStrategy {
        scoreboard.points[time]++;

        // Team does not score a game
        if (scoreboard.points[time] != 4)
            return this

        // Team scores
        scoreboard.games[time]++

        // Change ends
        if ((scoreboard.games[time] + scoreboard.games[1-time])%2 == 1)
            scoreboard.switched = 1 - scoreboard.switched

        // Change server
        scoreboard.server = 1-scoreboard.server


        // Game does not decide set
        if(scoreboard.games[time] < scoreboard.gamesToSet ){
            scoreboard.points = arrayOf(0, 0)
            return this
        }

        // Wins set regularly
        if(scoreboard.games[time]-scoreboard.games[1-time] >= 2) {
            for(i in 0..1)
                scoreboard.setOverview[i].add(Pair<Int,Int>(scoreboard.games[i],-1))
            scoreboard.points = arrayOf(0, 0)
            return updateSets(scoreboard, time)
        }

        scoreboard.points = arrayOf(0, 0)
        // Both are at 6 games, tiebreaker required
        if (scoreboard.games[time] == scoreboard.games[1-time] )
            return TiebreakerStrategy()

        // Either 5 x 6 or 6 x 5
        return this
    }
}