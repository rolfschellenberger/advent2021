package com.rolf.day19

import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin

data class Location(val x: Int, val y: Int, val z: Int) : Comparable<Location> {

    fun allCombinations(): List<Location> {
        val combinations = mutableListOf<Location>()

        // front
        combinations.add(rotateZ(0.0))
        combinations.add(rotateZ(90.0))
        combinations.add(rotateZ(180.0))
        combinations.add(rotateZ(270.0))

        // right
        combinations.add(rotateY(90.0))
        combinations.add(rotateY(90.0).rotateZ(90.0))
        combinations.add(rotateY(90.0).rotateZ(180.0))
        combinations.add(rotateY(90.0).rotateZ(270.0))

        // back
        combinations.add(rotateY(180.0))
        combinations.add(rotateY(180.0).rotateZ(90.0))
        combinations.add(rotateY(180.0).rotateZ(180.0))
        combinations.add(rotateY(180.0).rotateZ(270.0))

        // left
        combinations.add(rotateY(270.0))
        combinations.add(rotateY(270.0).rotateZ(90.0))
        combinations.add(rotateY(270.0).rotateZ(180.0))
        combinations.add(rotateY(270.0).rotateZ(270.0))

        // up
        combinations.add(rotateX(90.0))
        combinations.add(rotateX(90.0).rotateZ(90.0))
        combinations.add(rotateX(90.0).rotateZ(180.0))
        combinations.add(rotateX(90.0).rotateZ(270.0))

        // down
        combinations.add(rotateX(270.0))
        combinations.add(rotateX(270.0).rotateZ(90.0))
        combinations.add(rotateX(270.0).rotateZ(180.0))
        combinations.add(rotateX(270.0).rotateZ(270.0))

        return combinations
    }

    private fun rotateX(degrees: Double): Location {
        val radians = Math.toRadians(degrees)
        val y1 = round(y * cos(radians) - z * sin(radians)).toInt()
        val z1 = round(y * sin(radians) + z * cos(radians)).toInt()
        return Location(x, y1, z1)
    }

    private fun rotateY(degrees: Double): Location {
        val radians = Math.toRadians(degrees)
        val z1 = round(z * cos(radians) - x * sin(radians)).toInt()
        val x1 = round(z * sin(radians) + x * cos(radians)).toInt()
        return Location(x1, y, z1)
    }

    private fun rotateZ(degrees: Double): Location {
        val radians = Math.toRadians(degrees)
        val x1 = round(x * cos(radians) - y * sin(radians)).toInt()
        val y1 = round(x * sin(radians) + y * cos(radians)).toInt()
        return Location(x1, y1, z)
    }

    override fun toString(): String {
        return "$x,$y,$z"
    }

    override fun compareTo(other: Location): Int {
        val xCompare = x.compareTo(other.x)
        if (xCompare != 0) return xCompare
        val yCompare = y.compareTo(other.y)
        if (yCompare != 0) return yCompare
        return z.compareTo(other.z)
    }
}
