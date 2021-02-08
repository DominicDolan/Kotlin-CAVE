package com.cave.library.vector.vec2

import com.cave.library.angle.radians
import kotlin.math.atan2
import kotlin.math.hypot

interface Vector2 {
    val x: Double
    val y: Double

    val r get() = hypot(x, y)

    val theta get() = atan2(this.y, this.x).radians

    override fun toString(): String

    operator fun get(i: Int): Double {
        return if (i == 0) x else if (i == 1) y else throw Exception("OutOfBoundsException: Tried to call 3rd coordinate on a 2D vector")
    }

    fun getOrZero(i: Int): Double {
        return if (i == 0) x else if (i == 1) y else 0.0
    }

    companion object {
        fun create(x: Double, y: Double) = object : Vector2 {
            override val x: Double = x
            override val y: Double = y

            override fun toString(): String {
                return "($x, $y)"
            }
        }

        fun create(other: Vector2) = create(other.x, other.y)
        fun create(other: InlineVector) = create(other.x, other.y)
    }
}

interface VariableVector2 : Vector2 {
    override var x: Double
    override var y: Double

    fun set(x: Double, y: Double) {
        this.x = x
        this.y = y
    }

    fun set(vector: Vector2) = set(vector.x, vector.y)
    fun set(vector: InlineVector) = set(vector.x, vector.y)

    operator fun set(component: Int, value: Double) {
        when(component) {
            0 -> x
            1 -> y
            else -> throw Exception("OutOfBoundsException: Tried to set 3rd coordinate on a 2D vector")
        }
    }

    companion object {
        fun create(x: Double, y: Double) = object : VariableVector2 {
            override var x: Double = x
            override var y: Double = y

            override fun toString(): String {
                return "($x, $y)"
            }
        }

        fun create(other: Vector2) = create(other.x, other.y)
        fun create(other: InlineVector) = create(other.x, other.y)
    }
}

operator fun Vector2.component1() = this.x
operator fun Vector2.component2() = this.y
