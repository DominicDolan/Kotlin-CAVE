package com.cave.library.matrix

import com.cave.library.angle.Degree
import com.cave.library.angle.Radian
import com.cave.library.matrix.mat3.StaticMatrix3
import com.cave.library.vector.vec3.dot
import kotlin.math.cos
import kotlin.math.sin


interface ArrayToMatrix {
    val size: Int

    val arraySize: Int
        get() = size*size

    fun coordsToIndex(column: Int, row: Int): Int {
        return row*size+ column
    }

    operator fun DoubleArray.get(column: Int, row: Int): Double {
        val index = coordsToIndex(column, row)
        return this[index]
    }

    operator fun DoubleArray.set(column: Int, row: Int, value: Double) {
        val index = coordsToIndex(column, row)
        if (index < this.size) {
            this[index] = value
        }
    }

    fun DoubleArray.zero() {
        this.fill(0.0)
    }

    fun DoubleArray.identity() {
        this.zero()
        for (i in 0 until size) {
            this[i, i] = 1.0
        }
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

    fun DoubleArray.rotate(angle: Radian, x: Double, y: Double, z: Double) {
        this.rotate(angle.toDouble(), x, y, z)
    }

    fun DoubleArray.rotate(angle: Degree, x: Double, y: Double, z: Double) {
        this.rotate(angle.toRadians().toDouble(), x, y, z)
    }

    private fun DoubleArray.rotate(radians: Double, x: Double, y: Double, z: Double) {
        val sin = sin(radians)
        val cos = cos(radians)
        println("sin: $sin, cos: $cos")
        val c = 1.0 - cos

        val xy = x * y * c
        val xz = x * z * c
        val yz = y * z * c

        this[0, 0] = (cos + x * x * c)
        this[1, 0] = (xy - z * sin)
        this[2, 0] = (xz + y * sin)
        this[0, 1] = (xy + z * sin)
        this[1, 1] = (cos + y * y * c)
        this[2, 1] = (yz - x * sin)
        this[0, 2] = (xz - y * sin)
        this[1, 2] = (yz + x * sin)
        this[2, 2] = (cos + z * z * c)
    }

    fun DoubleArray.multiplyIntoArray(matLeft: StaticMatrix3, matRight: StaticMatrix3) {
        val matrixSize = this@ArrayToMatrix.size
        for (row in 0 until matrixSize) {
            for (column in 0 until matrixSize) {
                this[column, row] = matLeft.row[row].dot(matRight.column[column])
            }
        }
    }

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
