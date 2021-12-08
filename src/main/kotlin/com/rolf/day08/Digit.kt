package com.rolf.day08

data class Digit(val input: String, var number: Int?) {

    fun contains(other: Digit): Boolean {
        val set1 = input.toSet()
        val set2 = other.input.toSet()
        return set1.containsAll(set2) || set2.containsAll(set1)
    }

    fun containsAll(other: Digit): Boolean {
        return other.input.length == input.length &&
                other.input.toSet().containsAll(input.toSet())
    }
}
