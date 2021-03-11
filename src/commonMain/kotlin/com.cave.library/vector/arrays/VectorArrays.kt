package com.cave.library.vector.arrays

import com.cave.library.vector.vec2.Vector2

object Vector2Arrays {

    fun createQuad(left: Number, top: Number, right: Number, bottom: Number): Array<Vector2> {
        return arrayOf(
            Vector2.create(left.toDouble(), top.toDouble()),
            Vector2.create(left.toDouble(), bottom.toDouble()),
            Vector2.create(right.toDouble(), bottom.toDouble()),
            Vector2.create(left.toDouble(), top.toDouble()),
            Vector2.create(right.toDouble(), bottom.toDouble()),
            Vector2.create(right.toDouble(), top.toDouble()))

    }

    fun createRectangle(x: Double, y: Double, width: Double, height: Double): Array<Vector2> {
        return createQuad(x, y + height, x + width, y)
    }

    fun createUnitSquare(): Array<Vector2> {
        return createQuad(0f, 1f, 1f, 0f)
    }

    fun createInvertedUnitSquare(): Array<Vector2> {
        return createQuad(0f, 0f, 1f, 1f)
    }

}