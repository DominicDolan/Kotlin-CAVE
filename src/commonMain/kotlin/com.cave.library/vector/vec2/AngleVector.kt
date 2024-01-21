package com.cave.library.vector.vec2

import com.cave.library.angle.Angle
import com.cave.library.angle.radians


interface AngleVector2 {
    val dimension: Int
        get() = 2

    val x: Angle
    val y: Angle

    operator fun get(i: Int): Angle {
        return if (i == 0) x else if (i == 1) y else throw Exception("OutOfBoundsException: Tried to call 3rd coordinate on a 2D vector")
    }

    fun getOrZero(i: Int): Angle {
        return if (i == 0) x else if (i == 1) y else 0.0.radians
    }

    operator fun component1() = x
    operator fun component2() = y

    companion object {
        fun create(x: Angle, y: Angle) = object : AngleVector2 {

            override val x: Angle = x
            override val y: Angle = y

            override fun toString() = toString(this)
        }

        fun create(other: AngleVector2) = create(other.x, other.y)
        fun create(other: InlineVector) = create(other.x.radians, other.y.radians)
        fun create() = create(0.0.radians, 0.0.radians)

        fun toString(vector: AngleVector2) = "(${vector.x}, ${vector.y})"
    }
}

interface VariableAngleVector2 : AngleVector2 {
    override var x: Angle
    override var y: Angle

    fun set(x: Angle, y: Angle) {
        this.x = x
        this.y = y
    }

    fun set(vector: AngleVector2) = set(vector.x, vector.y)
    fun set(vector: InlineVector) = set(vector.x.radians, vector.y.radians)

    operator fun set(component: Int, value: Angle) {
        when(component) {
            0 -> x = value
            1 -> y = value
            else -> throw Exception("OutOfBoundsException: Tried to set 3rd coordinate on a 2D vector")
        }
    }

    companion object {
        fun create(x: Angle, y: Angle) = object : VariableAngleVector2 {
            override var x: Angle = x
            override var y: Angle = y

            override fun toString() = AngleVector2.toString(this)
        }

        fun create() = create(0.0.radians, 0.0.radians)
        fun create(other: AngleVector2) = create(other.x, other.y)
        fun create(other: InlineVector) = create(other.x.radians, other.y.radians)
    }
}