package com.rolf

import java.io.File

const val BASE_PATH = "src/main/resources"

fun readLines(name: String) = File(BASE_PATH, name).readLines()

fun readLongs(name: String) = readLines(name).map { it.toLong() }
