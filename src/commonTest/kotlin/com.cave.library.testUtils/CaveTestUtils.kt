package com.cave.library.testUtils

fun areWithinError(value1: Double, value2: Double, error: Double): Boolean {
    return value1 > value2 - error && value1 < value2 + error
}

fun createIdentifyableMatrix4DoubleArray() = doubleArrayOf(
    0.0, 0.1, 0.2, 0.3,
    1.0, 1.1, 1.2, 1.3,
    2.0, 2.1, 2.2, 2.3,
    3.0, 3.1, 3.2, 3.3,
)

fun FloatArray.toDoubleArray(): DoubleArray {
    val doubleArray = DoubleArray(this.size)
    for (i in this.indices) {
        doubleArray[i] = this[i].toDouble()
    }

    return doubleArray
}

fun DoubleArray.contentEquals(other: DoubleArray, error: Double): Boolean {
    return findUnequal(other, error) == -1
}

fun DoubleArray.findUnequal(other: DoubleArray, error: Double): Int {
    for (i in this.indices) {
        if (!areWithinError(this[i], other[i], error)) {
            return i
        }
    }
    return -1
}
