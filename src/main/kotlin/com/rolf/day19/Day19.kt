package com.rolf.day19

import com.rolf.groupLines
import com.rolf.readLines
import kotlin.math.abs

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
    solve2(scanners)
}

fun parseScanners(scannerLines: List<List<String>>): List<Scanner> {
    val scanners = mutableListOf<Scanner>()

    for (scannerLine in scannerLines) {
        var scannerId = 0
        val beacons = mutableSetOf<Location>()
        for (i in scannerLine.indices) {
            val line = scannerLine[i]
            if (i == 0) {
                scannerId = line.split(" ")[2].toInt()
            } else {
                val (x, y, z) = line.split(",").map { it.toInt() }
                val beacon = Location(x, y, z)
                beacons.add(beacon)
            }
        }
        scanners.add(Scanner(scannerId, beacons))
    }
    return scanners
}

fun solve1(scanners: List<Scanner>) {
    val scanner0 = scanners[0]

    // Join all scanners to the perspective of scanner 0
    var remainingCount = remainingCount(scanners)
    while (remainingCount != 0) {
        println("Scanners remaining to align with scanner 0: $remainingCount")
        for (scanner in scanners) {
            scanner0.join(scanner)
        }
        remainingCount = remainingCount(scanners)
    }
    println(scanner0.beacons.size)
}

fun remainingCount(scanners: List<Scanner>): Int {
    return scanners.size - scanners.map { if (it.hasLocation()) 1 else 0 }.sum()
}

fun solve2(scanners: List<Scanner>) {
    // Now it's easy to calculate the distance with all scanners their locations found
    val locations = scanners.map { it.location!! }
    var largestDistance = 0
    for (locationA in locations) {
        for (locationB in locations) {
            val distance = abs(locationA.x - locationB.x) +
                    abs(locationA.y - locationB.y) +
                    abs(locationA.z - locationB.z)
            largestDistance = maxOf(largestDistance, distance)
        }
    }
    println(largestDistance)
}
