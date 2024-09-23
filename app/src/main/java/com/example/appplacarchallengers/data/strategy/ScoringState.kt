package com.example.appplacarchallengers.data.strategy

enum class ScoringState {
    Normal,
    Tiebreaker,
    Supertiebreaker,
    Endgame
}

fun ScoringState.toStrategy() : ScoringStrategy {
    return when (this) {
        ScoringState.Normal -> NormalStrategy()
        ScoringState.Tiebreaker -> TiebreakerStrategy()
        ScoringState.Supertiebreaker -> SupertieStrategy()
        else -> EndgameStrategy()
    }
}

fun ScoringState.description() : String {
    return when(this) {
        ScoringState.Normal -> "ongoing match"
        ScoringState.Tiebreaker -> "tiebreaker"
        ScoringState.Supertiebreaker -> "supertiebreaker"
        else -> "game finished"
    }
}

fun ScoringStrategy.toState() : ScoringState {
    return when (this) {
        is NormalStrategy -> ScoringState.Normal
        is TiebreakerStrategy -> ScoringState.Tiebreaker
        is SupertieStrategy -> ScoringState.Supertiebreaker
        else -> ScoringState.Endgame
    }
}