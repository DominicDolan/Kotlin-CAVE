package com.cave.library.angle

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class UtilTest {

    @Test
    fun `normalizing returns an angle between 0 and 360 when the input is less than 360`() {
        val angle = 30.degrees
        val normalized = angle.normalized()

        assertEquals(30.0, normalized.toDegrees(), 0.001)
    }

    @Test
    fun `normalizing returns an angle between 0 and 360 when the input is greater than 360`() {
        val angle = 390.degrees
        val normalized = angle.normalized()

        assertEquals(30.0, normalized.toDegrees(), 0.001)
    }

    @Test
    fun `normalizing returns an angle between 0 and 360 when the input is greater than 720`() {
        val angle = 750.degrees
        val normalized = angle.normalized()

        assertEquals(30.0, normalized.toDegrees(), 0.001)
    }

    @Test
    fun `normalizing returns an angle between 0 and 360 when the input is less than 0`() {
        val angle = (-330).degrees
        val normalized = angle.normalized()

        assertEquals(30.0, normalized.toDegrees(), 0.001)
    }

    @Test
    fun `normalizing returns an angle between 0 and 360 when the input is less than -360`() {
        val angle = (-690).degrees
        val normalized = angle.normalized()

        assertEquals(30.0, normalized.toDegrees(), 0.001)
    }

    @Test
    fun `minimumDifference Returns The Difference for acute angles`() {
        val angle1 = 30.degrees
        val angle2 = 60.degrees

        val diff = minimumDifference(angle1, angle2)

        assertEquals(30.0, diff.toDegrees(), 0.001)
    }

    @Test
    fun `minimumDifference Returns The Difference for obtuse angles`() {
        val angle1 = 30.degrees
        val angle2 = 135.degrees

        val diff = minimumDifference(angle1, angle2)

        assertEquals(105.0, diff.toDegrees(), 0.001)
    }

    @Test
    fun `minimumDifference Returns The negative Difference for angles greater than 180 degrees`() {
        val angle1 = 30.degrees
        val angle2 = 220.degrees

        val diff = minimumDifference(angle1, angle2)

        assertEquals(-170.0, diff.toDegrees(), 0.001)
    }

    @Test
    fun `minimumDifference Returns The Difference for angles greater than 360 degrees`() {
        val angle1 = 30.degrees
        val angle2 = 380.degrees

        val diff = minimumDifference(angle1, angle2)

        assertEquals(-10.0, diff.toDegrees(), 0.001)
    }

    @Test
    fun `minimumDifference Returns The Difference for angles greater than 360 degrees different`() {
        val angle1 = 30.degrees
        val angle2 = 400.degrees

        val diff = minimumDifference(angle1, angle2)

        assertEquals(10.0, diff.toDegrees(), 0.001)
    }

    @Test
    fun `maximumDifference Returns The Difference for acute angles`() {
        val angle1 = 30.degrees
        val angle2 = 60.degrees

        val diff = maximumDifference(angle1, angle2)

        assertEquals(-330.0, diff.toDegrees(), 0.001)
    }

    @Test
    fun `maximumDifference Returns The Difference for obtuse angles`() {
        val angle1 = 30.degrees
        val angle2 = 135.degrees

        val diff = maximumDifference(angle1, angle2)

        assertEquals(-255.0, diff.toDegrees(), 0.001)
    }

    @Test
    fun `maximumDifference Returns The negative Difference for angles greater than 180 degrees`() {
        val angle1 = 30.degrees
        val angle2 = 220.degrees

        val diff = maximumDifference(angle1, angle2)

        assertEquals(190.0, diff.toDegrees(), 0.001)
    }

    @Test
    fun `maximumDifference Returns The Difference for angles greater than 360 degrees`() {
        val angle1 = 30.degrees
        val angle2 = 380.degrees

        val diff = maximumDifference(angle1, angle2)

        assertEquals(350.0, diff.toDegrees(), 0.001)
    }

    @Test
    fun `maximumDifference Returns The Difference for angles greater than 360 degrees different`() {
        val angle1 = 30.degrees
        val angle2 = 400.degrees

        val diff = maximumDifference(angle1, angle2)

        assertEquals(-350.0, diff.toDegrees(), 0.001)
    }

    @Test
    fun `maximumDifference Returns The Difference for angles greater than 360 degrees different - 2`() {
        val angle1 = (-30).degrees
        val angle2 = (-35).degrees

        val diff = maximumDifference(angle1, angle2)

        assertEquals(355.0, diff.toDegrees(), 0.001)
    }

    @Test
    fun `maximumDifference Returns The Difference for angles greater than 360 degrees different - 3`() {
        val angle1 = (-35).degrees
        val angle2 = (-30).degrees

        val diff = maximumDifference(angle1, angle2)

        assertEquals(-355.0, diff.toDegrees(), 0.001)
    }

    @Test
    fun `maximumDifference Returns The Difference for angles that pass from positive to negative`() {
        val angle1 = (-30).degrees
        val angle2 = (30).degrees

        val diff = maximumDifference(angle1, angle2)

        assertEquals(-300.0, diff.toDegrees(), 0.001)
    }

    @Test
    fun `isBetween returns true for an angle between 2 other angles`() {
        val angle1 = 30.degrees
        val angle2 = 60.degrees

        val isBetween = 45.degrees.isBetween(angle1, angle2)

        assert(isBetween)
    }

    @Test
    fun `isBetween returns false for an angle more than 2 other angles`() {
        val angle1 = 30.degrees
        val angle2 = 60.degrees

        val isBetween = 90.degrees.isBetween(angle1, angle2)

        assertFalse(isBetween)
    }
    @Test
    fun `isBetween returns false for an angle less than 2 other angles`() {
        val angle1 = 30.degrees
        val angle2 = 60.degrees

        val isBetween = 0.degrees.isBetween(angle1, angle2)

        assertFalse(isBetween)
    }
    @Test
    fun `isBetween returns true for an angle between 2 other angles with negative values`() {
        val angle1 = (-30).degrees
        val angle2 = (-60).degrees

        val isBetween = (-45).degrees.isBetween(angle1, angle2)

        assert(isBetween)
    }
    @Test
    fun `isBetween returns true for an angle multiple of 360 between 2 other negative angles`() {
        val angle1 = (-30).degrees
        val angle2 = (-60).degrees

        val isBetween = (315).degrees.isBetween(angle1, angle2)

        assert(isBetween)
    }

    @Test
    fun `isBetween returns true for a negative angle between 2 other angles with multiples of 360 values`() {
        val angle1 = (330).degrees
        val angle2 = (300).degrees

        val isBetween = (-45).degrees.isBetween(angle1, angle2)

        assert(isBetween)
    }

}