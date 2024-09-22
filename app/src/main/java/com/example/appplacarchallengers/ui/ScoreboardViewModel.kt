package com.example.appplacarchallengers.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.appplacarchallengers.data.Scoreboard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.viewModelScope
import com.example.appplacarchallengers.R
import com.example.appplacarchallengers.data.DataSource
import com.example.appplacarchallengers.data.db.ScoreboardDao
import com.example.appplacarchallengers.data.db.ScoreboardEntity
import com.example.appplacarchallengers.data.db.ScoreboardRepository
import com.example.appplacarchallengers.data.db.toScoreboardEntity
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScoreboardViewModel(val scoreboardDao: ScoreboardDao) : ViewModel() {
    private val _scoreboardStack = mutableListOf<Scoreboard>()
    private val _scoreboardState = MutableStateFlow(Scoreboard())
    val scoreboardState: StateFlow<Scoreboard> = _scoreboardState.asStateFlow()

    private val _allScoreboards = MutableStateFlow<List<ScoreboardEntity>>(emptyList())
    val allScoreboards: StateFlow<List<ScoreboardEntity>> = _allScoreboards.asStateFlow()

    var config: SnapshotStateMap<Int,String> = SnapshotStateMap<Int,String>()

    fun getConfig(key: Int): String {
        return config.get(key) ?: ""
    }

    fun setConfig(key: Int, value: String): Unit {
        config.put(key, value)
    }

    fun resetConfig(): Unit {
        config.clear()
    }

    fun createScoreboard() {
        _scoreboardState.update { currentState ->
            val newState = currentState.copy()
            newState.matchName = getConfig(R.string.TextField_match_name)
            newState.playerNames = arrayOf(
                arrayOf(getConfig(R.string.TextField_player_a1),getConfig(R.string.TextField_player_a2)),
                arrayOf(getConfig(R.string.TextField_player_b1),getConfig(R.string.TextField_player_b2)))
            newState
        }
    }

    fun getPoints(team: Int): String {
        return _scoreboardState.value.getPoints(team)
    }

    fun score(team: Int) {
        _scoreboardStack.add(_scoreboardState.value)
        _scoreboardState.update { currentState ->
            val newState = currentState.copy()
            newState.score(team)
            newState
        }
    }

    fun undo() {
        if(_scoreboardStack.isEmpty()) return
        _scoreboardState.update { currentState ->
            val newState = _scoreboardStack.last()
            newState
        }
        _scoreboardStack.removeAt(_scoreboardStack.size-1)
    }

    fun saveScoreboard() {
        viewModelScope.launch {
            val scoreboardEntity = _scoreboardState.value.toScoreboardEntity()
            scoreboardDao.insertScoreboard(scoreboardEntity)
            Log.d("pdm","teste")
        }
    }

    fun getAllScoreboards() {
        viewModelScope.launch {
            val scoreboards = scoreboardDao.getAllScoreboards()
            _allScoreboards.update { scoreboards }
        }
    }
}