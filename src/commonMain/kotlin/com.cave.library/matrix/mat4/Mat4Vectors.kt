package com.cave.library.matrix.mat4

import com.cave.library.matrix.ArrayToMatrix
import com.cave.library.matrix.formatted
import com.cave.library.matrix.mat3.GenericMatrixVector3
import com.cave.library.vector.vec3.VariableVector3
import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec4.VariableVector4
import com.cave.library.vector.vec4.Vector4
import kotlin.math.sqrt


open class GenericMatrixVector4(
    array: DoubleArray,
    xIndex: Int, yIndex: Int, zIndex: Int,
    private val wIndex: Int
) : GenericMatrixVector3(array, xIndex, yIndex, zIndex), Vector4 {

    override val w: Double
        get() = array[wIndex]

    override val r: Double
        get() = sqrt(x*x + y*y + z*z)

    override fun toString(): String {
        return "${x.formatted()}  ${y.formatted()}  ${z.formatted()}  ${w.formatted()}"
    }

}


class ColumnVector4(column: Int, array: DoubleArray, arr2Mat: ArrayToMatrix)
    : GenericMatrixVector4(
    array,
    arr2Mat.coordsToIndex(column, 0),
    arr2Mat.coordsToIndex(column, 1),
    arr2Mat.coordsToIndex(column, 2),
    arr2Mat.coordsToIndex(column, 3)
)

class RowVector4(row: Int, array: DoubleArray, arr2Mat: ArrayToMatrix)
    : GenericMatrixVector4(
    array,
    arr2Mat.coordsToIndex(0, row),
    arr2Mat.coordsToIndex(1, row),
    arr2Mat.coordsToIndex(2, row),
    arr2Mat.coordsToIndex(3, row)
)

open class GenericMatrixVariableVector4(
    array: DoubleArray,
    xIndex: Int,
    yIndex: Int,
    zIndex: Int,
    private val wIndex: Int
) : GenericMatrixVector4(array, xIndex, yIndex, zIndex, wIndex), VariableVector4 {

    override var x: Double
        get() = super.x
        set(value) { array[xIndex] = value }
    override var y: Double
        get() = super.y
        set(value) { array[yIndex] = value }
    override var z: Double
        get() = super.z
        set(value) { array[zIndex] = value }
    override var w: Double
        get() = super.w
        set(value) { array[wIndex] = value }

}


class ColumnVariableVector4(column: Int, array: DoubleArray, arr2Mat: ArrayToMatrix)
    : GenericMatrixVariableVector4(
    array,
    arr2Mat.coordsToIndex(column, 0),
    arr2Mat.coordsToIndex(column, 1),
    arr2Mat.coordsToIndex(column, 2),
    arr2Mat.coordsToIndex(column, 3)
)

class RowVariableVector4(row: Int, array: DoubleArray, arr2Mat: ArrayToMatrix)
    : GenericMatrixVariableVector4(
    array,
    arr2Mat.coordsToIndex(0, row),
    arr2Mat.coordsToIndex(1, row),
    arr2Mat.coordsToIndex(2, row),
    arr2Mat.coordsToIndex(3, row)
)