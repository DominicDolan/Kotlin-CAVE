package com.cave.library.matrix.joml

import com.cave.library.testUtils.toDoubleArray
import org.joml.Matrix4f

fun createMatrix4f(array: FloatArray): Matrix4f {
    return Matrix4f(
        array[0],  array[1],  array[2],  array[3],
        array[4],  array[5],  array[6],  array[7],
        array[8],  array[9],  array[10], array[11],
        array[12], array[13], array[14], array[15],
    )
}

fun createMatrix4f(array: DoubleArray): Matrix4f {
    return Matrix4f(
        array[0].toFloat(),  array[1].toFloat(),  array[2].toFloat(),  array[3].toFloat(),
        array[4].toFloat(),  array[5].toFloat(),  array[6].toFloat(),  array[7].toFloat(),
        array[8].toFloat(),  array[9].toFloat(),  array[10].toFloat(), array[11].toFloat(),
        array[12].toFloat(), array[13].toFloat(), array[14].toFloat(), array[15].toFloat(),
    )
}

fun Matrix4f.toDoubleArray(): DoubleArray {
    val floatArray = FloatArray(16)
    this.get(floatArray)
    return floatArray.toDoubleArray()
}
