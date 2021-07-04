package com.cave.library.matrix.mat4

import com.cave.library.matrix.MatrixArrayTransforms
import com.cave.library.vector.vec4.VariableVector4
import com.cave.library.vector.vec4.Vector4

interface MatrixVector4: VariableVector4 {
    val defaultApply: Vector4

    fun apply(x: Double = defaultApply.x, y: Double = defaultApply.y, z: Double = defaultApply.z, w: Double = defaultApply.w): Matrix4

    fun apply(vector: Vector4) = apply(vector.x, vector.y, vector.z, vector.w)

}

internal abstract class AbstractMatrixVector4(
    protected val matrix: DoubleArray,
    protected val xIndex: Int,
    protected val yIndex: Int,
    protected val zIndex: Int,
    protected val wIndex: Int) : MatrixVector4, MatrixArrayTransforms {

    override var x: Double
        get() = matrix[xIndex]
        set(value) {matrix[xIndex] = value}
    override var y: Double
        get() = matrix[yIndex]
        set(value) {matrix[yIndex] = value}
    override var z: Double
        get() = matrix[zIndex]
        set(value) {matrix[zIndex] = value}
    override var w: Double
        get() = matrix[wIndex]
        set(value) {matrix[wIndex] = value}
}