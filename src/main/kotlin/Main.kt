import Navigation.SetupNavGraph
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

//@OptIn(ExperimentalStdlibApi::class)
@Composable
@Preview
fun App() {
    lateinit var navController: NavHostController

    MaterialTheme {
        navController = rememberNavController()
        SetupNavGraph(navController)
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "RSA",
        state = WindowState(width = 800.dp, height = 600.dp),
        resizable = false
        ) {
        App()
    }
}
