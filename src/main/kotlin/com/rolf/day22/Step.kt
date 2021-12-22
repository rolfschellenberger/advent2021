package com.rolf.day22

data class Step(val on: Boolean, val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {

    fun toCubes(): List<Cube> {
        val cubes = mutableListOf<Cube>()
        for (z in zRange) {
            for (y in yRange) {
                for (x in xRange) {
                    cubes.add(Cube(x, y, z))
                }
            }
        }
        return cubes
    }

    fun toArea(): Area {
        return Area(xRange, yRange, zRange)
    }
}
