package com.cave.library.matrix.mat4

import com.cave.library.matrix.ArrayToMatrix
import com.cave.library.matrix.mat3.Matrix3Creator
import com.cave.library.vector.vec2.Vector2
import com.cave.library.vector.vec3.Vector3

abstract class Matrix4Creator<M> : Matrix3Creator<M>() {

    fun translation(x: Double, y: Double, z: Double): M {
        val array = DoubleArray(arraySize)
        array.identity()
        array.translate(x, y, z)
        return create(array)
    }

    fun translation(vector: Vector3): M {
        return translation(vector.x, vector.y, vector.z)
    }

    fun translation(vector: Vector2, z: Double = 0.0): M {
        return translation(vector.x, vector.y, z)
    }
}