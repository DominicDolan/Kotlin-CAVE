package com.cave.library.tools

import kotlin.math.sqrt

fun hypot(x: Double, y: Double, z: Double): Double {
    return sqrt(x*x + y*y + z*z)
}

fun hypot(x: Double, y: Double, z: Double, w: Double): Double {
    return sqrt(x*x + y*y + z*z + w*w)
}