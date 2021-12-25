package com.rolf.day23

data class Room(val index: Int, val input: MutableList<String>, val size: Int, val resultValue: String) {

    fun hasValue(): Boolean {
        return input.isNotEmpty()
    }

    fun pop(): String {
        return input.removeFirst()
    }

    fun push(value: String) {
        input.add(0, value)
    }

    fun roomSteps(): Int {
        return size - input.size + 1
    }

    fun deepCopy(): Room {
        return Room(index = index, input = input.toMutableList(), size, resultValue)
    }

    fun isFor(value: String): Boolean {
        for (v in input) {
            if (v != resultValue) {
                return false
            }
        }
        return value == resultValue
    }

    fun hasValidValues(): Boolean {
        for (v in input) {
            if (v != resultValue) {
                return false
            }
        }
        return true
    }

    fun isDone(): Boolean {
        return input.size == size && hasValidValues()
    }
}
