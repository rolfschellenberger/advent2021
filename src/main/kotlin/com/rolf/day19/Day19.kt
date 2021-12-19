package com.rolf.day19

import com.rolf.groupLines
import com.rolf.readLines
import javax.print.attribute.IntegerSyntax

const val DAY = "19"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")
    val scannerLines = groupLines(lines, "")
    val scanners = parseScanners(scannerLines)

    println("-- Part 1 --")
    solve1(scanners)
    println("-- Part 2 --")
    solve2(lines)
}

fun parseScanners(scannerLines: List<List<String>>): List<Scanner> {
    val scanners = mutableListOf<Scanner>()

    for (scannerLine in scannerLines) {
        var scannerId = 0
        val beacons = mutableSetOf<Beacon>()
        for (i in scannerLine.indices) {
            val line = scannerLine[i]
            if (i == 0) {
                scannerId = line.split(" ")[2].toInt()
            } else {
                val (x, y, z) = line.split(",").map { it.toInt() }
                val beacon = Beacon(x, y, z)
                beacons.add(beacon)
            }
        }
        scanners.add(Scanner(scannerId, beacons))
    }
    return scanners
}

fun solve1(scanners: List<Scanner>) {
//    println(scanners)

    val scanner0 = scanners[0]

//    var previousSize = 0
//    while (scanner0.beacons.size != previousSize) {
    for (i in scanners.indices) {
        println("========: $i")
        for (scannerA in scanners) {
            for (scannerB in scanners) {
                if (scannerA.id != scannerB.id) {
                    scannerA.join(scannerB)
//                val overlap = scannerA.overlap(scannerB)
//                if (overlap.size >= 12) {
//                    println("scanner ${scannerA.id}=${scannerB.id}")
//                    println("overlap=$overlap")
//                }
                }
            }
        }
        println("#########################")
        println(scanner0.beacons.sorted().joinToString("\n"))
        println(scanner0.beacons.size)
    }
//        previousSize = scanner0.beacons.size
//        println(scanner0.beacons.size)
//    }

//    println(scanners)
    println("#########################")
    println(scanner0.beacons.sorted().joinToString("\n"))
    println(scanner0.beacons.size)
}

fun solve2(lines: List<String>) {
}

class Scanner(val id: Int, val beacons: MutableSet<Beacon>) {

    override fun toString(): String {
//        return "Scanner{id={$id}, beacons={$beacons}}"
        val sb = StringBuilder()
        sb.append("Scanner{id=$id")
        for (beacon in beacons) {
            sb.append("\n")
            sb.append(beacon)
        }
        sb.append("}")
        return sb.toString()
    }

//    fun overlap(other: Scanner): Set<Beacon> {
//
//        // Detect overlap count (without rotation yet!)
//        val beaconRotations = other.rotate()
//        var count = 0
//        for (beaconRotation in beaconRotations) {
//            count++
//            for (beacon in beacons) {
//                // Align 2 beacons and see how many other beacons overlap
//                for ((index, otherBeacon) in beaconRotation.withIndex()) {
//                    val xDiff = beacon.x - otherBeacon.x
//                    val yDiff = beacon.y - otherBeacon.y
//                    val zDiff = beacon.z - otherBeacon.z
//                    // Detect overlap
//                    val otherSet = mutableSetOf<Beacon>()
//                    for (compareBeacon in beaconRotation) {
//                        val adjustedBeacon =
//                            Beacon(compareBeacon.x + xDiff, compareBeacon.y + yDiff, compareBeacon.z + zDiff)
//                        otherSet.add(adjustedBeacon)
//                    }
//                    val overlap = otherSet.intersect(beacons)
//                    if (overlap.size >= 12) {
//                        // Now return the overlapping set seen from the this scanner
//                        return overlap
////                        println("=============")
////                        println(beacon)
////                        println(otherBeacon)
////                        println(other.beacons[index])
////                        println(overlap)
////                        println(overlap)
////                        println(overlap.size)
////                        return overlap.size
//                    }
//                }
//            }
//        }
//
//        return emptySet()
//    }

    fun join(other: Scanner) {
        // Joining is done by taking the current beacons and adding the other beacons adjusted/rotated
        // If they overlap, we want to know the non-overlapping beacons with their adjusted locations
        println("scanner A: $id")
        println("scanner B: ${other.id}")
        val newBeacons = notOverlap(other)
//        println("===========")
//        println(this.beacons.sorted().joinToString("\n"))
//        println("===========")
        this.beacons.addAll(newBeacons)
//        println(newBeacons.sorted().joinToString("\n"))
//        println("===========")
//        println(this.beacons.sorted().joinToString("\n"))
//        println("===========")
//        println()
    }

    private val overlapDiff = mutableMapOf<Int, Overlap>()

