package com.cave.library.matrix.mat4

import com.cave.library.angle.Angle
import com.cave.library.angle.VariableRotation
import com.cave.library.angle.radians
import com.cave.library.vector.vec2.*
import com.cave.library.vector.vec3.MutableVector3
import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec4.MutableVector4
import com.cave.library.vector.vec4.Vector4


interface MatrixVector2: MutableVector2 {
    val defaultApply: Vector2

    fun apply(x: Double = defaultApply.x, y: Double = defaultApply.y): Matrix4

    fun apply(vector: Vector2) = apply(vector.x, vector.y)

    fun apply(vector: InlineVector) = apply(vector.x, vector.y)

}

interface MatrixVector3: MutableVector3 {
    val defaultApply: Vector3

    fun apply(x: Double = defaultApply.x, y: Double = defaultApply.y, z: Double = defaultApply.z): Matrix4

    fun apply(vector: Vector3) = apply(vector.x, vector.y, vector.z)

}

interface MatrixVector4: MutableVector4 {
    val defaultApply: Vector4

    fun apply(x: Double = defaultApply.x, y: Double = defaultApply.y, z: Double = defaultApply.z, w: Double = defaultApply.w): Matrix4

    fun apply(vector: Vector4) = apply(vector.x, vector.y, vector.z, vector.w)
}

interface MatrixRotationVector : VariableRotation {
    fun apply(angle: Angle, x: Double, y: Double, z: Double): Matrix4
    fun apply(angle: Angle): Matrix4
}


interface MatrixAngleVector2: VariableAngleVector2 {
    fun apply(x: Angle = 0.0.radians, y: Angle = 0.0.radians): Matrix4

    fun apply(vector: AngleVector2) = apply(vector.x, vector.y)

    fun apply(vector: InlineVector) = apply(vector.x.radians, vector.y.radians)

}