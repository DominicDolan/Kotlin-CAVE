package com.cave.library.matrix.mat4

import com.cave.library.angle.Degree
import com.cave.library.angle.Radian
import com.cave.library.angle.VariableRotation
import com.cave.library.vector.vec4.VariableVector4
import com.cave.library.vector.vec4.Vector4

interface MatrixVector4: VariableVector4 {
    val defaultApply: Vector4

    fun apply(x: Double = defaultApply.x, y: Double = defaultApply.y, z: Double = defaultApply.z, w: Double = defaultApply.w): Matrix4

    fun apply(vector: Vector4) = apply(vector.x, vector.y, vector.z, vector.w)

}

interface MatrixRotationVector : VariableRotation {
    fun apply(angle: Radian, x: Double, y: Double, z: Double): Matrix4
    fun apply(angle: Degree, x: Double, y: Double, z: Double): Matrix4
    fun apply(angle: Radian): Matrix4
    fun apply(angle: Degree): Matrix4
}