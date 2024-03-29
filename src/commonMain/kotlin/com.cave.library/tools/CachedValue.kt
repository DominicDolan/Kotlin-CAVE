package com.cave.library.tools

import com.cave.library.angle.Angle
import com.cave.library.angle.radians

interface CachedDouble {
    fun get(): Double

    companion object {
        fun create(
            dependents: Array<() -> Double>,
            default: Double = 0.0,
            calculate: () -> Double,
        ): CachedDouble {
            return CachedDoubleImpl(dependents, default, calculate)
        }
    }
}

private class CachedDoubleImpl(
    dependents: Array<() -> Double>,
    default: Double = 0.0,
    private val calculate: () -> Double,
) : CachedDouble {

    private var value: Double

    private val dependents = Dependents(dependents)

    init {
        val calculated = calculate()
        value = if (calculated.isFinite()) calculated else default
    }

    override fun get(): Double {
        if (dependents.haveChanged()) {
            val value = calculate()
            if (value.isFinite()) {
                this.value = value
            }
        }

        return value
    }
}

interface CachedRadian {
    fun get(): Angle

    companion object {
        fun create(
            dependents: Array<() -> Double>,
            default: Angle = 0.0.radians,
            calculate: () -> Angle,
        ): CachedRadian {
            return CachedRadianImpl(dependents, default, calculate)
        }
    }
}

private class CachedRadianImpl(
    dependents: Array<() -> Double>,
    default: Angle = 0.0.radians,
    private val calculate: () -> Angle,
) : CachedRadian {

    private var value: Angle = default

    private val dependents = Dependents(dependents)

    override fun get(): Angle {
        if (dependents.haveChanged()) {
            val value = calculate()
            if (value.toRadians().isFinite()) {
                this.value = value
            }
        }

        return value
    }
}


private class Dependents(private val dependents: Array<() -> Double>) {
    private var hasInitialised = false
    private val dependentsCount = dependents.size
    private val cached = DoubleArray(dependentsCount) {
        dependents[it]()
    }

    fun haveChanged(): Boolean {
        var needsRecalculating = !hasInitialised
        for (i in 0 until dependentsCount) {
            if (cached[i] != dependents[i]()) {
                cached[i] = dependents[i]()
                needsRecalculating = true
            }
        }

        hasInitialised = true
        return needsRecalculating
    }
}