package com.cave.library.angle

inline class Angle(private val radians: Double) {
    fun toDegrees(): Double {
        return radiansToDegrees(radians)
    }

    fun toRadians(): Double {
        return radians
    }

    companion object {
        fun fromDegrees(degrees: Double): Angle {
            return Angle(degreesToRadians(degrees))
        }

        fun fromRadians(radians: Double): Angle {
            return Angle(radians)
        }
    }
}
