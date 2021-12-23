package com.rolf.day23

import com.rolf.readLines
import kotlin.math.abs

const val DAY = "23"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")

    println("-- Part 1 --")
    solve1(lines)
    println("-- Part 2 --")
//    solve2(lines)
}

fun solve1(lines: List<String>) {
    val testRooms = listOf(
        Room(2, mutableListOf("C", "D", "D", "C"), 4, "A"),
        Room(4, mutableListOf("B"), 4, "B"),
        Room(6, mutableListOf("A", "B", "A", "A"), 4, "C"),
        Room(8, mutableListOf("D", "A", "C", "B"), 4, "D")
    )
    val testHallway = "CD.....B...".toCharArray().map { it.toString() }.toMutableList()
    val testBurrow = Burrow(testRooms, testHallway)
//    testBurrow.solve()
//    return

    val rooms = listOf(
        Room(2, mutableListOf("C", "D", "D", "C"), 4, "A"),
        Room(4, mutableListOf("B", "C", "B", "D"), 4, "B"),
        Room(6, mutableListOf("A", "B", "A", "A"), 4, "C"),
        Room(8, mutableListOf("D", "A", "C", "B"), 4, "D")
    )
    val hallway = mutableListOf<String>()
    for (i in 0 until 11) {
        hallway.add(Burrow.EMPTY)
    }
    val burrow = Burrow(rooms, hallway)
//    println(burrow)

    val steps = burrow.solve()
    var lowestScore = Integer.MAX_VALUE
    var lowestPath = steps.first()
    for (step in steps) {
        val lastState = step.last()
        if (lastState.isDone()) {
//            println(lastState)
//            println(step.map { it.cost }.sum())
            val score = step.map { it.cost }.sum()
            if (score < lowestScore) {
                lowestScore = score
                lowestPath = step
            }
        }
    }
    for (step in lowestPath) {
        println(step)
    }
    println("${steps.size} possible solutions found")
    println("steps: ${lowestPath.size}")
    println("lowest score: $lowestScore")
}

data class Burrow(val rooms: List<Room>, val hallway: MutableList<String>) {

    fun solve(): List<List<Move>> {
        val moves = listOf(
            listOf(
                Move(this, 0)
            )
        )
        return findAllMoves(moves)
    }

    private fun findAllMoves(moves: List<List<Move>>): List<List<Move>> {
        val result = mutableListOf<List<Move>>()
        var newMovesFound = false
        for (move in moves) {
            val lastMove = move.last()
//            println(lastMove.burrow)
            if (lastMove.isDone()) {
                result.add(move)
            } else {
                val nextMoves = lastMove.nextMoves()
                for (nextMove in nextMoves) {
                    // Join this move and every next move option
                    val path = move + nextMove
                    result.add(path)
                    newMovesFound = true
                }
            }
        }
        if (newMovesFound) {
//            println("New moves found")
//            for (r in result) {
//                if (r.last().hasHallway(".......B...".toCharArray().map { it.toString() })) {
//                    println(r.last())
//                }
//                if (r.last().hasHallway("C......B...".toCharArray().map { it.toString() })) {
//                    println(r.last())
//                }
//                if (r.last().hasHallway("C....B.B...".toCharArray().map { it.toString() })) {
//                    println(r.last())
//                }
//                if (r.last().hasHallway("CD...B.B...".toCharArray().map { it.toString() })) {
//                    println(r.last())
//                }
//                if (r.last().hasHallway("CD.....B...".toCharArray().map { it.toString() })) {
//                    println(r.last())
//                }
//                if (r.last().hasHallway("CD.........".toCharArray().map { it.toString() })) {
//                    println(r.last())
//                }
//            }
            return findAllMoves(result)
        }
        return result
    }

    fun nextMoves(): List<Move> {
        val moves = mutableListOf<Move>()

        // Moves from rooms to hallway
        for (room in rooms) {
            val roomMoves = nextMoves(room)
            moves.addAll(roomMoves)
        }

        // Moves from hallway to rooms
        for (index in 0 until hallway.size) {
            val hallwayMoves = nextMoves(index)
            moves.addAll(hallwayMoves)
        }

        return moves
    }

    // Room to hallways
    fun nextMoves(room: Room): List<Move> {
        val moves = mutableListOf<Move>()

        if (!room.hasValue()) {
            return moves
        }
        if (room.hasValidValues()) {
            return moves
        }

        // Start from room index to left
        for (index in room.index - 1 downTo 0) {
            // We can only move to non-room exit locations
            if (!isRoomIndex(index)) {
                if (canMoveToHallway(index)) {
                    val move = moveOut(room.index, index)
                    moves.add(move)
                } else {
                    // If we cannot move, break the loop
                    break
                }
            }
        }

        // Continue from room index to right
        for (index in room.index + 1 until hallway.size) {
            // We can only move to non-room exit locations
            if (!isRoomIndex(index)) {
                if (canMoveToHallway(index)) {
                    val move = moveOut(room.index, index)
                    moves.add(move)
                } else {
                    // If we cannot move, break the loop
                    break
                }
            }
        }

        return moves
    }

