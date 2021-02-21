package com.cave.library.matrix.mat3

import com.cave.library.angle.*
import com.cave.library.angle.AbstractAxisOfRotation
import com.cave.library.matrix.*
import com.cave.library.vector.vec2.Vector2
import com.cave.library.vector.vec3.Vector3

interface StaticMatrix3 {
    operator fun get(column: Int, row: Int): Double

    val scale: Vector3
    val rotation: AxisOfRotation

    val column: Column
    val row: Row

    fun fill(array: DoubleArray)

    interface Column {
        operator fun get(column: Int, row: Int): Double
        operator fun get(column: Int): ColumnVector3

        companion object {
            fun create(array: DoubleArray, arr2Mat: ArrayToMatrix = StaticMatrix3) = object : Column {
                private val columns = Array(size) { ColumnVector3(it, array, arr2Mat) }

                override fun get(column: Int, row: Int) = get(column)[row]
                override fun get(column: Int) = columns[column]
            }
        }
    }

    interface Row {
        operator fun get(row: Int, column: Int): Double
        operator fun get(row: Int): RowVector3

        companion object {
            fun create(array: DoubleArray, arr2Mat: ArrayToMatrix = StaticMatrix3) = object : Row {
                private val rows = Array(size) { RowVector3(it, array, arr2Mat) }

                override fun get(row: Int, column: Int) = get(row)[column]
                override fun get(row: Int) = rows[row]
            }
        }
    }

    companion object : ArrayToMatrix {
        override val size: Int = 3

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

        fun rotated(rotation: Angle, x: Double, y: Double, z: Double): StaticMatrix3 {
            val array = DoubleArray(arraySize)
            val rotationAxis = AxisOfRotation.create(rotation, x, y, z)

            array.identity()
            array.rotate(rotationAxis)

            return StaticMatrix3Impl(array, defaultRotation = rotationAxis)
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
    arr2Mat: ArrayToMatrix = StaticMatrix3,
    defaultRotation: AxisOfRotation? = null
) : StaticMatrix3, ArrayToMatrix by arr2Mat{

    override fun get(column: Int, row: Int) = array[column, row]

    override val scale: Vector3 by lazy {
        val xIndex = coordsToIndex(0, 0)
        val yIndex = coordsToIndex(1, 1)
        val zIndex = coordsToIndex(2, 2)
        GenericMatrixVector3(array, xIndex, yIndex, zIndex)
    }

    override val rotation: AxisOfRotation by lazy {
        if (defaultRotation == null) StaticAxisOfRotationImpl(this, 0.radians, 0.0, 0.0, 1.0)
        else StaticAxisOfRotationImpl(this, defaultRotation)
    }

    override val column = StaticMatrix3.Column.create(array, this)
    override val row = StaticMatrix3.Row.create(array, this)

    override fun fill(array: DoubleArray) {
        this.array.copyInto(array)
    }

    override fun toString(): String {
        return row[0].toString() + '\n' +
                row[1].toString() + '\n' +
                row[2].toString()
    }
}

internal class StaticAxisOfRotationImpl(
    override val matrix: StaticMatrix3,
    defaultRotation: Radian,
    defaultX: Double, defaultY: Double, defaultZ: Double,
    ) : AbstractAxisOfRotation(), AxisOfRotation {

    override val rotation: Radian
    override val x: Double
    override val y: Double
    override val z: Double

    constructor(matrix: StaticMatrix3, other: AxisOfRotation): this(matrix, other.rotation, other.x, other.y, other.z)

    init {
        val rotation = super.rotation
        this.rotation = (if (rotation.toDouble().isValid()) rotation else defaultRotation)

        val x = super.x
        this.x = (if (x.isValid()) x else defaultX)

        val y = super.y
        this.y = (if (y.isValid()) y else defaultY)

        println("calculating z")
        val z = super.z
        this.z = (if (z.isValid()) z else defaultZ)
    }

    override fun toString() = Vector3.toString(this)

    private fun Double.isValid() = !isNaN() && !isInfinite()
}



