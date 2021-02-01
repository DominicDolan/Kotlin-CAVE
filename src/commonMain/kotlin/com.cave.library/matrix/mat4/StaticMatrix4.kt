package com.cave.library.matrix.mat4

import com.cave.library.matrix.*
import com.cave.library.vector.vec2.InlineVector
import com.cave.library.vector.vec2.Vector2
import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec4.Vector4


interface StaticMatrix4 {
    operator fun get(column: Int, row: Int): Double

    val translation: Vector3
    val scale: Vector3

    val column: StaticColumn4
    val row: StaticRow4

    fun fill(array: DoubleArray)

    companion object {
        fun from(array: DoubleArray): StaticMatrix4 {
            val arr = DoubleArray(16) { if (it < array.size) array[it] else 0.0 }
            return StaticMatrix4Impl(arr)
        }

        fun from(matrix4: StaticMatrix4): StaticMatrix4 {
            val array = DoubleArray(16)
            matrix4.fill(array)
            return StaticMatrix4Impl(array)
        }

        fun identity(): StaticMatrix4 {
            val array = DoubleArray(16) { 0.0 }
            array.identity()
            return StaticMatrix4Impl(array)
        }

        fun zero(): StaticMatrix4 {
            val array = DoubleArray(16) { 0.0 }

            return StaticMatrix4Impl(array)
        }

        fun translation(x: Double, y: Double, z: Double): StaticMatrix4 {
            val array = DoubleArray(16)
            array.identity()
            array.translate(x, y, z)
            return StaticMatrix4Impl(array)
        }

        fun translation(vector: Vector3): StaticMatrix4 {
            return translation(vector.x, vector.y, vector.z)
        }

        fun translation(vector: Vector2, z: Double = 0.0): StaticMatrix4 {
            return translation(vector.x, vector.y, z)
        }

        fun scaled(x: Double, y: Double, z: Double): StaticMatrix4 {
            val array = DoubleArray(16)
            array.identity()
            array.scale(x, y, z)
            return StaticMatrix4Impl(array)
        }

        fun scaled(vector: Vector3): StaticMatrix4 {
            return scaled(vector.x, vector.y, vector.z)
        }

        fun scaled(vector: Vector2, z: Double = 0.0): StaticMatrix4 {
            return scaled(vector.x, vector.y, z)
        }
    }
}

interface StaticColumn4 {
    operator fun get(column: Int, row: Int): Double
    operator fun get(column: Int): Vector4
}

interface StaticRow4 {
    operator fun get(row: Int, column: Int): Double
    operator fun get(row: Int): Vector4
}

private class StaticMatrix4Impl(private val array: DoubleArray) : StaticMatrix4{
    override fun get(column: Int, row: Int) = array[column, row]

    override val translation: Vector3 by lazy {
        val column = 3
        val xIndex = coordsToIndex(column, 0)
        val yIndex = coordsToIndex(column, 1)
        val zIndex = coordsToIndex(column, 2)
        GenericMatrixVector3(array, xIndex, yIndex, zIndex)
    }

    override val scale: Vector3 by lazy {
        val xIndex = coordsToIndex(0, 0)
        val yIndex = coordsToIndex(1, 1)
        val zIndex = coordsToIndex(2, 2)
        GenericMatrixVector3(array, xIndex, yIndex, zIndex)
    }


    override val column: StaticColumn4 = StaticColumn4Impl(array)
    override val row: StaticRow4 = StaticRow4Impl(array)

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

private class StaticColumn4Impl(private val array: DoubleArray) : StaticColumn4 {
    private val columns = Array<Vector4>(4) { ColumnVector4(it, array) }

    override fun get(column: Int, row: Int) = get(column)[row]
    override fun get(column: Int) = columns[column]

}

internal open class GenericMatrixVector3(
    private val array: DoubleArray,
    private val xIndex: Int,
    private val yIndex: Int,
    private val zIndex: Int
) : Vector3 {

    override val x: Double
        get() = array[xIndex]
    override val y: Double
        get() = array[yIndex]
    override val z: Double
        get() = array[zIndex]

    override fun toString(): String {
        return "${x.formatted()}  ${y.formatted()}  ${z.formatted()}"
    }

}

internal open class GenericMatrixVector4(
            private val array: DoubleArray,
            xIndex: Int, yIndex: Int, zIndex: Int,
            private val wIndex: Int
        ) : GenericMatrixVector3(array, xIndex, yIndex, zIndex), Vector4 {

    override val w: Double
        get() = array[wIndex]

    override fun toString(): String {
        return "${x.formatted()}  ${y.formatted()}  ${z.formatted()}  ${w.formatted()}"
    }

}

private class ColumnVector4(column: Int, array: DoubleArray)
    : GenericMatrixVector4(
        array,
        coordsToIndex(column, 0),
        coordsToIndex(column, 1),
        coordsToIndex(column, 2),
        coordsToIndex(column, 3)
    )

private class StaticRow4Impl(private val array: DoubleArray) : StaticRow4 {
    private val rows = Array<Vector4>(4) { RowVector4(it, array) }

    override fun get(row: Int, column: Int) = get(row)[column]
    override fun get(row: Int) = rows[row]

}

private class RowVector4(row: Int, array: DoubleArray)
    : GenericMatrixVector4(
    array,
    coordsToIndex(0, row),
    coordsToIndex(1, row),
    coordsToIndex(2, row),
    coordsToIndex(3, row)
)

