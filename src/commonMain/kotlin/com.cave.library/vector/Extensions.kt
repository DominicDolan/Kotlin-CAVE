package com.cave.library.vector

import com.cave.library.vector.vec2.VariableVector2
import com.cave.library.vector.vec2.Vector2
import com.cave.library.vector.vec2.minus
import com.cave.library.vector.vec2.vec
import com.cave.library.vector.vec3.VariableVector3
import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec4.VariableVector4
import com.cave.library.vector.vec4.Vector4


operator fun VariableVector2.plusAssign(vector: Vector2) {
    this.x += vector.x
    this.y += vector.y
}

operator fun VariableVector3.plusAssign(vector: Vector3) {
    this.x += vector.x
    this.y += vector.y
    this.z += vector.z
}

operator fun VariableVector4.plusAssign(vector: Vector4) {
    this.x += vector.x
    this.y += vector.y
    this.z += vector.z
    this.w += vector.w
}

operator fun VariableVector2.minusAssign(vector: Vector2) {
    this.x -= vector.x
    this.y -= vector.y
}

operator fun VariableVector3.minusAssign(vector: Vector3) {
    this.x -= vector.x
    this.y -= vector.y
    this.z -= vector.z
}

operator fun VariableVector4.minusAssign(vector: Vector4) {
    this.x -= vector.x
    this.y -= vector.y
    this.z -= vector.z
    this.w -= vector.w
}

operator fun VariableVector2.timesAssign(scale: Double) {
    this.scale(scale)
}

operator fun VariableVector2.divAssign(scale: Double) {
    this.scale(1.0/scale)
}

fun Vector2.distanceTo(other: Vector2): Double {
    return (vec(this) - vec(other)).r
}

fun Vector3.dot(x: Double, y: Double, z: Double) = this.x*x + this.y*y + this.z*z
fun Vector3.dot(other: Vector3) = dot(other.x, other.y, other.z)

fun Vector4.dot(x: Double, y: Double, z: Double, w: Double) = this.x*x + this.y*y + this.z*z + this.w*w
fun Vector4.dot(other: Vector4) = dot(other.x, other.y, other.z, other.w)
