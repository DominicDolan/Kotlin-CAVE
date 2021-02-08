package com.cave.library.matrix.mat4

import com.cave.library.angle.AxisOfRotation
import com.cave.library.matrix.*
import com.cave.library.matrix.mat3.GenericMatrixVector3
import com.cave.library.matrix.mat3.StaticMatrix3Impl
import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec4.Vector4


interface StaticMatrix4 {
    operator fun get(column: Int, row: Int): Double

    val translation: Vector3
    val scale: Vector3
    val rotation: AxisOfRotation

    val column: Column
    val row: Row

    fun fill(array: DoubleArray)

    interface Column {
        operator fun get(column: Int, row: Int): Double
        operator fun get(column: Int): Vector4

        companion object {
            fun create(array: DoubleArray) = object : Column {
                private val columns = Array<Vector4>(4) { ColumnVector4(it, array, StaticMatrix4) }

                override fun get(column: Int, row: Int) = get(column)[row]
                override fun get(column: Int) = columns[column]
            }
        }
    }

    interface Row {
        operator fun get(row: Int, column: Int): Double
        operator fun get(row: Int): Vector4

        companion object {
            fun create(array: DoubleArray) = object : Row {
                private val rows = Array<Vector4>(4) { RowVector4(it, array, StaticMatrix4) }

                override fun get(row: Int, column: Int) = get(row)[column]
                override fun get(row: Int) = rows[row]
            }
        }
    }

    companion object : Matrix4Creator<StaticMatrix4>() {
        override val size: Int = 4

        override fun create(array: DoubleArray): StaticMatrix4 = StaticMatrix4Impl(array)

    }
}

private class StaticMatrix4Impl(private val array: DoubleArray) : StaticMatrix4, ArrayToMatrix {
    override val size: Int = 4

    private val mat3 by lazy { StaticMatrix3Impl(array, this) }

    override fun get(column: Int, row: Int) = array[column, row]

    override val translation: Vector3 by lazy {
        val xIndex = coordsToIndex(2, 0)
        val yIndex = coordsToIndex(2, 1)
        val zIndex = coordsToIndex(2, 2)
        GenericMatrixVector3(array, xIndex, yIndex, zIndex)
    }

    override val scale: Vector3
        get() = mat3.scale

    override val rotation: AxisOfRotation
        get() = mat3.rotation

    override val column = StaticMatrix4.Column.create(array)
    override val row = StaticMatrix4.Row.create(array)

    override fun fill(array: DoubleArray) {
        this.array.copyInto(array)
    }

    override fun toString(): String {
        return row[0].toString() + '\n' +
            row[1].toString() + '\n' +
            row[2].toString() + '\n' +
            row[3].toString() + '\n'
    }
}

