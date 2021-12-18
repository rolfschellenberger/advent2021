package com.rolf.day18

import com.rolf.readLines
import kotlin.math.ceil
import kotlin.math.floor

const val DAY = "18"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")

    println("-- Part 1 --")
    solve1(lines)
    println("-- Part 2 --")
    solve2(lines)
}

class Pair(
    var left: Pair?,
    var right: Pair?,
    var value: Int?,
    var parent: Pair?
) {

    constructor(left: Pair, right: Pair) : this(left, right, null, null)

    constructor(value: Int) : this(null, null, value, null)

    init {
        if (left != null) {
            left!!.parent = this
        }
        if (right != null) {
            right!!.parent = this
        }
    }

    fun hasValue(): Boolean {
        return value != null
    }

    fun getLeft(): Int {
        if (left != null) {
            return left!!.getLeft()
        }
        return value!!
    }

    fun getLeftValuePair(): Pair {
        if (left != null) {
            return left!!.getLeftValuePair()
        }
        return this
    }

    fun getRight(): Int {
        if (right != null) {
            return right!!.getRight()
        }
        return value!!
    }

    fun getRightValuePair(): Pair {
        if (right != null) {
            return right!!.getRightValuePair()
        }
        return this
    }

    fun getParents(): List<Pair> {
        val parents = mutableListOf<Pair>()
        if (parent != null) {
            parents.add(parent!!)
            parents.addAll(parent!!.getParents())
        }
        return parents
    }

    fun hasParent(): Boolean {
        return parent != null
    }

    fun getLeftValue(): Pair? {
        // [[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]
        // Take parent and if right or parent is not me, than use left of parent to take right-most value?
        var pointer = this
        while (pointer.hasParent()) {
            if (pointer.parent!!.right!! == pointer) {
                val myLeftValuePair = pointer.parent!!.left!!.getRightValuePair()
//                println(myLeftValuePair)
                return myLeftValuePair
            }
            pointer = pointer.parent!!
        }
        return null
    }

    fun getRightValue(): Pair? {
        // [[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]
        // Take parent and if left or parent is not me, than use right of parent to take left-most value?
        var pointer = this
        while (pointer.hasParent()) {
            if (pointer.parent!!.left!! == pointer) {
                val myRightValuePair = pointer.parent!!.right!!.getLeftValuePair()
//                println(myRightValuePair)
                return myRightValuePair
            }
            pointer = pointer.parent!!
        }
        return null
    }

    fun solve() {
        var explode = true
        var split = true
        while (explode || split) {
            explode = false
            split = false

            // Go left first, next right in the tree to iterate the pairs
            val list = flatten()
//            println(list)

            // Check if they should explode or split
            for (pair in list) {
                if (pair.shouldExplode()) {
//                    println("explode=$pair")
//                    println(this)
                    pair.explode()
//                    println(this)
                    explode = true
                    break
                }
            }
            if (!explode) {
                for (pair in list) {
                    if (pair.shouldSplit()) {
//                        println("split=$pair")
//                        println(this)
                        pair.split()
//                        println(this)
                        split = true
                        break
                    }
                }
            }
        }
    }

    private fun flatten(): List<Pair> {
        val result = mutableListOf<Pair>()

        // Left first
        if (left != null) {
            result.addAll(left!!.flatten())
        }
        if (right != null) {
            result.addAll(right!!.flatten())
        }
        result.add(this)

        return result
    }

    fun shouldExplode(): Boolean {
        // Explode when it has 4 parents
        return !hasValue() && getParents().size >= 4
    }

    fun explode() {
        val leftValue = getLeftValue()
        if (leftValue != null) {
            leftValue.value = leftValue.value!! + this.getLeft()
        }
        val rightValue = getRightValue()
        if (rightValue != null) {
            rightValue.value = rightValue.value!! + this.getRight()
        }
        left = null
        right = null
        value = 0
    }

    fun shouldSplit(): Boolean {
        return hasValue() && value!! >= 10
    }

    fun split() {
        val left = floor(value!! / 2.0).toInt()
        val right = ceil(value!! / 2.0).toInt()
        this.left = Pair(left)
        this.left!!.parent = this
        this.right = Pair(right)
        this.right!!.parent = this
        this.value = null
    }

    fun magnitude(): Int {
        if (hasValue()) {
            return value!!
        }
        return 3 * left!!.magnitude() + 2 * right!!.magnitude()
    }

    override fun toString(): String {
        if (hasValue()) {
            return value.toString()
        }

        val sb = StringBuilder()
        sb.append("[")
        sb.append(left)
        sb.append(",")
        sb.append(right)
        sb.append("]")
        return sb.toString()
    }
}

fun solve1(lines: List<String>) {
//    val one = Pair(Pair(1), Pair(1))
//    val two = Pair(Pair(2), Pair(2))
//    val three = Pair(Pair(3), Pair(3))
//    val four = Pair(Pair(4), Pair(4))
//    val step1 = Pair(one, two)
//    val step2 = Pair(step1, three)
//    val step3 = Pair(step2, four)
//    println(step3)
//    step3.solve()

    var snail: Pair? = null
    for (line in lines) {
        if (snail == null) {
            snail = parse(line)
        } else {
            val next = parse(line)
            snail = Pair(snail, next)
            println("input:$snail")
            snail.solve()
            println("output:$snail")
        }
    }
    println(snail!!.magnitude())
//    snail!!.solve()

//    for (line in lines) {
//        val pair = parse(line)
//        println("input:$pair")
//        pair.solve()
//        println("output:$pair")
//    }
}

fun parse(line: String): Pair {
    // [[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]
    // Find , for the outer pair: read [ and ] until we find a , when our counter is at 1
    var counter = 0
    for (i in line.indices) {
        val char = line[i]
        if (char == '[') counter++
        if (char == ']') counter--
        if (char == ',' && counter == 1) {
            // Center found, so parse left and right (remove opening and closing brackets)
            val left = line.substring(1, i)
            val right = line.substring(i + 1, line.length - 1)
//            println("left=$left")
//            println("right=$right")
            return Pair(parse(left), parse(right))
        }
    }

    // No comma found in the center? We hit a number!
//    println(line)
    return Pair(line.toInt())
}

fun solve2(lines: List<String>) {
    var highestMagnitude = 0
    val combinations = combinations(lines)
    for (combination in combinations) {
        combination.solve()
        highestMagnitude = maxOf(highestMagnitude, combination.magnitude())
    }
    println(highestMagnitude)
}

fun combinations(lines: List<String>): List<Pair> {
    val result = mutableListOf<Pair>()
    for (a in lines.indices) {
        for (b in lines.indices) {
            if (a != b) {
                result.add(Pair(parse(lines[a]), parse(lines[b])))
            }
        }
    }
    return result
}
