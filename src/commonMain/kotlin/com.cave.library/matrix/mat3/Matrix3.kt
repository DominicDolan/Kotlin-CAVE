package com.cave.library.matrix.mat3

import com.cave.library.angle.AbstractRotation
import com.cave.library.angle.Radian
import com.cave.library.angle.VariableRotation
import com.cave.library.angle.radians
import com.cave.library.matrix.MatrixContext
import com.cave.library.tools.CachedDouble
import com.cave.library.tools.CachedRadian
import com.cave.library.vector.vec3.VariableVector3
import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec4.Vector4

interface Matrix3 : StaticMatrix3 {

    override val scale: VariableVector3
    override val rotation: VariableRotation

    fun identity(): Matrix3
    fun normal(): Matrix3
    fun invert(): Matrix3
    fun transpose(): Matrix3
    fun zero(): Matrix3

    operator fun set(column: Int, row: Int, value: Double)
    fun set(other: StaticMatrix3)

    operator fun timesAssign(other: StaticMatrix3)

    interface Column : StaticMatrix3.Column {
        override fun get(column: Int): ColumnVariableVector3
        operator fun set(column: Int, row: Int, value: Double)
        operator fun set(column: Int, value: Vector4)

        companion object {
            fun create(array: DoubleArray) = object : Column {
                private val columns = Array(columnCount) { ColumnVariableVector3(it, array, Matrix3) }

                override fun get(column: Int): ColumnVariableVector3 = columns[column]
                override fun get(column: Int, row: Int) = get(column)[row]

                override fun set(column: Int, row: Int, value: Double) {
                    get(column)[row] = value
                }

                override fun set(column: Int, value: Vector4) {
                    for (row in 0 until columnCount) {
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
                private val rows = Array(columnCount) { RowVariableVector3(it, array, Matrix3) }

                override fun get(row: Int): RowVariableVector3 = rows[row]
                override fun get(row: Int, column: Int) = get(column)[row]

                override fun set(row: Int, column: Int, value: Double) {
                    get(column)[row] = value
                }

                override fun set(row: Int, value: Vector4) {
                    for (column in 0 until columnCount) {
                        set(row, column, value[row])
                    }
                }

            }
        }
    }

    companion object : Matrix3Creator<Matrix3>() {
        override val columnCount: Int = 3
        override val rowCount: Int = 3

        override fun create(array: DoubleArray): Matrix3 {
            return Matrix3Impl(array)
        }
    }

}

internal class Matrix3Impl(private val array: DoubleArray, context: MatrixContext = Matrix3) : Matrix3, MatrixContext by context {
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

    override fun set(other: StaticMatrix3) {
        other.fill(array)
    }

    override fun timesAssign(other: StaticMatrix3) {
        array.multiplyIntoArray(this, other)
    }

    override fun get(column: Int, row: Int): Double {
        return array[column, row]
    }

    override val scale: VariableVector3 by lazy {
        val xIndex = coordsToIndex(0, 0)
        val yIndex = coordsToIndex(1, 1)
        val zIndex = coordsToIndex(2, 2)
        IndexedMatrixVariableVector3(array, xIndex, yIndex, zIndex)
    }

    override val rotation: VariableRotation = RotationVariableImpl(array, 0.0.radians, 0.0, 0.0, 1.0, this)

    override val column: Matrix3.Column = Matrix3.Column.create(array)
    override val row: Matrix3.Row = Matrix3.Row.create(array)

    override fun fill(array: DoubleArray) {
        this.array.copyInto(array)
    }

    override fun fill(array: FloatArray) {
        this.array.copyInto(array)
    }

    override fun toString(): String {
        return row[0].toString() + '\n' +
                row[1].toString() + '\n' +
                row[2].toString()
    }
}


internal class RotationVariableImpl(
    private val array: DoubleArray,
    private val defaultRotation: Radian,
    defaultX: Double, defaultY: Double, defaultZ: Double,
    context: MatrixContext,
) : AbstractRotation(array, context), VariableRotation {

    private val angleCache = CachedRadian.create(arrayOf({ array[0, 0] }, { array[1, 1] }, { array[2, 2] })) {
        super.angle
    }
    override var angle: Radian
        get() {
            val calculated = angleCache.get()
            return if (calculated.toDouble().isFinite()) {
                calculated
            } else {
                defaultRotation
            }
        }
        set(value) {
            array.rotate(value, axis.x, axis.y, axis.z)
        }

    private val superAxis: Vector3
        get() = super.axis

    override fun set(angle: Radian, x: Double, y: Double, z: Double) {
        array.rotate(angle, x, y, z)
    }

    override val axis: VariableVector3 = object : VariableVector3 {

        private val xCache = CachedDouble.create(arrayOf({ array[1, 2] }, { array[2, 1] }), defaultX) { superAxis.x }
        override var x: Double
            get() = xCache.get()
            set(value) {
                array.rotate(angle, value, y, z)
            }


        private val yCache = CachedDouble.create(arrayOf({ array[2, 0] }, { array[0, 2] }), defaultY) { superAxis.y }
        override var y: Double
            get() = yCache.get()
            set(value) {
                array.rotate(angle, x, value, z)
            }


        private val zCache = CachedDouble.create(arrayOf({ array[0, 1] }, { array[1, 0] }), defaultZ) { superAxis.z }
        override var z: Double
            get() = zCache.get()
            set(value) {
                array.rotate(angle, x, y, value)
            }


        override fun set(x: Double, y: Double, z: Double) {
            array.rotate(angle, x, y, z)
        }

        override fun set(x: Double, y: Double) {
            array.rotate(angle, x, y, z)
        }

        override fun toString() = Vector3.toString(this)
    }

}