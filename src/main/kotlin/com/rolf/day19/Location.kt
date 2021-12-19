package com.rolf.day19

data class Location(val x: Int, val y: Int, val z: Int) : Comparable<Location> {

    fun allCombinations(): List<Location> {
        val combinations = mutableListOf<Location>()

        combinations.add(Location(x, y, z))
        combinations.add(Location(x, y, -z))
        combinations.add(Location(x, -y, z))
        combinations.add(Location(x, -y, -z))
        combinations.add(Location(-x, y, z))
        combinations.add(Location(-x, y, -z))
        combinations.add(Location(-x, -y, z))
        combinations.add(Location(-x, -y, -z))

        combinations.add(Location(y, z, x))
        combinations.add(Location(y, z, -x))
        combinations.add(Location(y, -z, x))
        combinations.add(Location(y, -z, -x))
        combinations.add(Location(-y, z, x))
        combinations.add(Location(-y, z, -x))
        combinations.add(Location(-y, -z, x))
        combinations.add(Location(-y, -z, -x))

        combinations.add(Location(z, x, y))
        combinations.add(Location(z, x, -y))
        combinations.add(Location(z, -x, y))
        combinations.add(Location(z, -x, -y))
        combinations.add(Location(-z, x, y))
        combinations.add(Location(-z, x, -y))
        combinations.add(Location(-z, -x, y))
        combinations.add(Location(-z, -x, -y))

        combinations.add(Location(x, z, y))
        combinations.add(Location(x, z, -y))
        combinations.add(Location(x, -z, y))
        combinations.add(Location(x, -z, -y))
        combinations.add(Location(-x, z, y))
        combinations.add(Location(-x, z, -y))
        combinations.add(Location(-x, -z, y))
        combinations.add(Location(-x, -z, -y))

        combinations.add(Location(y, x, z))
        combinations.add(Location(y, x, -z))
        combinations.add(Location(y, -x, z))
        combinations.add(Location(y, -x, -z))
        combinations.add(Location(-y, x, z))
        combinations.add(Location(-y, x, -z))
        combinations.add(Location(-y, -x, z))
        combinations.add(Location(-y, -x, -z))

        combinations.add(Location(z, y, x))
        combinations.add(Location(z, y, -x))
        combinations.add(Location(z, -y, x))
        combinations.add(Location(z, -y, -x))
        combinations.add(Location(-z, y, x))
        combinations.add(Location(-z, y, -x))
        combinations.add(Location(-z, -y, x))
        combinations.add(Location(-z, -y, -x))

        return combinations
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
