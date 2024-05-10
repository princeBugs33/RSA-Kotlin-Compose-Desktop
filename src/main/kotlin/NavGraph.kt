import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun SetupNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.RSABlindSignature.route
    ) {
        composable(
            route = Screen.RSACipher.route
        ) {
            Cipher(navController = navHostController)
        }
        composable(
            route = Screen.RSABlindSignature.route
        ) {
            BlindSignature(navController = navHostController)
        }

    }
}