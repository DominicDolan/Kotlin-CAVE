package com.cave.library.tools

import com.cave.library.angle.Radian

interface CachedDouble {
    fun get(): Double

    companion object {
        fun create(
            dependents: Array<() -> Double>,
            calculate: () -> Double
        ): CachedDouble {
            return CachedDoubleImpl(dependents, calculate)
        }
    }
}

private class CachedDoubleImpl(
    dependents: Array<() -> Double>,
    private val calculate: () -> Double
) : CachedDouble {

    private var value: Double = calculate()

    private val dependents = Dependents(dependents)

    override fun get(): Double {
        if (dependents.haveChanged()) {
            value = calculate()
        }

        return value
    }
}

interface CachedRadian {
    fun get(): Radian

    companion object {
        fun create(
            dependents: Array<() -> Double>,
            calculate: () -> Radian
        ): CachedRadian {
            return CachedRadianImpl(dependents, calculate)
        }
    }
}

private class CachedRadianImpl(
    dependents: Array<() -> Double>,
    private val calculate: () -> Radian
) : CachedRadian {

    private var value: Radian = calculate()

    private val dependents = Dependents(dependents)

    override fun get(): Radian {
        if (dependents.haveChanged()) {
            value = calculate()
        }

        return value
    }
}


private class Dependents(private val dependents: Array<() -> Double>) {
    private val dependentsCount = dependents.size
    private val cached = DoubleArray(dependentsCount) {
        dependents[it]()
    }

    fun haveChanged(): Boolean {
        var needsRecalculating = false
        for (i in 0 until dependentsCount) {
            if (cached[i] != dependents[i]()) {
                cached[i] = dependents[i]()
                needsRecalculating = true
            }
        }

        return needsRecalculating
    }
}