package com.example.appplacarchallengers.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScoreboardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScoreboard(scoreboard: ScoreboardEntity)

    @Delete
    suspend fun deleteScoreboard(scoreboard: ScoreboardEntity)

    @Query("SELECT * FROM ScoreboardEntity")
    suspend fun getAllScoreboards(): List<ScoreboardEntity>
}