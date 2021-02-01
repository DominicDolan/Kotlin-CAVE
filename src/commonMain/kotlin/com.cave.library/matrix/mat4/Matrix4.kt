package com.cave.library.matrix.mat4

import com.cave.library.vector.vec4.VariableVector4
import com.cave.library.vector.vec4.Vector4

interface Matrix4 : StaticMatrix4 {
    fun identity()
    fun normal()
    fun invert()
    fun transpose()
    fun zero()

    operator fun set(row: Int, column: Int)

}


interface Column4 {

    operator fun set(column: Int, row: Int)
    operator fun get(column: Int): VariableVector4
}
