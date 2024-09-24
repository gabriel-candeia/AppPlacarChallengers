package com.example.appplacarchallengers.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ScoreboardEntity::class],
    version = 1
)
abstract class ScoreboardDatabase : RoomDatabase() {

    abstract fun scoreboardDao(): ScoreboardDao

    companion object {
        @Volatile
        private var Instance: ScoreboardDatabase? = null

        fun getDatabase(context: Context): ScoreboardDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ScoreboardDatabase::class.java, "scoreboard_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}