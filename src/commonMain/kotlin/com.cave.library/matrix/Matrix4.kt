package com.cave.library.matrix

import com.cave.library.vector.vec4.VariableVector4
import com.cave.library.vector.vec4.Vector4

interface StaticMatrix4 {
    operator fun get(row: Int, column: Int): Double
    fun column(index: Int): Vector4
    fun row(index: Int): Vector4
}

interface Matrix4 : StaticMatrix4 {
    fun identity()
    fun normal()
    fun invert()
    fun transpose()
    fun zero()

    operator fun set(row: Int, column: Int)
    override fun column(index: Int): VariableVector4
    override fun row(index: Int): VariableVector4
}
