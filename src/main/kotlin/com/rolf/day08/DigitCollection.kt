package com.rolf.day08

class DigitCollection(private val digits: List<Digit>) {

    init {
        // 1 can be found with length 2
        deduct(1, 2, null, digits)
        // 4 can be found with length 4
        deduct(4, 4, null, digits)
        // 7 can be found with length 3
        deduct(7, 3, null, digits)
        // 8 can be found with length 7
        deduct(8, 7, null, digits)
        // 9 can be found with length 6 and overlaps with the 4
        deduct(9, 6, 4, digits)
        // 3 can be found with length 5 and overlaps with the 7
        deduct(3, 5, 7, digits)
        // 0 can be found with length 6 and overlaps with the 7
        deduct(0, 6, 7, digits)
        // 5 can be found with length 5 and overlaps with the 9
        deduct(5, 5, 9, digits)
        // 6 can be found with length 6 and overlaps with the 5
        deduct(6, 6, 5, digits)
        // 2 can be found with length 5 and overlaps with the 8
        deduct(2, 5, 8, digits)
    }

    private fun deduct(number: Int, inputLength: Int, requiredDigit: Int?, otherDigits: List<Digit>) {

        // Find the required digit in the list
        var required: Digit? = null
        for (d in otherDigits) {
            if (requiredDigit != null && requiredDigit == d.number) {
                required = d
            }
        }

        // Check the requirements to determine the number
        for (option in otherDigits) {
            if (option.number == null) {
                if (option.input.length == inputLength) {
                    if (required == null || option.contains(required)) {
                        option.number = number
                    }
                }
            }
        }
    }

    fun getNumber(last: String): Long {
        var output = ""
        val inputDigits = last.split(" ")
        for (input in inputDigits) {
            val digitToSolve = Digit(input, null)
            for (digit in digits) {
                if (digit.containsAll(digitToSolve)) {
                    output += digit.number
                }
            }
        }
        return output.toLong()
    }
}
