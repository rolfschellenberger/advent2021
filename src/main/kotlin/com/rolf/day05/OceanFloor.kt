package com.rolf.day05

import com.rolf.Matrix

data class OceanFloor(val input: List<MutableList<Int>>) : Matrix<Int>(input) {
    fun place(pipe: Pipe) {
        val points = getPointsBetween(pipe.start, pipe.end)

        for (point in points) {
            set(point.x, point.y, get(point.x, point.y) + 1)
        }
    }

    private fun getPointsBetween(start: Point, end: Point): List<Point> {
        val minX = minOf(start.x, end.x)
        val maxX = maxOf(start.x, end.x)
        val minY = minOf(start.y, end.y)
        val maxY = maxOf(start.y, end.y)

        val points = mutableListOf<Point>()
        // Horizontal?
        if (minX == maxX || minY == maxY) {
            for (y in minY..maxY) {
                for (x in minX..maxX) {
                    points.add(Point(x, y))
                }
            }
        }
        // Diagonal?
        else {
            val yDelta = if (end.y > start.y) 1 else -1
            val xDelta = if (end.x > start.x) 1 else -1
            for (i in 0..(maxY - minY)) {
                points.add(Point(start.x + (i * xDelta), start.y + (i * yDelta)))
            }
        }
        return points
    }
}