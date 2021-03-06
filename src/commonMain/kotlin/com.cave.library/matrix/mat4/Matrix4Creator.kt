package com.cave.library.matrix.mat4

import com.cave.library.angle.Radian
import com.cave.library.matrix.mat3.Matrix3Creator
import com.cave.library.matrix.mat3.StaticMatrix3
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
     *  @param aspectRatio: The aspect ratio of the view port, height/width
     *  @param near: The distance to the near plane
     *  @param far: the distance to the far plane, accepts infinity as a value
     */
    fun perspective(fov: Radian, aspectRatio: Double, near: Double, far: Double): M {
        val array = DoubleArray(arraySize)
        array.perspective(fov, aspectRatio, near, far)
        return create(array)
    }

    open fun from(matrix4: StaticMatrix4): M {
        val array = DoubleArray(arraySize)
        matrix4.fill(array)
        return create(array)
    }

}