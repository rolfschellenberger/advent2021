package com.rolf.day03

data class BinaryNumber(
    val input: String
) {
    fun get(i: Int): String {
        return input[i].toString()
    }

    fun toInt() = Integer.parseInt(input, 2)
}
