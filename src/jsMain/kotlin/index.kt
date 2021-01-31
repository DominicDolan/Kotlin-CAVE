import com.cave.library.vector.vec2.plus
import com.cave.library.vector.vec2.vec
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.dom.appendElement
import kotlinx.dom.appendText
import org.w3c.dom.Element

fun main() {
    val vec1 = vec(1.0, 2.0)
    val vec2 = vec(2.0, 1.0)
    println((vec1 + vec2))

    println("hello, cave")

    BaseComponent {
        appendElement("p") { appendText("vec1: $vec1, vec2: $vec2") }
        appendElement("p") { appendText("vec1 + vec2: ${vec1 + vec2}") }

    }

}

class BaseComponent(element: Element.() -> Unit) {
    private var hasLoaded = false

    init {

        var intervalId = 0
        val setup = {
            val el = document.getElementById("app")
            if (el != null) {
                el.appendElement("div", element)

                hasLoaded = true
                window.clearInterval(intervalId)
            }
        }

        intervalId = window.setInterval(setup, 50)
    }
}