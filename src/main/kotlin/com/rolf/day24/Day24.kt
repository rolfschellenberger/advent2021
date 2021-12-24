package com.rolf.day24

import com.rolf.readLines

const val DAY = "24"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")

    // 69914999975369
    // 14911675311114
    println("-- Part 1 --")
    solve1(lines)
    println("-- Part 2 --")
//    solve2(lines)
}

fun solve1(lines: List<String>) {
    val instructions = parseInstructions(lines)
//    val result = runInstructions(instructions, 0, State(0, 0, 0, 0))
//    println(result)

//    var number = 69914999975369
    var number = 99999999999999
    while (true) {
        if (isValid(number)) {
            if (number % 10001 == 0L) {
                println(number)
            }
            val input = toInput(number)
            val state = State(0, 0, 0, 0)
            for (instruction in instructions) {
                if (instruction.needsInput()) {
                    instruction.input = input.removeAt(0)
                }
                instruction.run(state)
//                println(state)
            }
//            println("$number = ${state.z}")
            if (state.z == 0) {
                println(state)
                return
            }
        }
        number -= 1
    }
}

val cache = mutableMapOf<String, String?>()

fun runInstructions(instructions: List<Instruction>, step: Int, state: State): String? {
    // All instructions done?
    if (step >= instructions.size) {
//        return ""
        return if (state.z == 0) {
            ""
        } else {
            null
        }
    }

    // Look up in cache
//    val key = "${step}-$state"
//    if (cache.containsKey(key)) {
//        return cache[key]
//    }

    // Run the instruction and spawn 9 different options on input
    val instruction = instructions[step]
    if (instruction.needsInput()) {
        for (i in 9 downTo 1) {
            val stateVersion = state.copy()
            instruction.input = i
            // Use nines at the start 0, 18, 36, 54, 72, 90, 108, 126, 144, 162, 180, 198, 216, 234
//            if (step <= 0) instruction.input = 9
            instruction.run(stateVersion)

            val key = "${step + 1}-$stateVersion"
            if (cache.containsKey(key)) {
                return cache[key]
            }
            val value = runInstructions(instructions, step + 1, stateVersion)
            cache[key] = value
            if (value != null) {
                return "${i}$value"
            }
        }
    } else {
        instruction.run(state)
        return runInstructions(instructions, step + 1, state)
    }
    return null
}

fun toInput(number: Long): MutableList<Int> {
    return number.toString().toCharArray().map { it.toString().toInt() }.toMutableList()
}

fun isValid(number: Long): Boolean {
    return !number.toString().contains("0")
}

fun parseInstructions(lines: List<String>): List<Instruction> {
    return lines.map { parseInstruction(it) }
}

fun parseInstruction(line: String): Instruction {
    val elements = line.split(" ")
    return when (elements[0]) {
        "inp" -> Inp(elements)
        "add" -> Add(elements)
        "mul" -> Mul(elements)
        "div" -> Div(elements)
        "mod" -> Mod(elements)
        "eql" -> Eql(elements)
        else -> throw Exception("Unsupported instruction")
    }
}

data class State(var w: Int, var x: Int, var y: Int, var z: Int) {
    fun getValue(field: String): Int {
        return when (field) {
            "w" -> w
            "x" -> x
            "y" -> y
            "z" -> z
            else -> throw Exception("Incorrect field found: $field")
        }
    }

    fun setValue(field: String, value: Int) {
        when (field) {
            "w" -> w = value
            "x" -> x = value
            "y" -> y = value
            "z" -> z = value
            else -> throw Exception("Incorrect field found: $field")
        }
    }
}

abstract class Instruction(val args: List<String>) {
    var input: Int = 0

    abstract fun run(state: State)

    open fun needsInput(): Boolean = false

    private fun isNumber(input: String): Boolean {
        return input != "w" && input != "x" && input != "y" && input != "z"
    }

    fun getValue(state: State, value: String): Int {
        if (isNumber(value)) {
            return value.toInt()
        }

        return state.getValue(value)
    }

    fun setValue(state: State, field: String, value: Int) {
        state.setValue(field, value)
    }

    override fun toString(): String {
        return "${javaClass.simpleName}{input=$input, args=$args}"
    }
}

// a = (w,x,y,z)
// b = (w,x,y,z) or number
class Inp(args: List<String>) : Instruction(args) {
    // inp a - Read an input value and write it to variable a.
    override fun run(state: State) {
        state.setValue(args[1], input)
    }

    override fun needsInput(): Boolean = true
}

class Add(args: List<String>) : Instruction(args) {
    // add a b - Add the value of a to the value of b, then store the result in variable a.
    override fun run(state: State) {
        val a = getValue(state, args[1])
        val b = getValue(state, args[2])
        val sum = a + b
        setValue(state, args[1], sum)
    }
}

class Mul(args: List<String>) : Instruction(args) {
    // mul a b - Multiply the value of a by the value of b, then store the result in variable a.
    override fun run(state: State) {
        val a = getValue(state, args[1])
        val b = getValue(state, args[2])
        val mul = a * b
        setValue(state, args[1], mul)
    }
}

class Div(args: List<String>) : Instruction(args) {
    // div a b - Divide the value of a by the value of b, truncate the result to an integer, then store the result in variable a. (Here, "truncate" means to round the value toward zero.)
    override fun run(state: State) {
        val a = getValue(state, args[1]).toDouble()
        val b = getValue(state, args[2]).toDouble()
        val div = (a / b).toInt()
        setValue(state, args[1], div)
    }
}

class Mod(args: List<String>) : Instruction(args) {
    // mod a b - Divide the value of a by the value of b, then store the remainder in variable a. (This is also called the modulo operation.)
    override fun run(state: State) {
        val a = getValue(state, args[1])
        val b = getValue(state, args[2])
        val mod = a % b
        setValue(state, args[1], mod)
    }
}

class Eql(args: List<String>) : Instruction(args) {
    // eql a b - If the value of a and b are equal, then store the value 1 in variable a. Otherwise, store the value 0 in variable a.
    override fun run(state: State) {
        val a = getValue(state, args[1])
        val b = getValue(state, args[2])
        val eql = if (a == b) 1 else 0
        setValue(state, args[1], eql)
    }
}

fun solve2(lines: List<String>) {
    run(lines, false)
}

fun run(lines: List<String>, part2: Boolean): Any {
    val blocks = mutableListOf<List<String>>()
    val subList = mutableListOf<String>()
    for (line in lines) {
        subList.add(line)
        if (subList.size == 18) {
            blocks.add(subList.toList())
            subList.clear()
        }
    }
    blocks.add(subList)
    val result = MutableList(14) { -1 }
    val buffer = ArrayDeque<Pair<Int, Int>>()
    fun List<String>.lastOf(command: String) = last { it.startsWith(command) }.split(" ").last().toInt()
    val best = if (part2) 1 else 9
    blocks.forEachIndexed { index, instructions ->
        if ("div z 26" in instructions) {
            val offset = instructions.lastOf("add x")
            val (lastIndex, lastOffset) = buffer.removeFirst()
            val difference = offset + lastOffset
            if (difference >= 0) {
                result[lastIndex] = if (part2) best else best - difference
                result[index] = if (part2) best + difference else best
            } else {
                result[lastIndex] = if (part2) best - difference else best
                result[index] = if (part2) best else best + difference
            }
        } else buffer.addFirst(index to instructions.lastOf("add z"))
    }

    return result.joinToString("").toLong()
}
