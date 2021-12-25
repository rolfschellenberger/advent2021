package com.rolf.day23

data class Move(val burrow: Burrow, val cost: Int) {
    fun isDone(): Boolean {
        return burrow.isDone()
    }

    fun nextMoves(): List<Move> {
        return burrow.nextMoves()
    }

    override fun toString(): String {
        return burrow.toString()
    }
}
