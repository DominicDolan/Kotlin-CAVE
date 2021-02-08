package com.cave.library.matrix.mat3

import com.cave.library.matrix.ArrayToMatrix
import com.cave.library.matrix.mat4.StaticMatrix4
import com.cave.library.vector.vec2.Vector2
import com.cave.library.vector.vec3.Vector3

abstract class Matrix3Creator<M> : ArrayToMatrix {
    protected abstract fun create(array: DoubleArray): M

    open fun from(array: DoubleArray): M {
        val arr = DoubleArray(arraySize) { if (it < array.size) array[it] else 0.0 }
        return create(arr)
    }

    open fun from(matrix4: StaticMatrix4): M {
        val array = DoubleArray(arraySize)
        matrix4.fill(array)
        return create(array)
    }

    open fun identity(): M {
        val array = DoubleArray(arraySize) { 0.0 }
        array.identity()
        return create(array)
    }

    open fun zero(): M {
        val array = DoubleArray(arraySize) { 0.0 }

        return create(array)
    }

    open fun scaled(x: Double, y: Double, z: Double): M {
        val array = DoubleArray(arraySize)
        array.identity()
        array.scale(x, y, z)
        return create(array)
    }

    open fun scaled(vector: Vector3): M {
        return scaled(vector.x, vector.y, vector.z)
    }

    open fun scaled(vector: Vector2, z: Double = 0.0): M {
        return scaled(vector.x, vector.y, z)
    }
}