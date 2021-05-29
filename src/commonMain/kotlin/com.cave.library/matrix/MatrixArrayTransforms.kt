package com.cave.library.matrix

import com.cave.library.angle.Degree
import com.cave.library.angle.Radian
import com.cave.library.angle.Rotation
import com.cave.library.matrix.mat3.StaticMatrix3
import com.cave.library.matrix.mat4.StaticMatrix4
import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec3.dot
import com.cave.library.vector.vec4.dot
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

    fun DoubleArray.multiplyIntoArray(matLeft: StaticMatrix3, matRight: StaticMatrix3, row: Int = 0, column: Int = 0) {
        val result = matLeft.row[row].dot(matRight.column[column])
        if (row < this@MatrixArrayTransforms.rowCount - 1) {
            multiplyIntoArray(matLeft, matRight, row + 1, column)
        } else if (column < this@MatrixArrayTransforms.columnCount - 1) {
            multiplyIntoArray(matLeft, matRight, 0, column+1)
        }
        this[row, column] = result
    }

    fun DoubleArray.multiplyIntoArray(matLeft: StaticMatrix4, matRight: StaticMatrix4, row: Int = 0, column: Int = 0) {
        val result = matLeft.row[row].dot(matRight.column[column])
        if (row < this@MatrixArrayTransforms.rowCount - 1) {
            multiplyIntoArray(matLeft, matRight, row + 1, column)
        } else if (column < this@MatrixArrayTransforms.columnCount - 1) {
            multiplyIntoArray(matLeft, matRight, 0, column+1)
        }
        this[row, column] = result
    }

    fun DoubleArray.copyInto(destination: FloatArray) {
        for (i in 0 until min(arraySize, destination.size)) {
            destination[i] = this[i].toFloat()
        }
    }

}