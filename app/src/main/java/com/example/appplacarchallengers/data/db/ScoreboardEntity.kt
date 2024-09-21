package com.example.appplacarchallengers.data.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
data class ScoreboardEntity(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        var hasTimer: Boolean? = null,
        var date: Long? = null, // Store date as milliseconds since epoch
        var gamesToSet: Int = 6,
        var totalSets: Int = 5,
        var matchName: String = "",
)

