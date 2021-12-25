package com.rolf.day23

import kotlin.math.abs

data class Burrow(val rooms: List<Room>, val hallway: MutableList<String>) {

    private val cache = mutableMapOf<Burrow, List<Move>>()

    fun solve(maxMoves: Int = Integer.MAX_VALUE): List<List<Move>> {
        val moves = listOf(
            listOf(
                Move(this, 0)
            )
        )
        return findAllMoves(moves, maxMoves)
    }

    private fun findAllMoves(moves: List<List<Move>>, maxMoves: Int = Integer.MAX_VALUE): List<List<Move>> {
        val result = mutableListOf<List<Move>>()
        var newMovesFound = false
        for (move in moves) {
            if (move.size <= maxMoves) {
                val lastMove = move.last()
                if (lastMove.isDone()) {
                    result.add(move)
                } else {
                    // Cache!
                    val nextMoves = cache.getOrElse(lastMove.burrow) { lastMove.nextMoves() }
                    cache[lastMove.burrow] = nextMoves
                    for (nextMove in nextMoves) {
                        // Join this move and every next move option
                        val path = move + nextMove
                        result.add(path)
                        newMovesFound = true
                    }
                }
            }
        }
        if (newMovesFound) {
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
    private fun nextMoves(room: Room): List<Move> {
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
        // We can move to empty locations
        return hallway[hallwayIndex] == EMPTY
    }

    private fun canMoveFromHallwayToRoom(hallwayIndex: Int, roomIndex: Int): Boolean {
        if (!isRoomIndex(roomIndex)) return false
        val value = hallway[hallwayIndex]
        val room = getRoom(roomIndex)
        return room.isFor(value)
    }

    private fun getRoom(index: Int): Room {
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
