package com.cave.library.vector.vec2

interface IntVector2 {
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
        fun create(x: Int, y: Int) = object : IntVector2 {
            override val x: Int = x
            override val y: Int = y

            override fun toString(): String {
                return "($x, $y)"
            }
        }

        fun from(other: IntVector2) = create(other.x, other.y)

        fun toString(vector: IntVector2) = "(${vector.x}, ${vector.y})"
    }
}


interface VariableIntVector2 : IntVector2 {
    override var x: Int
    override var y: Int

    fun set(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    fun set(vector: IntVector2) = set(vector.x, vector.y)

    companion object {
        fun create(x: Int, y: Int) = object : VariableIntVector2 {
            override var x: Int = x
            override var y: Int = y

            override fun toString(): String {
                return "($x, $y)"
            }
        }

        fun create() = create(0, 0)

        fun from(other: IntVector2) = create(other.x, other.y)
    }
}


operator fun IntVector2.component1() = this.x
operator fun IntVector2.component2() = this.y