package com.cave.library.matrix.mat4

import com.cave.library.angle.Angle
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

    /**
     *  @param fov: The vertical field of view angle in Radians
     *  @param aspectRatio: The aspect ratio of the view port, width/height
     *  @param near: The distance to the near plane
     *  @param far: the distance to the far plane, accepts infinity as a value
     */
    fun perspective(fov: Angle, aspectRatio: Double, near: Double = 0.0, far: Double = Double.POSITIVE_INFINITY): M {
        val array = DoubleArray(arraySize)
        array.perspective(fov.toRadians(), aspectRatio, near, far)
        return create(array)
    }

    open fun from(matrix4: Matrix4): M {
        val array = DoubleArray(arraySize)
        matrix4.fill(array)
        return create(array)
    }

}