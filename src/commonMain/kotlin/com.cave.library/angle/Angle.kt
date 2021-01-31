package com.cave.library.angle

import kotlin.math.PI

interface Angle {
    fun toDouble(): Double
    fun toFloat(): Float
    fun toDegrees(): Degree
    fun toRadians(): Radian
}

inline class Degree(private val degrees: Double) : Angle {
    override fun toDegrees() = toDouble().degrees

    override fun toRadians() = (this.toDouble()*PI /180.0).radians

    override fun toDouble() = degrees

    override fun toFloat() = degrees.toFloat()

    override fun toString(): String {
        return toDouble().toString()
    }
}

inline class Radian(private val radians: Double) : Angle {
    override fun toDegrees() = (this.toDouble()*180.0/ PI).degrees

    override fun toRadians() = toDouble().radians

    override fun toDouble() = radians

    override fun toFloat() = radians.toFloat()

    override fun toString(): String {
        return toDouble().toString()
    }
}