    // Hallway to rooms
    private fun nextMoves(hallwayIndex: Int): List<Move> {
        val moves = mutableListOf<Move>()

        if (isEmpty(hallwayIndex)) {
            return moves
        }

        // Start from hallway index to left
        for (index in hallwayIndex - 1 downTo 0) {
            if (canMoveToHallway(index)) {
                // Only move to rooms that can store this value
                if (canMoveFromHallwayToRoom(hallwayIndex, index)) {
                    val move = moveIn(hallwayIndex, index)
                    moves.add(move)
                }
            } else {
                // If we cannot move, break the loop
                break
            }
        }

        // Continue from hallway index to right
        for (index in hallwayIndex + 1 until hallway.size) {
            if (canMoveToHallway(index)) {
                // Only move to rooms that can store this value
                if (canMoveFromHallwayToRoom(hallwayIndex, index)) {
                    val move = moveIn(hallwayIndex, index)
                    moves.add(move)
                }
            } else {
                // If we cannot move, break the loop
                break
            }
        }

        return moves
    }

    private fun isEmpty(hallwayIndex: Int): Boolean {
        return hallway[hallwayIndex] == EMPTY
    }

    private fun isRoomIndex(index: Int): Boolean {
        for (room in rooms) {
            if (room.index == index) {
                return true
            }
        }
        return false
    }

    private fun moveOut(roomIndex: Int, toIndex: Int): Move {
        val burrow = deepCopy()
        val room = burrow.getRoom(roomIndex)
        val roomSteps = room.roomSteps()
        val value = room.pop()
        val steps = abs(roomIndex - toIndex) + roomSteps
        val cost = cost(steps, value)
        burrow.hallway[toIndex] = value
        return Move(burrow, cost)
    }

    private fun moveIn(hallwayIndex: Int, toIndex: Int): Move {
        val burrow = deepCopy()
        val room = burrow.getRoom(toIndex)
        val value = burrow.hallway[hallwayIndex]
        room.push(value)
        val roomSteps = room.roomSteps()
        val steps = abs(hallwayIndex - toIndex) + roomSteps
        val cost = cost(steps, value)
        burrow.hallway[hallwayIndex] = EMPTY
        return Move(burrow, cost)
    }

    private fun cost(steps: Int, value: String): Int {
        val cost = when (value) {
            "A" -> 1
            "B" -> 10
            "C" -> 100
            "D" -> 1000
            else -> 0
        }
        return steps * cost
    }

    private fun canMoveToHallway(hallwayIndex: Int): Boolean {
        // We cannot move to the position of a room exit
        // FIXME
//        if (rooms.map { it.index }.contains(index)) {
//            return false
//        }
        // We can move to empty locations
        return hallway[hallwayIndex] == EMPTY
    }

    private fun canMoveFromHallwayToRoom(hallwayIndex: Int, roomIndex: Int): Boolean {
        if (!isRoomIndex(roomIndex)) return false
        val value = hallway[hallwayIndex]
        val room = getRoom(roomIndex)
        return room.isFor(value)
    }

    fun getRoom(index: Int): Room {
        for (room in rooms) {
            if (room.index == index) {
                return room
            }
        }
        throw Exception("Room not found with index $index")
    }

    private fun deepCopy(): Burrow {
        val newRooms = rooms.map { it.deepCopy() }
        val hallway = hallway.toMutableList()
        return copy(rooms = newRooms, hallway = hallway)
    }

    fun isDone(): Boolean {
        for (room in rooms) {
            if (!room.isDone()) {
                return false
            }
        }
        return true
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("#")
        for (i in 0 until hallway.size) {
            sb.append("#")
        }
        sb.append("#")
        sb.append("\n")
        sb.append("#")
        for (i in 0 until hallway.size) {
            sb.append(hallway[i])
        }
        sb.append("#")
        sb.append("\n")
        for (lines in 0 until rooms[0].size) {
            sb.append("#")
            for (i in 0 until hallway.size) {
                if (isRoomIndex(i)) {
                    val room = getRoom(i)
                    if (lines < room.input.size) {
                        sb.append(getRoom(i).input[lines])
                    } else {
                        sb.append(" ")
                    }
                } else {
                    sb.append("#")
                }
            }
            sb.append("#")
            sb.append("\n")
        }
        sb.append("#")
        for (i in 0 until hallway.size) {
            sb.append("#")
        }
        sb.append("#")
        return sb.toString()
    }

    companion object {
        const val EMPTY = "."
    }
}

data class Room(val index: Int, val input: MutableList<String>, val size: Int, val resultValue: String) {
    //    private val stack = Stack<String>()

//    init {
//        for (value in input) {
//            stack.push(value)
//        }
//    }

//    fun first(): String {
//        return input.first()
//    }

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

data class Move(val burrow: Burrow, val cost: Int) {
    fun isDone(): Boolean {
        return burrow.isDone()
    }

    fun nextMoves(): List<Move> {
        return burrow.nextMoves()
    }

    fun hasHallway(pattern: List<String>): Boolean {
        return burrow.hallway == pattern
    }

    override fun toString(): String {
        return burrow.toString()
    }
}

fun solve2(lines: List<String>) {

}
