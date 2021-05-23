package com.cave.library.matrix.mat4

import com.cave.library.angle.Degree
import com.cave.library.angle.Radian
import com.cave.library.vector.vec2.InlineVector
import com.cave.library.vector.vec2.Vector2
import com.cave.library.vector.vec3.Vector3

fun Matrix4.applyScale(x: Double, y: Double, z: Double): Matrix4 {
    this.scale.set(x, y, z)
    return this
}

fun Matrix4.applyScale(vector: Vector3): Matrix4 {
    return applyScale(vector.x, vector.y, vector.z)
}

fun Matrix4.applyScale(vector: Vector2, z: Double = 0.0): Matrix4 {
    return applyScale(vector.x, vector.y, z)
}

fun Matrix4.applyScale(vector: InlineVector, z: Double = 0.0): Matrix4 {
    return applyScale(vector.x, vector.y, z)
}


fun Matrix4.applyTranslation(x: Double, y: Double, z: Double): Matrix4 {
    this.translation.set(x, y, z)
    return this
}

fun Matrix4.applyTranslation(vector: Vector3): Matrix4 {
    return applyTranslation(vector.x, vector.y, vector.z)
}

fun Matrix4.applyTranslation(vector: Vector2, z: Double = 0.0): Matrix4 {
    return applyTranslation(vector.x, vector.y, z)
}

fun Matrix4.applyTranslation(vector: InlineVector, z: Double = 0.0): Matrix4 {
    return applyTranslation(vector.x, vector.y, z)
}

fun Matrix4.applyRotation(angle: Radian, x: Double, y: Double, z: Double): Matrix4 {
    this.rotation.set(angle, x, y, z)
    return this
}

fun Matrix4.applyRotation(angle: Radian, axis: Vector3): Matrix4 {
    return this.applyRotation(angle, axis.x, axis.y, axis.z)
}

fun Matrix4.applyRotation(angle: Degree, x: Double, y: Double, z: Double): Matrix4 {
    this.rotation.set(angle, x, y, z)
    return this
}

fun Matrix4.applyRotation(angle: Degree, axis: Vector3): Matrix4 {
    return this.applyRotation(angle, axis.x, axis.y, axis.z)
}

fun Matrix4.applyRotation(angle: Degree): Matrix4 {
    this.rotation.angle = angle.toRadians()
    return this
}

fun Matrix4.applyRotation(angle: Radian): Matrix4 {
    this.rotation.angle = angle
    return this
}
    