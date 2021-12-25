package com.rolf.day25

import com.rolf.Matrix
import com.rolf.Point

class Floor(input: MutableList<MutableList<String>>) : Matrix<String>(input) {

    override fun toString(): String {
        return toString("", "\n")
    }

    fun move(): Int {
        var moves = 1
        var steps = 0
        while (moves > 0) {
            moves = step()
            steps++
        }
        return steps
    }

    fun step(): Int {
        val east = allPoints().filter { isEast(it) }
        val south = allPoints().filter { isSouth(it) }
        return moveEast(east) + moveSouth(south)
    }

    private fun moveEast(points: List<Point>): Int {
        val moves = points.map { it to getEast(it) }.filter { isEmpty(it.second) }
        for ((from, to) in moves) {
            set(from, ".")
            set(to, ">")
        }
        return moves.size
    }

    private fun moveSouth(points: List<Point>): Int {
        val moves = points.map { it to getSouth(it) }.filter { isEmpty(it.second) }
        for ((from, to) in moves) {
            set(from, ".")
            set(to, "v")
        }
        return moves.size
    }

    private fun getSouth(point: Point): Point {
        val y = (point.y + 1) % height()
        return Point(point.x, y)
    }

    private fun getEast(point: Point): Point {
        val x = (point.x + 1) % width()
        return Point(x, point.y)
    }

    private fun isSouth(point: Point): Boolean {
        return get(point) == "v"
    }

    private fun isEast(point: Point): Boolean {
        return get(point) == ">"
    }

    private fun isEmpty(point: Point): Boolean {
        return get(point) == "."
    }
}
