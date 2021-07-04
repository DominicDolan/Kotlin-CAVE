package com.cave.library.matrix

import com.cave.library.angle.Degree
import com.cave.library.angle.Radian
import com.cave.library.angle.Rotation
import com.cave.library.matrix.mat3.Matrix3
import com.cave.library.matrix.mat4.Matrix4
import com.cave.library.vector.dot
import com.cave.library.vector.vec3.Vector3
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.tan

interface MatrixArrayTransforms {
    val columnCount: Int
    val rowCount: Int

    val arraySize: Int
        get() = columnCount*rowCount

    fun coordsToIndex(row: Int, column: Int): Int {
        return row*rowCount + column
    }

    operator fun DoubleArray.get(row: Int, column: Int): Double {
        val index = coordsToIndex(column, row)
        return this[index]
    }

    operator fun DoubleArray.set(row: Int, column: Int, value: Double) {
        val index = coordsToIndex(column, row)
        if (index < arraySize) {
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

    fun DoubleArray.setTranslation(x: Double, y: Double, z: Double) {
        val column = 3
        this[coordsToIndex(column, 0)] = x
        this[coordsToIndex(column, 1)] = y
        this[coordsToIndex(column, 2)] = z
    }

    fun DoubleArray.applyTranslation(matrix: Matrix4, x: Double, y: Double, z: Double) {
        applyMat4ColumnFunction(this, 3) { row ->
            matrix.row[row].dot(x, y, z, 1.0)
        }
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

    fun DoubleArray.rotate(angle: Degree, vector: Vector3) {
        this.rotate(angle.toRadians().toDouble(), vector.x, vector.y, vector.z)
    }

    fun DoubleArray.rotate(rotation: Rotation) {
        this.rotate(rotation.angle, rotation.axis.x, rotation.axis.y, rotation.axis.z)
    }

    private fun DoubleArray.rotate(radians: Double, x: Double, y: Double, z: Double) {
        val sin = sin(radians)
        val cos = cos(radians)

        val c = 1.0 - cos

        val xy = x * y * c
        val xz = x * z * c
        val yz = y * z * c

        this[0, 0] = (cos + x * x * c); this[0, 1] = (xy - z * sin);    this[0, 2] = (xz + y * sin)
        this[1, 0] = (xy + z * sin);    this[1, 1] = (cos + y * y * c); this[1, 2] = (yz - x * sin)
        this[2, 0] = (xz - y * sin);    this[2, 1] = (yz + x * sin);    this[2, 2] = (cos + z * z * c)

    }

    /**
     *  @param fov: The vertical field of view angle in Radians
     *  @param aspectRatio: The aspect ratio of the view port, width/height
     *  @param near: The distance to the near plane
     *  @param far: the distance to the far plane, accepts infinity as a value
     */
    fun DoubleArray.perspective(fov: Radian, aspectRatio: Double, near: Double, far: Double) {
        val errorCorrection = 1E-7
        val m11 = 1.0/ tan(fov.toDouble()/2.0)
        val isInfinite = far > 0.0 && far.isInfinite()

        val m22 = if (isInfinite) errorCorrection - 1.0 else -(far + near)/(far - near)
        val m23 = if (isInfinite) (errorCorrection - 2.0)*near else (-2*far*near)/(far - near)
        this.zero()

        this[0, 0] = m11/aspectRatio
        this[1, 1] = m11
        this[2, 2] = m22
        this[2, 3] = m23
        this[3, 2] = -1.0
    }

    fun DoubleArray.product(matLeft: Matrix3, matRight: Matrix3) {
        applyMat3Function(this) { row, column -> matLeft.row[row].dot(matRight.column[column]) }
    }

    fun DoubleArray.product(matLeft: Matrix4, matRight: Matrix4) {
        applyMat4Function(this) { row, column -> matLeft.row[row].dot(matRight.column[column]) }
    }

    fun DoubleArray.copyInto(destination: FloatArray) {
        for (i in 0 until min(arraySize, destination.size)) {
            destination[i] = this[i].toFloat()
        }
    }
}


inline fun MatrixArrayTransforms.applyMat4Function(array: DoubleArray, operation: (row: Int, column: Int) -> Double) {

    val m00 = operation(0, 0)
    val m01 = operation(0, 1)
    val m02 = operation(0, 2)
    val m03 = operation(0, 3)

    val m10 = operation(1, 0)
    val m11 = operation(1, 1)
    val m12 = operation(1, 2)
    val m13 = operation(1, 3)

    val m20 = operation(2, 0)
    val m21 = operation(2, 1)
    val m22 = operation(2, 2)
    val m23 = operation(2, 3)

    val m30 = operation(3, 0)
    val m31 = operation(3, 1)
    val m32 = operation(3, 2)
    val m33 = operation(3, 3)

    array[0, 0] = m00
    array[0, 1] = m01
    array[0, 2] = m02
    array[0, 3] = m03

    array[1, 0] = m10
    array[1, 1] = m11
    array[1, 2] = m12
    array[1, 3] = m13

    array[2, 0] = m20
    array[2, 1] = m21
    array[2, 2] = m22
    array[2, 3] = m23

    array[3, 0] = m30
    array[3, 1] = m31
    array[3, 2] = m32
    array[3, 3] = m33
}

inline fun MatrixArrayTransforms.applyMat4RowFunction(array: DoubleArray, row: Int, operation: (column: Int) -> Double) {
    val col0 = operation(0)
    val col1 = operation(1)
    val col2 = operation(2)
    val col3 = operation(3)

    array[row, 0] = col0
    array[row, 1] = col1
    array[row, 2] = col2
    array[row, 3] = col3
}

inline fun MatrixArrayTransforms.applyMat4ColumnFunction(array: DoubleArray, column: Int, operation: (row: Int) -> Double) {
    val row0 = operation(0)
    val row1 = operation(1)
    val row2 = operation(2)
    val row3 = operation(3)

    array[0, column] = row0
    array[1, column] = row1
    array[2, column] = row2
    array[3, column] = row3
}

inline fun MatrixArrayTransforms.applyMat3Function(array: DoubleArray, operation: (row: Int, column: Int) -> Double) {
    val m00 = operation(0, 0)
    val m01 = operation(0, 1)
    val m02 = operation(0, 2)

    val m10 = operation(1, 0)
    val m11 = operation(1, 1)
    val m12 = operation(1, 2)

    val m20 = operation(2, 0)
    val m21 = operation(2, 1)
    val m22 = operation(2, 2)

    array[0, 0] = m00
    array[0, 1] = m01
    array[0, 2] = m02

    array[1, 0] = m10
    array[1, 1] = m11
    array[1, 2] = m12

    array[2, 0] = m20
    array[2, 1] = m21
    array[2, 2] = m22
}