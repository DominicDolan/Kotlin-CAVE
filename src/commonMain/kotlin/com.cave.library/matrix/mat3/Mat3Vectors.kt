package com.cave.library.matrix.mat3

import com.cave.library.angle.Radian
import com.cave.library.matrix.MatrixArrayTransforms
import com.cave.library.matrix.formatted
import com.cave.library.tools.CachedDouble
import com.cave.library.tools.CachedRadian
import com.cave.library.vector.vec3.VariableVector3
import com.cave.library.vector.vec3.Vector3

abstract class MatrixVector3(
    protected val array: DoubleArray,
    protected val xGetter: MatrixArrayTransforms.(DoubleArray) -> Double,
    protected val yGetter: MatrixArrayTransforms.(DoubleArray) -> Double,
    protected val zGetter: MatrixArrayTransforms.(DoubleArray) -> Double,
    private val transforms: MatrixArrayTransforms = StaticMatrix3
) : Vector3 {

    private val rCache = CachedDouble.create(arrayOf({ x }, { y }, { z })) { super.r }
    override val r: Double
        get() {
            return rCache.get()
        }

    private val thetaCache = CachedRadian.create(arrayOf({ x }, { y }, { z })) { super.theta }
    override val theta: Radian
        get() = thetaCache.get()

    override val x: Double
        get() = xGetter(transforms, array)
    override val y: Double
        get() = yGetter(transforms, array)
    override val z: Double
        get() = zGetter(transforms, array)


    val normalized: Vector3 by lazy {
        object : Vector3 {
            override val x: Double
                get() = this@MatrixVector3.x/this@MatrixVector3.r
            override val y: Double
                get() = this@MatrixVector3.y/this@MatrixVector3.r
            override val z: Double
                get() = this@MatrixVector3.z/this@MatrixVector3.r

            override fun toString(): String {
                return Vector3.toString(this)
            }
        }
    }

    override fun toString(): String {
        return "${x.formatted()}  ${y.formatted()}  ${z.formatted()}"
    }

}
open class IndexedMatrixVector3(
    array: DoubleArray,
    protected val xIndex: Int,
    protected val yIndex: Int,
    protected val zIndex: Int
) : MatrixVector3(array, { it[xIndex] }, { it[yIndex] }, { it[zIndex] })

open class ColumnVector3(column: Int, array: DoubleArray, transforms: MatrixArrayTransforms)
    : IndexedMatrixVector3(
    array,
    transforms.coordsToIndex(column, 0),
    transforms.coordsToIndex(column, 1),
    transforms.coordsToIndex(column, 2)
)

open class RowVector3(row: Int, array: DoubleArray, transforms: MatrixArrayTransforms)
    : IndexedMatrixVector3(
    array,
    transforms.coordsToIndex(0, row),
    transforms.coordsToIndex(1, row),
    transforms.coordsToIndex(2, row)
)


open class IndexedMatrixVariableVector3(
    array: DoubleArray,
    xIndex: Int,
    yIndex: Int,
    zIndex: Int
) : IndexedMatrixVector3(array, xIndex, yIndex, zIndex), VariableVector3 {

    override var x: Double
        get() = super.x
        set(value) { array[xIndex] = value }
    override var y: Double
        get() = super.y
        set(value) { array[yIndex] = value }
    override var z: Double
        get() = super.z
        set(value) { array[zIndex] = value }

}

class ColumnVariableVector3(column: Int, array: DoubleArray, transforms: MatrixArrayTransforms)
    : ColumnVector3(column, array, transforms), VariableVector3 {

    override var x: Double
        get() = super.x
        set(value) { array[xIndex] = value }
    override var y: Double
        get() = super.y
        set(value) { array[yIndex] = value }
    override var z: Double
        get() = super.z
        set(value) { array[zIndex] = value }
}

class RowVariableVector3(column: Int, array: DoubleArray, transforms: MatrixArrayTransforms)
    : RowVector3(column, array, transforms), VariableVector3 {

    override var x: Double
        get() = super.x
        set(value) { array[xIndex] = value }
    override var y: Double
        get() = super.y
        set(value) { array[yIndex] = value }
    override var z: Double
        get() = super.z
        set(value) { array[zIndex] = value }
}


