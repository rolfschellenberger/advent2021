package com.rolf.day21

data class DeterministicDice(var value: Int = 1, var rolls: Int = 0) {

    fun roll(): List<Int> {
        rolls += 3
        value += 3
        return listOf(value - 3, value - 2, value - 1)
    }
}