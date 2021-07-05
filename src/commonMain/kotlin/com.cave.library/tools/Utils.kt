package com.cave.library.tools

import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec4.Vector4
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.sqrt

fun squared(x: Double, y: Double, z: Double): Double {
    return x*x + y*y + z*z
}

fun squared(x: Double, y: Double, z: Double, w: Double): Double {
    return x*x + y*y + z*z + w*w
}

fun hypot(x: Double, y: Double, z: Double): Double {
    return sqrt(squared(x, y, z))
}

fun hypot(v: Vector3) = hypot(v.x, v.y, v.z)

fun hypot(x: Double, y: Double, z: Double, w: Double): Double {
    return sqrt(squared(x, y, z, w))
}

fun hypot(v: Vector4) = hypot(v.x, v.y, v.z, v.w)

fun safeAcos(v: Double): Double {
    return if (v < -1.0) PI else if (v > +1.0) 0.0 else acos(v)
}
