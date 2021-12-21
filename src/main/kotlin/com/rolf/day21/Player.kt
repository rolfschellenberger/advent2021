package com.rolf.day21

data class Player(val name: String, var position: Int, var score: Int = 0) {

    fun play(die: DeterministicDie) {
        val steps = die.roll().sum()
        var newPosition = (position + steps) % 10
        if (newPosition == 0) {
            newPosition = 10
        }
        score += newPosition
        position = newPosition
    }
}
