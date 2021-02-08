import com.cave.library.angle.degrees
import com.cave.library.angle.radians
import com.cave.library.matrix.mat3.Matrix3
import com.cave.library.matrix.mat3.StaticMatrix3
import com.cave.library.vector.vec3.Vector3
import org.joml.Matrix3f

fun main() {

    println()
    val joml1 = Matrix3f()
    joml1.m00(1f); joml1.m10(2f); joml1.m20(3f)
    joml1.m01(4f); joml1.m11(5f); joml1.m21(6f)
    joml1.m02(7f); joml1.m12(8f); joml1.m22(9f)

    val joml2 = Matrix3f()
    joml2.m00(10f); joml2.m10(11f); joml2.m20(12f)
    joml2.m01(13f); joml2.m11(14f); joml2.m21(15f)
    joml2.m02(16f); joml2.m12(17f); joml2.m22(18f)

    joml1.mul(joml2)

    println("joml multiplication:")
    println(joml1.toString())
    println()
    println("CAVE Multiplication")
    val m1 = StaticMatrix3.from(doubleArrayOf(
        1.0, 2.0, 3.0,
        4.0, 5.0, 6.0,
        7.0, 8.0, 9.0
    ))

    val m2 = StaticMatrix3.from(doubleArrayOf(
        10.0, 11.0, 12.0,
        13.0, 14.0, 15.0,
        16.0, 17.0, 18.0
    ))


    println(StaticMatrix3.multiplied(m1, m2))

    println()

    println("Rotation")

    val sr = StaticMatrix3.rotated(30.degrees, 0.0, 0.0, 1.0)
    println(sr)
    println("rotation: ${sr.rotation.angle.toDegrees()}")

    val r = Matrix3.identity()

    println("identity:")
    println(r)

    println("axis: ${Vector3.from(r.rotation)}")
//    r.rotation.set(1.degrees, 0.0, 0.0, 1.0)
    println(r)
    println("axis: ${Vector3.from(r.rotation)}")

//    r.rotation.set(30.degrees, 0.0, 0.0, 1.0)
    println(r)
    println("rotation: ${r.rotation.angle.toDegrees()}")
}