import com.cave.library.vector.vec2.plus
import com.cave.library.vector.vec2.vec
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.dom.appendElement
import kotlinx.dom.appendText
import org.w3c.dom.Element

fun main() {

    BaseComponent("app")

}
abstract class KDOM(rootId: String) {

    init {
        document.addEventListener("DOMContentLoaded", {
            val root = document.getElementById(rootId)
            root?.appendElement("div") {
                render()

            }

        })
    }

    abstract fun Element.render()
}

class BaseComponent(rootId: String) : KDOM(rootId) {
    override fun Element.render() {
        val vec1 = vec(1.0, 2.0)
        val vec2 = vec(2.0, 1.0)
        println((vec1 + vec2))

        println("hello, cave")

        appendElement("p") { appendText("vec1: $vec1, vec2: $vec2") }
        appendElement("p") { appendText("vec1 + vec2: ${vec1 + vec2}") }
    }

}