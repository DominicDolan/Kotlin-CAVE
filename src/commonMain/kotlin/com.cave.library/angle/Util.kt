package com.cave.library.angle

import kotlin.math.PI

inline val Number.degrees: Degree
    get() = Degree(this.toDouble())

inline val Number.radians: Radian
    get() = Radian(this.toDouble())

operator fun Angle.minus(other: Angle): Angle = (this.toRadians().toDouble() - other.toRadians().toDouble()).radians

operator fun Degree.unaryMinus(): Degree = Degree(-(this.toDouble()))

operator fun Radian.unaryMinus(): Radian = Radian(-(this.toDouble()))

operator fun Degree.plus(other: Degree): Degree {
    return (this.toDouble() + other.toDouble()).degrees
}

operator fun Radian.plus(other: Radian): Radian {
    return (this.toDouble() + other.toDouble()).radians
}

operator fun Degree.minus(other: Degree): Degree {
    return (this.toDouble() - other.toDouble()).degrees
}

operator fun Radian.minus(other: Radian): Radian {
    return (this.toDouble() - other.toDouble()).radians
}

operator fun Degree.div(other: Degree): Double {
    return (this.toDouble() / other.toDouble())
}

operator fun Radian.div(other: Radian): Double {
    return (this.toDouble() / other.toDouble())
}

operator fun Degree.times(other: Number): Degree {
    return (this.toDouble() * other.toDouble()).degrees
}

operator fun Radian.times(other: Number): Radian {
    return (this.toDouble() * other.toDouble()).radians
}

fun minimumDifference(angle1: Radian, angle2: Radian): Radian {
    val diff = angle2 - angle1
    return ((diff.toDouble() + PI) % (2.0* PI) - PI).radians
}