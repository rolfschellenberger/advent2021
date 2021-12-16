package com.rolf.day16

import java.math.BigInteger

class InputStream(private val binary: List<Char>) {
    private var pointer = 0

    fun getValue(): Long {
        val version = readVersion()
        val typeId = readTypeId()
        versionSum += version

        // Literal
        if (typeId == 4L) {
            return getLiteral()
        }
        // Operator
        else {
            val toRead = getToRead()
            val bitsToRead = toRead.first
            val segmentsToRead = toRead.second

            val values = mutableListOf<Long>()
            for (i in 0 until segmentsToRead) {
                values.add(getValue())
            }
            val currentPointer = pointer
            while (pointer - currentPointer < bitsToRead) {
                values.add(getValue())
            }

            // With all values present, do the operation and return the result
            return doOperator(typeId, values)
        }
    }

    private fun readVersion(): Long {
        val versionBinary = sequence(3)
        return toLong(toString(versionBinary))
    }

    private fun readTypeId(): Long {
        val typeIdBinary = sequence(3)
        return toLong(toString(typeIdBinary))
    }

    private fun getLiteral(): Long {
        val totalSequence = mutableListOf<Char>()
        // Keep reading 5 bits until we find a sequence that starts with a 0
        var go = true
        while (go) {
            val sequence = sequence(5)
            totalSequence.addAll(sequence.subList(1, sequence.size))
            go = sequence.first() == '1'
        }
        return toLong(toString(totalSequence))
    }

    private fun doOperator(operator: Long, values: List<Long>): Long {
        return when (operator) {
            0L -> values.sum()
            1L -> values.reduce(Long::times)
            2L -> values.minOrNull()!!
            3L -> values.maxOrNull()!!
            4L -> values.first()
            5L -> if (values[0] > values[1]) 1 else 0
            6L -> if (values[0] < values[1]) 1 else 0
            7L -> if (values[0] == values[1]) 1 else 0
            else -> 0
        }
    }

    private fun getToRead(): Pair<Int, Int> {
        val lengthBit = sequence(1)
        val bitsToRead = if (lengthBit.first() == '0') toLong(toString(sequence(15))).toInt() else 0
        val segmentsToRead = if (lengthBit.first() == '1') toLong(toString(sequence(11))).toInt() else 0
        return Pair(bitsToRead, segmentsToRead)
    }

    private fun sequence(size: Int): List<Char> {
        pointer += size
        return binary.subList(pointer - size, pointer)
    }

    private fun toString(input: List<Char>): String {
        return input.joinToString("")
    }

    private fun toLong(binaryString: String): Long {
        return BigInteger(binaryString, 2).toLong()
    }

    override fun toString(): String {
        return toString(binary)
    }
}
