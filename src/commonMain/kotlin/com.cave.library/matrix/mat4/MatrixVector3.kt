package com.cave.library.matrix.mat4

import com.cave.library.matrix.MatrixArrayTransforms
import com.cave.library.vector.vec3.VariableVector3
import com.cave.library.vector.vec3.Vector3

interface MatrixVector3: VariableVector3 {
    val defaultApply: Vector3

    fun apply(x: Double = defaultApply.x, y: Double = defaultApply.y, z: Double = defaultApply.z): Matrix4

    fun apply(vector: Vector3) = apply(vector.x, vector.y, vector.z)

}

internal abstract class AbstractMatrixVector3(
    protected val matrix: DoubleArray,
    protected val xIndex: Int,
    protected val yIndex: Int,
    protected val zIndex: Int) : MatrixVector3, MatrixArrayTransforms {

    override var x: Double
        get() = matrix[xIndex]
        set(value) {matrix[xIndex] = value}
    override var y: Double
        get() = matrix[yIndex]
        set(value) {matrix[yIndex] = value}
    override var z: Double
        get() = matrix[zIndex]
        set(value) {matrix[zIndex] = value}
}