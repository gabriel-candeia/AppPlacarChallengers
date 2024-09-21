package com.example.appplacarchallengers.ui

import androidx.lifecycle.ViewModel
import com.example.appplacarchallengers.data.Scoreboard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.example.appplacarchallengers.R
import com.example.appplacarchallengers.data.DataSource
import kotlinx.coroutines.flow.update

class ScoreboardViewModel : ViewModel() {
    private val _scoreboardStack = mutableListOf<Scoreboard>()
    private val _scoreboardState = MutableStateFlow(Scoreboard())
    val scoreboardState: StateFlow<Scoreboard> = _scoreboardState.asStateFlow()

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
}