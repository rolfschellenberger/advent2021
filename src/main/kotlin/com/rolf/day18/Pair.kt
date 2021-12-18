package com.rolf.day18

import kotlin.math.ceil
import kotlin.math.floor

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
            left?.parent = this
        }
        if (right != null) {
            right?.parent = this
        }
    }

    private fun hasValue(): Boolean {
        return value != null
    }

    private fun getValue(): Int {
        return if (value != null) value!! else -1
    }

    private fun getLeftValue(): Int {
        if (left != null) {
            return left!!.getLeftValue()
        }
        return getValue()
    }

    private fun getLeftMostValuePair(): Pair {
        if (left != null) {
            return left!!.getLeftMostValuePair()
        }
        return this
    }

    private fun getRightValue(): Int {
        if (right != null) {
            return right!!.getRightValue()
        }
        return getValue()
    }

    private fun getRightMostValuePair(): Pair {
        if (right != null) {
            return right!!.getRightMostValuePair()
        }
        return this
    }

    private fun hasParent(): Boolean {
        return parent != null
    }

    private fun getParents(): List<Pair> {
        val parents = mutableListOf<Pair>()
        if (parent != null) {
            parents.add(parent!!)
            parents.addAll(parent!!.getParents())
        }
        return parents
    }

    private fun getValueToTheLeft(): Pair? {
        // Take parent and if right or parent is not me, than use left of parent to take right-most value?
        var pointer = this
        while (pointer.hasParent()) {
            if (pointer.parent?.right == pointer) {
                return pointer.parent?.left?.getRightMostValuePair()
            }
            pointer = pointer.parent!!
        }
        return null
    }

    private fun getValueToTheRight(): Pair? {
        // Take parent and if left or parent is not me, than use right of parent to take left-most value?
        var pointer = this
        while (pointer.hasParent()) {
            if (pointer.parent?.left == pointer) {
                return pointer.parent?.right?.getLeftMostValuePair()
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
//            println(this)

            // Go left first, next right in the tree to iterate the pairs
            val list = flatten()

            // Check if they should explode or split
            for (pair in list) {
                if (pair.shouldExplode()) {
//                    println("explode=$pair")
                    pair.explode()
                    explode = true
                    break
                }
            }
            if (!explode) {
                for (pair in list) {
                    if (pair.shouldSplit()) {
//                        println("split=$pair")
                        pair.split()
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
        val leftValue = getValueToTheLeft()
        if (leftValue != null) {
            leftValue.value = leftValue.getValue() + this.getLeftValue()
        }
        val rightValue = getValueToTheRight()
        if (rightValue != null) {
            rightValue.value = rightValue.getValue() + this.getRightValue()
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
        this.left?.parent = this
        this.right = Pair(right)
        this.right?.parent = this
        this.value = null
    }

    fun magnitude(): Int {
        if (hasValue()) {
            return getValue()
        }
        return 3 * left!!.magnitude() + 2 * right!!.magnitude()
    }

    override fun toString(): String {
        if (hasValue()) {
            return getValue().toString()
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
