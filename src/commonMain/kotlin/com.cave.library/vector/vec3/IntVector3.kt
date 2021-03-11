package com.cave.library.vector.vec3

import com.cave.library.vector.vec2.IntVector2
import com.cave.library.vector.vec2.VariableIntVector2

interface IntVector3 : IntVector2 {
    val z: Int


    override fun get(i: Int): Int {
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            else -> throw Exception("OutOfBoundsException: Tried to call 4th coordinate on a 3D vector")
        }
    }

    override fun getOrZero(i: Int): Int {
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            else -> 0
        }
    }

    companion object {
        fun create(x: Int, y: Int, z: Int) = object : IntVector3 {
            override val x: Int = x
            override val y: Int = y
            override val z: Int = z

            override fun toString() = "($x, $y, $z)"

        }

        fun from(other: IntVector3) = create(other.x, other.y, other.z)

        fun from(xy: IntVector2, z: Int = 0) = create(xy.x, xy.y, z)

    }
}


interface VariableIntVector3 : IntVector3, VariableIntVector2 {
    override var z: Int

    companion object {
        fun create(x: Int, y: Int, z: Int) = object : VariableIntVector3 {
            override var x: Int = x
            override var y: Int = y
            override var z: Int = z

            override fun toString() = "($x, $y, $z)"

        }

        fun from(other: IntVector3) = create(other.x, other.y, other.z)

        fun from(xy: IntVector2, z: Int = 0) = create(xy.x, xy.y, z)

    }
}

operator fun IntVector3.component1() = this.x
operator fun IntVector3.component2() = this.y
operator fun IntVector3.component3() = this.z