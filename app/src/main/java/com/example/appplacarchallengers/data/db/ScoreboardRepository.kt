package com.example.appplacarchallengers.data.db

class ScoreboardRepository(private val scoreboardDao: ScoreboardDao) {
    suspend fun saveScoreboard(scoreboardEntity: ScoreboardEntity) {
        scoreboardDao.insertScoreboard(scoreboardEntity)
    }

    suspend fun getAllScoreboards(): List<ScoreboardEntity> {
        return scoreboardDao.getAllScoreboards()
    }
}