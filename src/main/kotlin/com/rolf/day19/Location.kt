package com.rolf.day19

data class Location(val x: Int, val y: Int, val z: Int) : Comparable<Location> {

    fun allCombinations(): List<Location> {
        val combinations = mutableListOf<Location>()

//        // Rotate in 4 positions over x-axis and look in both positive and negative direction of x-axis
//        // FIXME: This doesn't work...
//        val radians = listOf(0.0, 90.0, 180.0, 270.0).map { Math.toRadians(it) }
//        for (radian in radians) {
//            val y1 = round(y * cos(radian) - z * sin(radian)).toInt()
//            val z1 = round(y * sin(radian) + z * cos(radian)).toInt()
//            combinations.add(Location(x, y1, z1))
//            combinations.add(Location(-x, y1, z1))
//        }
//
//        for (radian in radians) {
//            val z1 = round(z * cos(radian) - x * sin(radian)).toInt()
//            val x1 = round(z * sin(radian) + x * cos(radian)).toInt()
//            combinations.add(Location(x1, y, z1))
//            combinations.add(Location(x1, -y, z1))
//        }
//
//        for (radian in radians) {
//            val x1 = round(x * cos(radian) - y * sin(radian)).toInt()
//            val y1 = round(x * sin(radian) + y * cos(radian)).toInt()
//            combinations.add(Location(x1, y1, z))
//            combinations.add(Location(x1, y1, -z))
//        }

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
