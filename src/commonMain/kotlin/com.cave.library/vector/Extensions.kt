package com.cave.library.vector

import com.cave.library.vector.vec2.MutableVector2
import com.cave.library.vector.vec2.Vector2
import com.cave.library.vector.vec2.minus
import com.cave.library.vector.vec2.vec
import com.cave.library.vector.vec3.MutableVector3
import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec4.MutableVector4
import com.cave.library.vector.vec4.Vector4

fun MutableVector3.add(x: Double, y: Double, z: Double) {
    this.x += x
    this.y += y
    this.z += z
}

fun MutableVector3.subtract(x: Double, y: Double, z: Double) {
    this.x -= x
    this.y -= y
    this.z -= z
}

operator fun MutableVector2.plusAssign(vector: Vector2) {
    this.x += vector.x
    this.y += vector.y
}

operator fun MutableVector3.plusAssign(vector: Vector3) = add(vector.x, vector.y, vector.z)

operator fun MutableVector4.plusAssign(vector: Vector4) {
    this.x += vector.x
    this.y += vector.y
    this.z += vector.z
    this.w += vector.w
}

operator fun MutableVector2.minusAssign(vector: Vector2) {
    this.x -= vector.x
    this.y -= vector.y
}

operator fun MutableVector3.minusAssign(vector: Vector3) {
    this.x -= vector.x
    this.y -= vector.y
    this.z -= vector.z
}

operator fun MutableVector4.minusAssign(vector: Vector4) {
    this.x -= vector.x
    this.y -= vector.y
    this.z -= vector.z
    this.w -= vector.w
}

operator fun MutableVector2.timesAssign(scale: Double) {
    this.scale(scale)
}

operator fun MutableVector2.divAssign(scale: Double) {
    this.scale(1.0/scale)
}

fun Vector2.distanceTo(other: Vector2): Double {
    return (vec(this) - vec(other)).r
}

fun Vector3.dot(x: Double, y: Double, z: Double) = this.x*x + this.y*y + this.z*z
fun Vector3.dot(other: Vector3) = dot(other.x, other.y, other.z)

fun Vector4.dot(x: Double, y: Double, z: Double, w: Double) = this.x*x + this.y*y + this.z*z + this.w*w
fun Vector4.dot(other: Vector4) = dot(other.x, other.y, other.z, other.w)

fun MutableVector3.crossAssign(x: Double, y: Double, z: Double) {
    val thisX = this.x
    val thisY = this.y
    val thisZ = this.z

    this.x = thisY*z - thisZ*y
    this.y = thisZ*x - thisX*z
    this.z = thisX*y - thisY*x
}

fun MutableVector3.crossAssign(other: Vector3) = crossAssign(other.x, other.y, other.z)

