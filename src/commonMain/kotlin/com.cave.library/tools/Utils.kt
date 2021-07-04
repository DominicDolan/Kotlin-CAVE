package com.cave.library.tools

import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec4.Vector4
import kotlin.math.sqrt

fun hypot(x: Double, y: Double, z: Double): Double {
    return sqrt(x*x + y*y + z*z)
}

fun hypot(v: Vector3) = hypot(v.x, v.y, v.z)

fun hypot(x: Double, y: Double, z: Double, w: Double): Double {
    return sqrt(x*x + y*y + z*z + w*w)
}

fun hypot(v: Vector4) = hypot(v.x, v.y, v.z, v.w)
