import com.cave.library.angle.degrees
import com.cave.library.angle.plus
import com.cave.library.color.Color
import com.cave.library.matrix.mat3.Matrix3
import com.cave.library.matrix.mat4.Matrix4
import org.joml.Matrix4f

fun main() {
    println(Color.toString(Color.rgba(0.0, 1.0, 0.0, 1.0)))
    println(toString(Color.rgba(0.0, 1.0, 0.0, 1.0)))

}

fun toString(color: Color): String {
    return String.format("0x%08X", color.toLong())
}

fun testVariableRotation() {

    var inputAngle = 0.degrees
    val m = Matrix3.identity()

    while (inputAngle.toDegrees() < 180.0) {
        m.rotation.angle = inputAngle

        println("input angle:    $inputAngle")
        println("rotation angle: ${m.rotation.angle.toDegrees()}")
        println()
        inputAngle += 5.degrees
        Thread.sleep(500)
    }

}

fun testStaticRotation() {

    var inputAngle = 0.degrees

    while (inputAngle.toDegrees() < 180.0) {
        val sr = Matrix3.rotated(inputAngle, 0.0, 0.0, 1.0)

        println("input angle:    $inputAngle")
        println("rotation angle: ${sr.rotation.angle.toDegrees()}")
        println()
        inputAngle += 5.degrees
        Thread.sleep(500)
    }

}

fun testMultiplication() {
    val m1 = Matrix4.identity()
        .translation.apply(1.0, 2.0, 0.0)
        .scale.apply(1.0, 2.0)

    println("CAVE:")
    val m2 = Matrix4.identity()
    println("m1:")
    println(m1)
    println("m2:")
    println(m2)
    m1 *= m2

    println()
    println("result")
    println(m1)

    println()
    println("JOML:")

    val m3 = Matrix4f().translate(1f, 2f, 0f).scale(1f, 2f, 1f)
    val m4 = Matrix4f()

    m4.mul(m3)

    println(m4)
}