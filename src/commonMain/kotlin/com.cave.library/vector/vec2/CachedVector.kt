package com.cave.library.vector.vec2

import com.cave.library.angle.Radian
import com.cave.library.tools.CachedDouble
import com.cave.library.tools.CachedRadian
import com.cave.library.vector.vec3.Vector3
import com.cave.library.vector.vec4.Vector4
import kotlin.math.sqrt

class CachedR(private val vector: Vector2) {

    private val r: CachedDouble

    init {
        val dependentsArray = Array(vector.dimension) {
            { vector[it] }
        }
        r = CachedDouble.create(dependentsArray) {
            var rSquared = 0.0
            for (i in 0 until vector.dimension) {
                rSquared += vector[i]
            }
            sqrt(rSquared)
        }
    }
}

abstract class CachedVector2(private val vector: Vector2) : Vector2 {
    private val rCache = CachedDouble.create(arrayOf({ x }, { y })) { super.r }
    override val r: Double
        get() = rCache.get()

    private val thetaCache = CachedRadian.create(arrayOf({ x }, { y })) { super.theta }
    override val theta: Radian
        get() = thetaCache.get()

    private var _x = 0.0
    override val x: Double
        get() {
            if (xDependentsHaveChanged()) {
                _x = vector.x
            }
            return _x
        }

    private var _y = 0.0
    override val y: Double
        get() {
            if (yDependentsHaveChanged()) {
                _y = vector.y
            }
            return _y
        }

    protected open fun xDependentsHaveChanged(): Boolean = false
    protected open fun yDependentsHaveChanged(): Boolean = false
}

abstract class CachedVector3(private val vector: Vector3) : CachedVector2(vector), Vector3 {
    private val rCache = CachedDouble.create(arrayOf({ x }, { y }, { z })) { vector.r }
    override val r: Double
        get() = rCache.get()

    private val thetaCache = CachedRadian.create(arrayOf({ x }, { y }, { z })) { vector.theta }
    override val theta: Radian
        get() = thetaCache.get()


    private var _z = 0.0
    override val z: Double
        get() {
            if (zDependentsHaveChanged()) {
                _z = vector.z
            }
            return _z
        }

    protected abstract fun zDependentsHaveChanged(): Boolean
}

abstract class CachedVector4(private val vector: Vector4) : CachedVector3(vector), Vector4 {
    private val rCache = CachedDouble.create(arrayOf({ x }, { y }, { z })) { vector.r }
    override val r: Double
        get() = rCache.get()

    private val thetaCache = CachedRadian.create(arrayOf({ x }, { y }, { z })) { vector.theta }
    override val theta: Radian
        get() = thetaCache.get()

    private var _w = 0.0
    override val w: Double
        get() {
            if (wDependentsHaveChanged()) {
                _w = vector.w
            }
            return _w
        }

    protected abstract fun wDependentsHaveChanged(): Boolean
}