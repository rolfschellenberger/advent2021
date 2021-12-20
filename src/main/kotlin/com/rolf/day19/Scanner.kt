package com.rolf.day19

class Scanner(val id: Int, val beacons: MutableSet<Location>) {

    var location: Location? = null

    fun hasLocation(): Boolean {
        return location != null
    }

    fun join(other: Scanner) {
        if (overlap(other)) {
            beacons.addAll(other.beacons)
        }
    }

    private fun overlap(other: Scanner): Boolean {
        if (other.hasLocation()) {
            return true
        }

        // Try to find overlap in all possible rotations of the other scanner
        val beaconRotations = other.rotate()
        for (beaconRotation in beaconRotations) {
            for (beacon in beacons) {
                // Align 2 beacons and see how many other beacons overlap
                for (otherBeacon in beaconRotation) {
                    val xDiff = beacon.x - otherBeacon.x
                    val yDiff = beacon.y - otherBeacon.y
                    val zDiff = beacon.z - otherBeacon.z

                    // Detect overlap
                    val otherSet = mutableSetOf<Location>()
                    for (compareBeacon in beaconRotation) {
                        val adjustedBeacon =
                            Location(compareBeacon.x + xDiff, compareBeacon.y + yDiff, compareBeacon.z + zDiff)
                        otherSet.add(adjustedBeacon)
                    }
                    val overlap = otherSet.intersect(beacons)

                    // If there is overlap, update the other scanner with its location (relative to this scanner)
                    // and replace all beacons of the other scanner with the rotated beacons
                    if (overlap.size >= 12) {
                        other.location = Location(xDiff, yDiff, zDiff)
                        other.beacons.clear()
                        other.beacons.addAll(otherSet)
                        return true
                    }
                }
            }
        }

        return false
    }

    private fun rotate(): List<List<Location>> {
        val result = mutableListOf<MutableList<Location>>()
        for (beacon in beacons) {
            val combinations = beacon.allCombinations()

            for (index in combinations.indices) {
                if (result.size <= index) {
                    result.add(mutableListOf())
                }
                val combination = combinations[index]
                result[index].add(combination)
            }
        }
        return result
    }
}
