package com.cave.library.angle

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sign

const val TAU = 2* PI

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

fun Angle.normalized(): Angle {
    val radians = this.toRadians() % TAU
    if (radians < 0) {
        return (TAU + radians).radians
    }
    return radians.radians
}

fun minimumDifference(angle1: Angle, angle2: Angle): Angle {
    val diff = angle2 - angle1
    val radians = diff.toRadians()

    return (sign(radians)*((abs(radians) + PI) % (TAU) - PI)).radians
}

fun maximumDifference(angle1: Angle, angle2: Angle): Angle {
    val diff = angle2.normalized().toRadians() - angle1.normalized().toRadians()

    return if (diff > PI || diff <= -PI) {
        diff.radians
    } else {
        (sign(diff)*-TAU + diff).radians
    }
}

inline fun degreesToRadians(degrees: Double): Double {
    return (degrees*PI /180.0)
}

inline fun radiansToDegrees(radians: Double): Double {
    return (radians*180.0/ PI)
}

fun Angle.isBetween(angle1: Angle, angle2: Angle): Boolean {
    val a1ToThis = minimumDifference(angle1, this).toRadians()
    val a1ToA2 = minimumDifference(angle1, angle2).toRadians()

    return sign(a1ToThis) == sign(a1ToA2) && abs(a1ToThis) < abs(a1ToA2)
}