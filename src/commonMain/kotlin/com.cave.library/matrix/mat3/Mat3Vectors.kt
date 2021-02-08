package com.cave.library.matrix.mat3

import com.cave.library.matrix.ArrayToMatrix
import com.cave.library.matrix.formatted
import com.cave.library.vector.vec3.VariableVector3
import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec4.Vector4


open class GenericMatrixVector3(
    protected val array: DoubleArray,
    protected val xIndex: Int,
    protected val yIndex: Int,
    protected val zIndex: Int
) : Vector3 {

    override val x: Double
        get() = array[xIndex]
    override val y: Double
        get() = array[yIndex]
    override val z: Double
        get() = array[zIndex]


    val normalized: Vector3 by lazy {
        object : Vector3 {
            override val x: Double
                get() = this@GenericMatrixVector3.x/this@GenericMatrixVector3.r
            override val y: Double
                get() = this@GenericMatrixVector3.y/this@GenericMatrixVector3.r
            override val z: Double
                get() = this@GenericMatrixVector3.z/this@GenericMatrixVector3.r

            override fun toString(): String {
                return Vector3.toString(this)
            }
        }
    }

    override fun toString(): String {
        return "${x.formatted()}  ${y.formatted()}  ${z.formatted()}"
    }


}

open class ColumnVector3(column: Int, array: DoubleArray, arr2Mat: ArrayToMatrix)
    : GenericMatrixVector3(
    array,
    arr2Mat.coordsToIndex(column, 0),
    arr2Mat.coordsToIndex(column, 1),
    arr2Mat.coordsToIndex(column, 2)
)

open class RowVector3(row: Int, array: DoubleArray, arr2Mat: ArrayToMatrix)
    : GenericMatrixVector3(
    array,
    arr2Mat.coordsToIndex(0, row),
    arr2Mat.coordsToIndex(1, row),
    arr2Mat.coordsToIndex(2, row)
)


open class GenericMatrixVariableVector3(
    array: DoubleArray,
    xIndex: Int,
    yIndex: Int,
    zIndex: Int
) : GenericMatrixVector3(array, xIndex, yIndex, zIndex), VariableVector3 {

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

class ColumnVariableVector3(column: Int, array: DoubleArray, arr2Mat: ArrayToMatrix)
    : ColumnVector3(column, array, arr2Mat), VariableVector3 {

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

class RowVariableVector3(column: Int, array: DoubleArray, arr2Mat: ArrayToMatrix)
    : RowVector3(column, array, arr2Mat), VariableVector3 {

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


