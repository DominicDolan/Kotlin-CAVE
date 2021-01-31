package com.cave.library.vector.vec2

interface Vector2i {
    val x: Int
    val y: Int

    override fun toString(): String

    operator fun get(i: Int): Int {
        return if (i == 0) x else if (i == 1) y else throw Exception("OutOfBoundsException: Tried to call 3rd coordinate on a 2D vector")
    }


    fun getOrZero(i: Int): Int {
        return when (i) {
            0 -> x
            1 -> y
            else -> 0
        }
    }

    companion object {
        fun create(x: Int, y: Int) = object : Vector2i {
            override val x: Int = x
            override val y: Int = y

            override fun toString(): String {
                return "($x, $y)"
            }
        }

        fun from(other: Vector2i) = create(other.x, other.y)
    }
}


interface VariableVector2i : Vector2i {
    override var x: Int
    override var y: Int

    companion object {
        fun create(x: Int, y: Int) = object : VariableVector2i {
            override var x: Int = x
            override var y: Int = y

            override fun toString(): String {
                return "($x, $y)"
            }
        }

        fun from(other: Vector2i) = create(other.x, other.y)
    }
}


operator fun Vector2i.component1() = this.x
operator fun Vector2i.component2() = this.y