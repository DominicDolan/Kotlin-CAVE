package com.cave.library.matrix.mat4

import com.cave.library.angle.Radian
import com.cave.library.matrix.MatrixContext
import com.cave.library.matrix.formatted
import com.cave.library.matrix.mat3.IndexedMatrixVector3
import com.cave.library.tools.CachedDouble
import com.cave.library.tools.CachedRadian
import com.cave.library.vector.vec4.VariableVector4
import com.cave.library.vector.vec4.Vector4


open class IndexedMatrixVector4(
    array: DoubleArray,
    xIndex: Int, yIndex: Int, zIndex: Int,
    private val wIndex: Int
) : IndexedMatrixVector3(array, xIndex, yIndex, zIndex), Vector4 {

    private val rCache = CachedDouble.create(arrayOf({ x }, { y }, { z })) { super<Vector4>.r }
    override val r: Double
        get() = rCache.get()

    private val thetaCache = CachedRadian.create(arrayOf({ x }, { y }, { z })) { super<Vector4>.theta }
    override val theta: Radian
        get() = thetaCache.get()

    override val w: Double
        get() = array[wIndex]

    override fun toString(): String {
        return "${x.formatted()}  ${y.formatted()}  ${z.formatted()}  ${w.formatted()}"
    }

}


class ColumnVector4(column: Int, array: DoubleArray, context: MatrixContext)
    : IndexedMatrixVector4(
    array,
    context.coordsToIndex(column, 0),
    context.coordsToIndex(column, 1),
    context.coordsToIndex(column, 2),
    context.coordsToIndex(column, 3)
)

class RowVector4(row: Int, array: DoubleArray, context: MatrixContext)
    : IndexedMatrixVector4(
    array,
    context.coordsToIndex(0, row),
    context.coordsToIndex(1, row),
    context.coordsToIndex(2, row),
    context.coordsToIndex(3, row)
)

open class IndexedMatrixVariableVector4(
    array: DoubleArray,
    xIndex: Int,
    yIndex: Int,
    zIndex: Int,
    private val wIndex: Int
) : IndexedMatrixVector4(array, xIndex, yIndex, zIndex, wIndex), VariableVector4 {

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


class ColumnVariableVector4(column: Int, array: DoubleArray, context: MatrixContext)
    : IndexedMatrixVariableVector4(
    array,
    context.coordsToIndex(column, 0),
    context.coordsToIndex(column, 1),
    context.coordsToIndex(column, 2),
    context.coordsToIndex(column, 3)
)

class RowVariableVector4(row: Int, array: DoubleArray, context: MatrixContext)
    : IndexedMatrixVariableVector4(
    array,
    context.coordsToIndex(0, row),
    context.coordsToIndex(1, row),
    context.coordsToIndex(2, row),
    context.coordsToIndex(3, row)
)