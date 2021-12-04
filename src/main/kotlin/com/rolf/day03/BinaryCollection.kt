package com.rolf.day03

import com.rolf.BinaryNumber

class BinaryCollection() {
    var elements: MutableList<BinaryNumber> = mutableListOf();

    fun add(number: BinaryNumber) {
        elements.add(number)
    }

    fun filterMostCommon(position: Int) {
        if (elements.size <= 1) {
            return
        }
        val one = mutableListOf<BinaryNumber>()
        val zero = mutableListOf<BinaryNumber>()
        for(element in elements) {
            val value = element.get(position).toInt()
            if (value == 0) {
                zero.add(element)
            } else {
                one.add(element)
            }
        }

        if (one.size >= zero.size) {
            elements.removeAll(zero)
        } else {
            elements.removeAll(one)
        }
    }

    fun filterLeastCommon(position: Int) {
        if (elements.size <= 1) {
            return
        }
        val one = mutableListOf<BinaryNumber>()
        val zero = mutableListOf<BinaryNumber>()
        for(element in elements) {
            val value = element.get(position).toInt()
            if (value == 0) {
                zero.add(element)
            } else {
                one.add(element)
            }
        }

        if (zero.size <= one.size) {
            elements.removeAll(one)
        } else {
            elements.removeAll(zero)
        }
    }

    override fun toString(): String {
        return "BinaryCollection(elements=$elements)"
    }
}
