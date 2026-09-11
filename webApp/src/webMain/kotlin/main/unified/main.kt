package main.unified

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import main.unified.resources.Res
import org.w3c.dom.HTMLLinkElement

@OptIn(
    ExperimentalComposeUiApi::class,
    DelicateCoroutinesApi::class
)
fun main() {
    // Async resolve the resource URI and inject the favicon without blocking first render
    GlobalScope.launch {
        try {
            // getUri takes the resource path string, not the resource object
            val faviconUri = Res.getUri("drawable/favorites_icon.svg")

            if (document.querySelector("link[rel='icon']") == null) {
                val link = document.createElement("link") as HTMLLinkElement
                link.rel = "icon"
                link.type = "image/svg+xml"
                link.href = faviconUri
                document.head?.appendChild(link)
            }
        } catch (e: Exception) {
            println("Failed to resolve favicon URI $e")
        }
    }

    ComposeViewport {
        App()
    }
}
