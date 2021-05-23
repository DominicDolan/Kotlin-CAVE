package com.cave.library.matrix.mat3

import com.cave.library.angle.*
import com.cave.library.matrix.MatrixContext
import com.cave.library.vector.vec2.Vector2
import com.cave.library.vector.vec3.Vector3

interface StaticMatrix3 {
    operator fun get(row: Int, column: Int): Double

    val scale: Vector3
    val rotation: Rotation

    val column: Column
    val row: Row

    fun fill(array: DoubleArray)
    fun fill(array: FloatArray)

    interface Column {
        operator fun get(row: Int, column: Int): Double
        operator fun get(column: Int): ColumnVector3

        companion object {
            fun create(array: DoubleArray, context: MatrixContext = StaticMatrix3) = object : Column {
                private val columns = Array(columnCount) { ColumnVector3(it, array, context) }

                override fun get(row: Int, column: Int) = get(column)[row]
                override fun get(column: Int) = columns[column]
            }
        }
    }

    interface Row {
        operator fun get(row: Int, column: Int): Double
        operator fun get(row: Int): RowVector3

        companion object {
            fun create(array: DoubleArray, context: MatrixContext = StaticMatrix3) = object : Row {
                private val rows = Array(columnCount) { RowVector3(it, array, context) }

                override fun get(row: Int, column: Int) = get(row)[column]
                override fun get(row: Int) = rows[row]
            }
        }
    }

    companion object : MatrixContext {
        override val columnCount: Int = 3
        override val rowCount: Int = 3

        fun from(array: DoubleArray): StaticMatrix3 {
            val arr = DoubleArray(arraySize) { if (it < array.size) array[it] else 0.0 }
            return StaticMatrix3Impl(arr)
        }

        fun from(matrix4: StaticMatrix3): StaticMatrix3 {
            val array = DoubleArray(arraySize)
            matrix4.fill(array)
            return StaticMatrix3Impl(array)
        }

        fun identity(): StaticMatrix3 {
            val array = DoubleArray(arraySize) { 0.0 }
            array.identity()
            return StaticMatrix3Impl(array)
        }

        fun zero(): StaticMatrix3 {
            val array = DoubleArray(arraySize) { 0.0 }

            return StaticMatrix3Impl(array)
        }

        fun translation(x: Double, y: Double, z: Double): StaticMatrix3 {
            val array = DoubleArray(arraySize)
            array.identity()
            array.translate(x, y, z)
            return StaticMatrix3Impl(array)
        }

        fun translation(vector: Vector3): StaticMatrix3 {
            return translation(vector.x, vector.y, vector.z)
        }

        fun translation(vector: Vector2, z: Double = 0.0): StaticMatrix3 {
            return translation(vector.x, vector.y, z)
        }

        fun scaled(x: Double, y: Double, z: Double): StaticMatrix3 {
            val array = DoubleArray(arraySize)
            array.identity()
            array.scale(x, y, z)
            return StaticMatrix3Impl(array)
        }

        fun scaled(vector: Vector3): StaticMatrix3 {
            return scaled(vector.x, vector.y, vector.z)
        }

        fun scaled(vector: Vector2, z: Double = 0.0): StaticMatrix3 {
            return scaled(vector.x, vector.y, z)
        }

        fun rotated(angle: Angle, x: Double, y: Double, z: Double): StaticMatrix3 {
            val array = DoubleArray(arraySize)
            val rotation = Rotation.create(angle, x, y, z)

            array.identity()
            array.rotate(rotation)

            return StaticMatrix3Impl(array, defaultRotation = rotation)
        }

        fun multiplied(matrixLeft: StaticMatrix3, matrixRight: StaticMatrix3): StaticMatrix3 {
            val array = DoubleArray(arraySize)
            array.multiplyIntoArray(matrixLeft, matrixRight)
            return from(array)
        }
    }
}

internal class StaticMatrix3Impl(
    private val array: DoubleArray,
    context: MatrixContext = StaticMatrix3,
    defaultRotation: Rotation? = null
) : StaticMatrix3, MatrixContext by context{

    override fun get(row: Int, column: Int) = array[row, column]

    override val scale: Vector3 by lazy {
        val xIndex = coordsToIndex(0, 0)
        val yIndex = coordsToIndex(1, 1)
        val zIndex = coordsToIndex(2, 2)
        IndexedMatrixVector3(array, xIndex, yIndex, zIndex)
    }

    override val rotation: Rotation by lazy {
        if (defaultRotation == null) StaticRotationImpl(array, 0.radians, 0.0, 0.0, 1.0, this)
        else StaticRotationImpl(array, defaultRotation, this)
    }

    override val column = StaticMatrix3.Column.create(array, this)
    override val row = StaticMatrix3.Row.create(array, this)

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

internal class StaticRotationImpl(
    array: DoubleArray,
    defaultRotation: Radian,
    defaultX: Double, defaultY: Double, defaultZ: Double,
    context: MatrixContext = StaticMatrix3
    ) : AbstractRotation(array, context), Rotation {

    override val angle: Radian
    override val axis: Vector3

    constructor(array: DoubleArray, other: Rotation, context: MatrixContext = StaticMatrix3)
            : this(array, other.angle, other.axis.x, other.axis.y, other.axis.z, context)

    init {
        val rotation = super.angle
        this.angle = (if (rotation.toDouble().isFinite()) rotation else defaultRotation)

        val axis = super.axis
        val x = (if (axis.x.isFinite()) axis.x else defaultX)

        val y = (if (axis.y.isFinite()) axis.y else defaultY)

        val z = (if (axis.z.isFinite()) axis.z else defaultZ)

        this.axis = Vector3.create(x, y, z)
    }

}



