import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import edu.kdmk.cipher.implementation.RSAKeyPairFill
import edu.kdmk.cipher.implementation.RSAKeyPairGenerate
import jakarta.xml.bind.DatatypeConverter
import java.security.SecureRandom


@OptIn(ExperimentalStdlibApi::class)
@Composable
@Preview
fun App() {

    var messagePublic by remember { mutableStateOf("") }
    var messagePrivate by remember { mutableStateOf("") }
    var keyPrivate by remember { mutableStateOf("") }
    var keyPublic by remember { mutableStateOf("") }
    var keyN by remember { mutableStateOf("") }

    MaterialTheme {
        Column (){
            Row () {
                OutlinedTextField(
                    value = messagePublic,
                    onValueChange = { messagePublic = it },
                    label = { Text("Public Message") }
                )
                OutlinedTextField(
                    value = messagePrivate,
                    onValueChange = { messagePrivate = it },
                    label = { Text("Private Message") }
                )
            }
            Row () {
                Button(
                    onClick = {
                        val SecureRandom = SecureRandom()
                        val RSAKeyPairGenerate = RSAKeyPairGenerate(SecureRandom)
//                        keyPublic = RSAKeyPairGenerate.getPublicKey().toHexString()
//                        keyPrivate = RSAKeyPairGenerate.getPrivateKey().toHexString()
//                        keyN = RSAKeyPairGenerate.getN().toString()
                        keyPublic = DatatypeConverter.printHexBinary(RSAKeyPairGenerate.getPublicKey())
                        keyPrivate = DatatypeConverter.printHexBinary(RSAKeyPairGenerate.getPrivateKey())
                        keyN = DatatypeConverter.printHexBinary(RSAKeyPairGenerate.getN())
                    }
                ) {
                    Text("Generate Keys")
                }
                Button(
                    onClick = {

                    }
                ) {
                    Text("Pick Mode")
                }
                Button(
                    onClick = {

                    }
                ) {
                    Text("Encrypt")
                }
                Button(
                    onClick = {

                    }
                ) {
                    Text("Decrypt")
                }
            }
            OutlinedTextField(
                value = keyPublic,
                onValueChange = { keyPublic = it },
                label = { Text("Public Key:") }
            )
            OutlinedTextField(
                value = keyPrivate,
                onValueChange = { keyPrivate = it },
                label = { Text("Private Key:") }
            )
            OutlinedTextField(
                value = keyN,
                onValueChange = { keyN = it },
                label = { Text("N:") }
            )


        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
