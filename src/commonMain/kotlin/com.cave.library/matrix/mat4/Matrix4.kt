package com.cave.library.matrix.mat4

import com.cave.library.angle.Degree
import com.cave.library.angle.Radian
import com.cave.library.angle.VariableRotation
import com.cave.library.angle.radians
import com.cave.library.matrix.MatrixArrayTransforms
import com.cave.library.matrix.mat3.IndexedMatrixVariableVector3
import com.cave.library.matrix.mat3.RotationVariableImpl
import com.cave.library.vector.vec2.timesAssign
import com.cave.library.vector.vec3.VariableVector3
import com.cave.library.vector.vec4.VariableVector4

interface Matrix4 : StaticMatrix4 {

    override val translation: VariableVector3
    override val scale: VariableVector3
    override val rotation: VariableRotation

    override val column: StaticMatrix4.Column
    override val row: StaticMatrix4.Row


    fun identity(): Matrix4
    fun normal(): Matrix4
    fun invert(): Matrix4
    fun transpose(): Matrix4
    fun zero(): Matrix4

    fun perspective(fov: Degree, aspectRatio: Double, near: Double = 0.0, far: Double = Double.POSITIVE_INFINITY): Matrix4
    fun perspective(fov: Radian, aspectRatio: Double, near: Double = 0.0, far: Double = Double.POSITIVE_INFINITY): Matrix4

    operator fun set(row: Int, column: Int, value: Double)

    fun set(other: StaticMatrix4)

    operator fun timesAssign(other: StaticMatrix4)

    interface Column : StaticMatrix4.Column {
        override fun get(column: Int): VariableVector4
        operator fun set(row: Int, column: Int, value: Double)
        operator fun set(column: Int, value: VariableVector4)

        companion object {
            fun create(array: DoubleArray): Column {
                return VariableColumnImpl(array)
            }
        }
    }
    interface Row : StaticMatrix4.Row {
        override fun get(row: Int): VariableVector4
        operator fun set(row: Int, column: Int, value: Double)
        operator fun set(row: Int, value: VariableVector4)

        companion object {
            fun create(array: DoubleArray): Row {
                return VariableRowImpl(array)
            }
        }
    }

    companion object : Matrix4Creator<Matrix4>() {
        override val columnCount: Int = 4
        override val rowCount: Int = 4

        override fun create(array: DoubleArray): Matrix4 = Matrix4Impl(array)

    }
}

private class Matrix4Impl(private val array: DoubleArray) : Matrix4, MatrixArrayTransforms {
    override val columnCount: Int = 4
    override val rowCount: Int = 4

    override fun identity(): Matrix4 {
        array.identity()
        return this
    }

    override fun normal(): Matrix4 {
        TODO("Not yet implemented")
    }

    override fun invert(): Matrix4 {
        TODO("Not yet implemented")
    }

    override fun transpose(): Matrix4 {
        TODO("Not yet implemented")
    }

    override fun zero(): Matrix4 {
        array.zero()
        return this
    }

    override fun perspective(fov: Degree, aspectRatio: Double, near: Double, far: Double): Matrix4 {
        array.perspective(fov.toRadians(), aspectRatio, near, far)
        return this
    }

    override fun perspective(fov: Radian, aspectRatio: Double, near: Double, far: Double): Matrix4 {
        array.perspective(fov, aspectRatio, near, far)
        return this
    }

    override fun set(other: StaticMatrix4) {
        other.fill(array)
    }

    override fun set(row: Int, column: Int, value: Double) {
        array[row, column] = value
    }

    override fun timesAssign(other: StaticMatrix4) {
        array.multiplyIntoArray(this, other)
    }

    override fun get(row: Int, column: Int) = array[row, column]

    override val translation: VariableVector3 by lazy {
        val xIndex = coordsToIndex(3, 0)
        val yIndex = coordsToIndex(3, 1)
        val zIndex = coordsToIndex(3, 2)
        IndexedMatrixVariableVector3(array, xIndex, yIndex, zIndex)
    }

    override val scale: VariableVector3 by lazy { object : VariableVector3 {
        override var x: Double
            get() = array[0, 0]
            set(value) {
                row[0] *= value
            }
        override var y: Double
            get() = array[1, 1]
            set(value) {
                row[1] *= value
            }
        override var z: Double
            get() = array[2, 2]
            set(value) {
                row[2] *= value
            }
    } }

    override val rotation: VariableRotation = RotationVariableImpl(array, 0.0.radians, 0.0, 0.0, 1.0, this)
    override val column: Matrix4.Column = Matrix4.Column.create(array)
    override val row: Matrix4.Row = Matrix4.Row.create(array)

    override fun fill(array: DoubleArray) {
        this.array.copyInto(array)
    }

    override fun fill(array: FloatArray) {
        this.array.copyInto(array)
    }

    override fun toString(): String {
        return row[0].toString() + '\n' +
                row[1].toString() + '\n' +
                row[2].toString() + '\n' +
                row[3].toString()
    }

}

private class VariableColumnImpl(private val array: DoubleArray)
    : Matrix4.Column, MatrixArrayTransforms by Matrix4 {
    private val columns = Array<VariableVector4>(4) { ColumnVariableVector4(it, array, Matrix4) }

    override fun set(row: Int, column: Int, value: Double) {
        array[row, column] = value
    }

    override fun set(column: Int, value: VariableVector4) {
        columns[column].set(value)
    }

    override fun get(row: Int, column: Int) = array[row, column]

    override fun get(column: Int): VariableVector4 {
        return columns[column]
    }
}

private class VariableRowImpl(private val array: DoubleArray)
    : Matrix4.Row, MatrixArrayTransforms by Matrix4 {
    private val rows = Array<VariableVector4>(4) { RowVariableVector4(it, array, Matrix4) }

    override fun set(row: Int, column: Int, value: Double) {
        array[row, column] = value
    }

    override fun set(row: Int, value: VariableVector4) {
        rows[row].set(value)
    }

    override fun get(row: Int, column: Int) = array[row, column]

    override fun get(row: Int): VariableVector4 {
        return rows[row]
    }
}
