package com.cave.library.angle

import kotlin.math.PI

inline val Number.degrees: Angle
    get() = Angle.fromDegrees(this.toDouble())

inline val Number.radians: Angle
    get() = Angle.fromRadians(this.toDouble())

operator fun Angle.minus(other: Angle): Angle = (this.toRadians() - other.toRadians()).radians

operator fun Angle.unaryMinus(): Angle = Angle.fromRadians(-(this.toRadians()))

operator fun Angle.plus(other: Angle): Angle {
    return (this.toRadians() + other.toRadians()).radians
}

operator fun Angle.div(other: Angle): Double {
    return (this.toRadians() / other.toRadians())
}

operator fun Angle.times(other: Number): Angle {
    return (this.toRadians() * other.toDouble()).radians
}

fun minimumDifference(angle1: Angle, angle2: Angle): Angle {
    val diff = angle2 - angle1
    return ((diff.toRadians() + PI) % (2.0* PI) - PI).radians
}

inline fun degreesToRadians(degrees: Double): Double {
    return (degrees*PI /180.0)
}

inline fun radiansToDegrees(radians: Double): Double {
    return (radians*180.0/ PI)
}