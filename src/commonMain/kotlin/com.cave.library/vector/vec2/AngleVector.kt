package com.cave.library.vector.vec2

import com.cave.library.angle.Degree
import com.cave.library.angle.Radian
import com.cave.library.angle.radians


interface AngleVector2 {
    val dimension: Int
        get() = 2

    val x: Radian
    val y: Radian

    operator fun get(i: Int): Radian {
        return if (i == 0) x else if (i == 1) y else throw Exception("OutOfBoundsException: Tried to call 3rd coordinate on a 2D vector")
    }

    fun getOrZero(i: Int): Radian {
        return if (i == 0) x else if (i == 1) y else 0.0.radians
    }

    operator fun component1() = x
    operator fun component2() = y

    companion object {
        fun create(x: Radian, y: Radian) = object : AngleVector2 {

            override val x: Radian = x
            override val y: Radian = y

            override fun toString() = toString(this)
        }

        fun create(x: Degree, y: Degree) = create(x.toRadians(), y.toRadians())
        fun create(other: AngleVector2) = create(other.x, other.y)
        fun create(other: InlineVector) = create(other.x.radians, other.y.radians)
        fun create() = create(0.0.radians, 0.0.radians)

        fun toString(vector: AngleVector2) = "(${vector.x}, ${vector.y})"
    }
}

interface VariableAngleVector2 : AngleVector2 {
    override var x: Radian
    override var y: Radian

    fun set(x: Radian, y: Radian) {
        this.x = x
        this.y = y
    }

    fun set(x: Degree, y: Degree) {
        this.x = x.toRadians()
        this.y = y.toRadians()
    }

    fun set(vector: AngleVector2) = set(vector.x, vector.y)
    fun set(vector: InlineVector) = set(vector.x.radians, vector.y.radians)

    operator fun set(component: Int, value: Radian) {
        when(component) {
            0 -> x = value
            1 -> y = value
            else -> throw Exception("OutOfBoundsException: Tried to set 3rd coordinate on a 2D vector")
        }
    }

    companion object {
        fun create(x: Radian, y: Radian) = object : VariableAngleVector2 {
            override var x: Radian = x
            override var y: Radian = y

            override fun toString() = AngleVector2.toString(this)
        }

        fun create(x: Degree, y: Degree) = create(x.toRadians(), y.toRadians())
        fun create() = create(0.0.radians, 0.0.radians)
        fun create(other: AngleVector2) = create(other.x, other.y)
        fun create(other: InlineVector) = create(other.x.radians, other.y.radians)
    }
}