    fun notOverlap(other: Scanner): Set<Beacon> {

        // Checked the overlapping before?
        if (overlapDiff.containsKey(other.id)) {
            val overlapInfo = overlapDiff[other.id]!!
            val beaconRotation = other.rotate()[overlapInfo.rotationIndex]
            val diff = overlapInfo.difference
            val otherSet = mutableSetOf<Beacon>()
            for (compareBeacon in beaconRotation) {
                val adjustedBeacon =
                    Beacon(compareBeacon.x + diff.x, compareBeacon.y + diff.y, compareBeacon.z + diff.z)
                otherSet.add(adjustedBeacon)
            }
            otherSet.removeAll(beacons)
            return otherSet
        }

        // Detect overlap count (without rotation yet!)
        val beaconRotations = other.rotate()
        var count = 0
        for (beaconRotation in beaconRotations) {
            count++
            for (beacon in beacons) {
                // Align 2 beacons and see how many other beacons overlap
                for ((index, otherBeacon) in beaconRotation.withIndex()) {
                    val xDiff = beacon.x - otherBeacon.x
                    val yDiff = beacon.y - otherBeacon.y
                    val zDiff = beacon.z - otherBeacon.z
                    // Detect overlap
                    val otherSet = mutableSetOf<Beacon>()
                    for (compareBeacon in beaconRotation) {
                        val adjustedBeacon =
                            Beacon(compareBeacon.x + xDiff, compareBeacon.y + yDiff, compareBeacon.z + zDiff)
                        otherSet.add(adjustedBeacon)
                    }
                    val overlap = otherSet.intersect(beacons)
                    if (overlap.size >= 12) {
                        // Cache!
                        overlapDiff[other.id] = Overlap(other, Beacon(xDiff, yDiff, zDiff), index)

                        // Now return the new beacons seen from the this scanner
                        otherSet.removeAll(beacons)
                        return otherSet
                    }
                }
            }
        }

        return emptySet()
    }

    private fun rotate(): List<List<Beacon>> {
        val result = mutableListOf<MutableList<Beacon>>()
        for (i in 0 until 48) {
            result.add(mutableListOf())
        }

        for (beacon in beacons) {
            val combinations = beacon.allCombinations()

            for (index in combinations.indices) {
                val combination = combinations[index]
                result[index].add(combination)
            }
        }
        return result
    }
}

data class Overlap(val scanner: Scanner, val difference: Beacon, val rotationIndex: Int)

data class Beacon(val x: Int, val y: Int, val z: Int) : Comparable<Beacon> {

    private val combinations = mutableListOf<Beacon>()

    fun allCombinations(): List<Beacon> {
        if (combinations.isEmpty()) {
            // Use all 48 combinations possible
            combinations.add(Beacon(x, y, z))
            combinations.add(Beacon(x, y, -z))
            combinations.add(Beacon(x, -y, z))
            combinations.add(Beacon(x, -y, -z))
            combinations.add(Beacon(-x, y, z))
            combinations.add(Beacon(-x, y, -z))
            combinations.add(Beacon(-x, -y, z))
            combinations.add(Beacon(-x, -y, -z))

            combinations.add(Beacon(y, z, x))
            combinations.add(Beacon(y, z, -x))
            combinations.add(Beacon(y, -z, x))
            combinations.add(Beacon(y, -z, -x))
            combinations.add(Beacon(-y, z, x))
            combinations.add(Beacon(-y, z, -x))
            combinations.add(Beacon(-y, -z, x))
            combinations.add(Beacon(-y, -z, -x))

            combinations.add(Beacon(z, x, y))
            combinations.add(Beacon(z, x, -y))
            combinations.add(Beacon(z, -x, y))
            combinations.add(Beacon(z, -x, -y))
            combinations.add(Beacon(-z, x, y))
            combinations.add(Beacon(-z, x, -y))
            combinations.add(Beacon(-z, -x, y))
            combinations.add(Beacon(-z, -x, -y))

            combinations.add(Beacon(x, z, y))
            combinations.add(Beacon(x, z, -y))
            combinations.add(Beacon(x, -z, y))
            combinations.add(Beacon(x, -z, -y))
            combinations.add(Beacon(-x, z, y))
            combinations.add(Beacon(-x, z, -y))
            combinations.add(Beacon(-x, -z, y))
            combinations.add(Beacon(-x, -z, -y))

            combinations.add(Beacon(y, x, z))
            combinations.add(Beacon(y, x, -z))
            combinations.add(Beacon(y, -x, z))
            combinations.add(Beacon(y, -x, -z))
            combinations.add(Beacon(-y, x, z))
            combinations.add(Beacon(-y, x, -z))
            combinations.add(Beacon(-y, -x, z))
            combinations.add(Beacon(-y, -x, -z))

            combinations.add(Beacon(z, y, x))
            combinations.add(Beacon(z, y, -x))
            combinations.add(Beacon(z, -y, x))
            combinations.add(Beacon(z, -y, -x))
            combinations.add(Beacon(-z, y, x))
            combinations.add(Beacon(-z, y, -x))
            combinations.add(Beacon(-z, -y, x))
            combinations.add(Beacon(-z, -y, -x))
        }

        return combinations
    }

    override fun toString(): String {
        return "$x,$y,$z"
    }

    override fun compareTo(other: Beacon): Int {
        val xCompare = x.compareTo(other.x)
        if (xCompare != 0) return xCompare
        val yCompare = y.compareTo(other.y)
        if (yCompare != 0) return yCompare
        return z.compareTo(other.z)
    }
}
