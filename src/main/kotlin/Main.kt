import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import edu.kdmk.cipher.implementation.TestClass


@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    //val testClass = TestClass()
//    loggingConfiguration {
//        DEFAULT_CONSOLE()
//    }
//    val logger = logger("main")
//    logger.info("Hello, world!")

    MaterialTheme {
        Column (
            modifier = androidx.compose.ui.Modifier.padding(16.dp),
        ){

        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
