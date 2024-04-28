import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import edu.kdmk.cipher.implementation.*
import jakarta.xml.bind.DatatypeConverter
import java.security.SecureRandom

//enum class Mode {
//    ENCRYPT_PRIVATE_DECRYPT_PUBLIC,
//    ENCRYPT_PUBLIC_DECRYPT_PRIVATE
//}

@OptIn(ExperimentalStdlibApi::class)
@Composable
@Preview
fun App() {
    var SecureRandom = SecureRandom()
    var keyPair: KeyPair = RSAKeyPairGenerate(SecureRandom)
    var message by remember { mutableStateOf("") }
    var cipher by remember { mutableStateOf("") }
    var keyPrivate by remember { mutableStateOf(DatatypeConverter.printHexBinary(keyPair.getPrivateKey())) }
    var keyPublic by remember { mutableStateOf(DatatypeConverter.printHexBinary(keyPair.getPublicKey())) }
    var keyN by remember { mutableStateOf(DatatypeConverter.printHexBinary(keyPair.getN())) }
    var mode by remember { mutableStateOf(OperationMode.ENCRYPT_PRIVATE_DECRYPT_PUBLIC) }


    MaterialTheme {
        Column (
            modifier = Modifier.padding(16.dp)
        ){
            Row () {
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("Message:") }
                )
                OutlinedTextField(
                    value = cipher,
                    onValueChange = { cipher = it },
                    label = { Text("Cipher:") }
                )
            }
            Row () {
                Button(
                    onClick = {
//                        val SecureRandom = SecureRandom()
//                        val RSAKeyPairGenerate = RSAKeyPairGenerate(SecureRandom)
////                        keyPublic = RSAKeyPairGenerate.getPublicKey().toHexString()
////                        keyPrivate = RSAKeyPairGenerate.getPrivateKey().toHexString()
////                        keyN = RSAKeyPairGenerate.getN().toString()
                        keyPair = RSAKeyPairGenerate(SecureRandom)
                        keyPublic = DatatypeConverter.printHexBinary(keyPair.getPublicKey())
                        keyPrivate = DatatypeConverter.printHexBinary(keyPair.getPrivateKey())
                        keyN = DatatypeConverter.printHexBinary(keyPair.getN())
                    }
                ) {
                    Text("Generate Keys")
                }
                Button(
                    onClick = {
                        if (mode == OperationMode.ENCRYPT_PRIVATE_DECRYPT_PUBLIC) {
                            mode = OperationMode.ENCRYPT_PUBLIC_DECRYPT_PRIVATE

                        } else {
                            mode = OperationMode.ENCRYPT_PRIVATE_DECRYPT_PUBLIC
                        }
                    }
                ) {
                    Text(mode.toString())
                }
                Button(
                    onClick = {
                        keyPair = RSAKeyPairFill(DatatypeConverter.parseHexBinary(keyPublic), DatatypeConverter.parseHexBinary(keyPrivate), DatatypeConverter.parseHexBinary(keyN))
                        val rsaCipherService = RSACipherService(keyPair, mode)
                        if (message.isNotEmpty()) {
                            cipher = java.util.Base64.getEncoder().encodeToString(rsaCipherService.encrypt(message.toByteArray()))
                        }
                    }
                ) {
                    Text("Encrypt")
                }
                Button(
                    onClick = {
                        keyPair = RSAKeyPairFill(DatatypeConverter.parseHexBinary(keyPublic), DatatypeConverter.parseHexBinary(keyPrivate), DatatypeConverter.parseHexBinary(keyN))
                        val rsaCipherService = RSACipherService(keyPair, mode)
                        if (cipher.isNotEmpty()) {
                            message = convertToString(rsaCipherService.decrypt(java.util.Base64.getDecoder().decode(cipher)))

                        }
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
