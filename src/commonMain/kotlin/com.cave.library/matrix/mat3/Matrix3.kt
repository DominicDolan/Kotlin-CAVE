package com.cave.library.matrix.mat3

import com.cave.library.angle.Radian
import com.cave.library.angle.AxisOfRotation
import com.cave.library.angle.VariableAxisOfRotation
import com.cave.library.matrix.ArrayToMatrix
import com.cave.library.vector.vec2.InlineVector
import com.cave.library.vector.vec2.Vector2
import com.cave.library.vector.vec3.VariableVector3
import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec4.Vector4

interface Matrix3 : StaticMatrix3 {

    override val scale: VariableVector3
    override val rotation: VariableAxisOfRotation

    fun identity(): Matrix3
    fun normal(): Matrix3
    fun invert(): Matrix3
    fun transpose(): Matrix3
    fun zero(): Matrix3

    operator fun set(column: Int, row: Int, value: Double)

    interface Column : StaticMatrix3.Column {
        override fun get(column: Int): ColumnVariableVector3
        operator fun set(column: Int, row: Int, value: Double)
        operator fun set(column: Int, value: Vector4)

        companion object {
            fun create(array: DoubleArray) = object : Column {
                private val columns = Array(size) { ColumnVariableVector3(it, array, Matrix3) }

                override fun get(column: Int): ColumnVariableVector3 = columns[column]
                override fun get(column: Int, row: Int) = get(column)[row]

                override fun set(column: Int, row: Int, value: Double) {
                    get(column)[row] = value
                }

                override fun set(column: Int, value: Vector4) {
                    for (row in 0 until size) {
                        set(column, row, value[row])
                    }
                }

            }
        }
    }

    interface Row : StaticMatrix3.Row {
        override fun get(row: Int): RowVariableVector3
        operator fun set(row: Int, column: Int, value: Double)
        operator fun set(row: Int, value: Vector4)

        companion object {
            fun create(array: DoubleArray) = object : Row {
                private val rows = Array(size) { RowVariableVector3(it, array, Matrix3) }

                override fun get(row: Int): RowVariableVector3 = rows[row]
                override fun get(row: Int, column: Int) = get(column)[row]

                override fun set(row: Int, column: Int, value: Double) {
                    get(column)[row] = value
                }

                override fun set(row: Int, value: Vector4) {
                    for (column in 0 until size) {
                        set(row, column, value[row])
                    }
                }

            }
        }
    }

    companion object : Matrix3Creator<Matrix3>() {
        override val size: Int = 3

        override fun create(array: DoubleArray): Matrix3 {
            return Matrix3Impl(array)
        }
    }

}

internal class Matrix3Impl(private val array: DoubleArray, arrToMat: ArrayToMatrix = Matrix3) : Matrix3, ArrayToMatrix by arrToMat {
    override fun identity(): Matrix3 {
        array.identity()
        return this
    }

    override fun normal(): Matrix3 {
        TODO("Not yet implemented")
    }

    override fun invert(): Matrix3 {
        TODO("Not yet implemented")
    }

    override fun transpose(): Matrix3 {
        TODO("Not yet implemented")
    }

    override fun zero(): Matrix3 {
        array.zero()
        return this
    }

    override fun set(column: Int, row: Int, value: Double) {
        array[column, row] = value
    }

    override fun get(column: Int, row: Int): Double {
        return array[column, row]
    }

    override val scale: VariableVector3 by lazy {
        val xIndex = coordsToIndex(0, 0)
        val yIndex = coordsToIndex(1, 1)
        val zIndex = coordsToIndex(2, 2)
        GenericMatrixVariableVector3(array, xIndex, yIndex, zIndex)
    }

    override val rotation: VariableAxisOfRotation = AxisOfRotationVariableImpl(this, array, Matrix3)

    override val column: Matrix3.Column = Matrix3.Column.create(array)
    override val row: Matrix3.Row = Matrix3.Row.create(array)

    override fun fill(array: DoubleArray) {
        this.array.copyInto(array)
    }

    override fun toString(): String {
        return row[0].toString() + '\n' +
                row[1].toString() + '\n' +
                row[2].toString()
    }
}


private class AxisOfRotationVariableImpl(
    matrix: Matrix3,
    private val array: DoubleArray,
    arr2Mat: ArrayToMatrix,
) : VariableAxisOfRotation, ArrayToMatrix by arr2Mat {

    private val static = AxisOfRotationImpl(matrix)

    override var angle: Radian
        get() = static.angle
        set(value) {
            array.rotate(value, x, y, z)
        }
    override var x: Double
        get() = static.x
        set(value) {
            array.rotate(angle, value, y, z)
        }
    override var y: Double
        get() = static.y
        set(value) {
            array.rotate(angle, x, value, z)
        }
    override var z: Double
        get() = static.z
        set(value) {
            array.rotate(angle, x, y, value)
        }

    override fun set(x: Double, y: Double, z: Double) {
        array.rotate(angle, x, y, z)
    }

    override fun set(x: Double, y: Double) {
        array.rotate(angle, x, y, z)
    }

    override fun set(angle: Radian, x: Double, y: Double, z: Double) {
        array.rotate(angle, x, y, z)
    }

    override fun toString() = Vector3.toString(this)

}