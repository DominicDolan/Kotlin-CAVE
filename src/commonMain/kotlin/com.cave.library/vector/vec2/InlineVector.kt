package com.cave.library.vector.vec2

import com.cave.library.angle.Degree
import com.cave.library.angle.Radian
import kotlin.math.cos
import kotlin.math.sin

inline class InlineVector(private val xy: Long): Vector2 {
    override val x get() = Float.fromBits((xy shr 32).toInt()).toDouble()
    override val y get() = Float.fromBits(xy.toInt()).toDouble()

    fun toLong() = xy

    override fun toString(): String {
        return "($x, $y)"
    }

    companion object {
        fun create(x: Float, y: Float): InlineVector {
            val xBits = x.toRawBits()
            val yBits = y.toRawBits()
            val xyBits = (yBits.toLong() shl 32 ushr 32) or (xBits.toLong() shl 32)
            return InlineVector(xyBits)
        }

        fun create(other: Vector2) = create(other.x.toFloat(), other.y.toFloat())
    }
}

fun vec(x: Number, y: Number) = InlineVector.create(x.toFloat(), y.toFloat())

fun vec(r: Number, theta: Degree): InlineVector {
    val x = r.toDouble()* cos(theta.toRadians().toDouble())
    val y = r.toDouble()* sin(theta.toRadians().toDouble())
    return vec(x, y)
}

fun vec(r: Number, theta: Radian): InlineVector {
    val x = r.toDouble()* cos(theta.toDouble())
    val y = r.toDouble()* sin(theta.toDouble())
    return vec(x, y)
}

fun vec(vector: Vector2) = vec(vector.x, vector.y)

operator fun InlineVector.plus(other: InlineVector) : InlineVector {
    return vec(this.x.toFloat() + other.x.toFloat(), this.y.toFloat() + other.y.toFloat())
}

operator fun InlineVector.minus(other: InlineVector) : InlineVector {
    return vec(this.x.toFloat() - other.x.toFloat(), this.y.toFloat() - other.y.toFloat())
}

operator fun InlineVector.times(other: InlineVector): Float = x.toFloat()* other.x.toFloat() + y.toFloat()* other.y.toFloat()

operator fun InlineVector.times(scale: Number) = vec(this.x.toFloat() * scale.toFloat(), this.y.toFloat() * scale.toFloat())

operator fun InlineVector.div(scale: Number) = vec(this.x.toFloat() / scale.toFloat(), this.y.toFloat() / scale.toFloat())

operator fun InlineVector.unaryMinus() = vec(-this.x.toFloat(), -this.y.toFloat())

infix fun InlineVector.equals(other: InlineVector) = this.x.toFloat() == other.x.toFloat() && this.y.toFloat() == other.y.toFloat()

fun InlineVector.distanceTo(other: InlineVector): Float {
    return (vec(this) - vec(other)).r.toFloat()
}

fun InlineVector.normalize() = vec(1.0, this.theta)

fun InlineVector.dot(other: InlineVector) = this.x*other.x + this.y*other.y

fun InlineVector.cross(other: InlineVector) = (this.x * other.y - this.y * other.x)

fun InlineVector.perpendicular() = vec(this.y, -this.x)
