package com.example.appplacarchallengers.ui

import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appplacarchallengers.R
import com.example.appplacarchallengers.data.DialogState
import com.example.appplacarchallengers.data.Scoreboard
import com.example.appplacarchallengers.data.db.ScoreboardDao
import com.example.appplacarchallengers.data.db.ScoreboardEntity
import com.example.appplacarchallengers.data.db.toScoreboardEntity
import com.example.appplacarchallengers.data.strategy.EndgameStrategy
import com.example.appplacarchallengers.data.strategy.toStrategy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

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
        if(value == "")
            config.remove(key)
        else
            config.put(key, value)
    }

    fun resetConfig(): Unit {
        config.clear()
    }

    fun resetAll() {
        resetConfig()
        _scoreboardStack.clear()
        _scoreboardState.update { currentState ->
            val newState = Scoreboard()
            newState
        }
    }

    fun createScoreboard() : Boolean {
        _scoreboardStack.clear()
        _scoreboardState.update { currentState ->
            val newState = Scoreboard(currentState.timer,null,totalSets = currentState.totalSets, gamesToSet = currentState.gamesToSet)
            newState.matchName = getConfig(R.string.TextField_match_name)
            newState.playerNames = arrayOf(
                arrayOf(getConfig(R.string.TextField_player_a1),getConfig(R.string.TextField_player_a2)),
                arrayOf(getConfig(R.string.TextField_player_b1),getConfig(R.string.TextField_player_b2)))
            newState.date = Calendar.getInstance().time
            newState
        }
        return true
    }

    fun score(team: Int) : DialogState {
        _scoreboardStack.add(_scoreboardState.value)
        var switched = _scoreboardState.value.switched
        _scoreboardState.update { currentState ->
            val newState = currentState.copy()
            newState.score(team)
            newState
        }
        if(_scoreboardState.value.scoringStrategy is EndgameStrategy)
            return DialogState.Endgame
        return if(switched != _scoreboardState.value.switched) DialogState.ChangeEnds else DialogState.NoDialog
    }

    fun undo() {
        if(_scoreboardStack.isEmpty()) return
        _scoreboardState.update { currentState ->
            val newState = _scoreboardStack.last()
            newState
        }
        _scoreboardStack.removeAt(_scoreboardStack.size-1)
    }

    fun saveScoreboard(timer : Long) {
        if(_scoreboardState.value.timer != null) {
            _scoreboardState.update  { currentState ->
                val newState = currentState.copy()
                newState.timer = timer
                newState
            }
        }
        viewModelScope.launch {
            val scoreboardEntity = _scoreboardState.value.toScoreboardEntity()
            scoreboardDao.insertScoreboard(scoreboardEntity)
        }
    }

    fun deleteScoreboard(scoreboardEntity: ScoreboardEntity) {
        viewModelScope.launch {
            scoreboardDao.deleteScoreboard(scoreboardEntity)
        }
    }

    fun getAllScoreboards() {
        viewModelScope.launch {
            val scoreboards = scoreboardDao.getAllScoreboards()
            _allScoreboards.update { scoreboards }
        }
    }

    fun loadScoreboard(it: ScoreboardEntity, timerViewModel: TimerViewModel) {
        resetAll()
        _scoreboardState.update { currentState ->
            val newState = Scoreboard()
            newState.matchName = it.matchName
            newState.timer = it.timer
            timerViewModel.setTimer(newState.timer?:0)
            //date

            newState.gamesToSet = it.gamesToSet
            newState.totalSets = it.totalSets
            newState.playerNames = arrayOf(
                arrayOf(it.playerNames.get(0),it.playerNames.get(1)),
                arrayOf(it.playerNames.get(2),it.playerNames.get(3)))

            newState.points = arrayOf<Int>(it.points[0], it.points[1])
            newState.games = arrayOf(it.games[0],it.games[1])
            newState.sets = arrayOf(it.sets[0],it.sets[1])
            newState.setOverview = arrayOf(it.setOverviewA.toMutableList(),it.setOverviewB.toMutableList())
            newState.switched = it.switched
            newState.winningTeam = it.winningTeam

            newState.scoringStrategy = it.scoringStrategy.toStrategy()
            newState.server = it.server
            newState
        }
    }

    fun toogleTimer() {
        _scoreboardState.update { currentState ->
            val newState = currentState.copy()
            newState.timer = if(newState.timer==null) 0 else null
            newState
        }
    }

}