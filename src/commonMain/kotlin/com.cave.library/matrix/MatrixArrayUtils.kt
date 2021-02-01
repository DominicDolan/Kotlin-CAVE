package com.cave.library.matrix


operator fun DoubleArray.get(column: Int, row: Int): Double {
    val index = coordsToIndex(column, row)
    return this[index]
}

operator fun DoubleArray.set(column: Int, row: Int, value: Double) {
    val index = coordsToIndex(column, row)
    this[index] = value
}

fun DoubleArray.identity() {
    this.zero()
    this[0, 0] = 1.0
    this[1, 1] = 1.0
    this[2, 2] = 1.0
    this[3, 3] = 1.0
}

fun DoubleArray.zero() {
    this.fill(0.0)
}

fun DoubleArray.translate(x: Double, y: Double, z: Double) {
    val column = 3
    this[coordsToIndex(column, 0)] += x
    this[coordsToIndex(column, 1)] += y
    this[coordsToIndex(column, 2)] += z
}

fun DoubleArray.scale(x: Double, y: Double, z: Double) {
    this[coordsToIndex(0, 0)] *= x
    this[coordsToIndex(1, 1)] *= y
    this[coordsToIndex(2, 2)] *= z
}

fun coordsToIndex(column: Int, row: Int): Int {
    return column*4 + row
}


fun Double.formatted(characterLength: Int = 5): String {
    val str = this.toString()
    return when {
        str.length < characterLength -> {
            getChars(' ', characterLength - str.length) + str
        }
        str.indexOf('.') < characterLength -> {
            str.substring(0, characterLength)
        }
        else -> str.substring(0, str.indexOf('.'))
    }
}
private const val emptyString = ""
private fun getChars(c: Char, count: Int): String {
    return (CharArray(count) { c }).joinToString(emptyString)
}
