import Navigation.Screen
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.kdmk.cipher.implementation.Converter.convertByteArrayToString
import edu.kdmk.cipher.implementation.RSACipher.OperationMode
import edu.kdmk.cipher.implementation.RSACipher.RSACipherService
import edu.kdmk.cipher.implementation.RSAKeyGen.KeyPair
import edu.kdmk.cipher.implementation.RSAKeyGen.RSAKeyPairFill
import edu.kdmk.cipher.implementation.RSAKeyGen.RSAKeyPairGenerate
import jakarta.xml.bind.DatatypeConverter
import java.security.SecureRandom
import java.util.*


@Composable
fun Cipher(navController: NavController) {
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

//            Row () {
//                OutlinedTextField(
//                    value = message,
//                    onValueChange = { message = it },
//                    label = { Text("Message:") }
//                )
//                OutlinedTextField(
//                    value = cipher,
//                    onValueChange = { cipher = it },
//                    label = { Text("Cipher:") }
//                )
//            }
            Row (
                modifier = Modifier.fillMaxWidth().height(220.dp),

                ) {
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("Message:") },
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 2.5.dp)
                        .fillMaxHeight(),
                    singleLine = false,
                    //enabled = isMessageUsable
                )
                OutlinedTextField(
                    value = cipher,
                    onValueChange = { cipher = it },
                    label = { Text("Signature:") },
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 2.5.dp)
                        .fillMaxHeight(),
                    singleLine = false
                )
            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.padding(5.dp),
                    onClick = {
                        navController.navigate(route = Screen.RSABlindSignature.route)
                    }
                ) {
                    Text("Blind Signature")
                }
                Button(
                    modifier = Modifier.padding(5.dp),
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
                    modifier = Modifier.padding(5.dp),
                    onClick = {
                        try {
                            keyPair = RSAKeyPairFill(DatatypeConverter.parseHexBinary(keyPublic), DatatypeConverter.parseHexBinary(keyPrivate), DatatypeConverter.parseHexBinary(keyN))
                            //keyPair = RSAKeyPairFill(DatatypeConverter.parseHexBinary(keyPublic), DatatypeConverter.parseHexBinary(keyPrivate), DatatypeConverter.parseHexBinary(keyN))
                            val rsaCipherService = RSACipherService(keyPair, mode)
                            if (message.isNotEmpty()) {
                                cipher = java.util.Base64.getEncoder().encodeToString(rsaCipherService.encrypt(message.toByteArray()))
                            }
                        } catch (e: Exception) {
                            println("Invalid key")
//                            keyN = ""
//                            keyPrivate = ""
//                            keyPublic = ""
                        }

                    }
                ) {
                    Text("Encrypt")
                }
                Button(
                    modifier = Modifier.padding(5.dp),
                    onClick = {
                        try {
                            keyPair = RSAKeyPairFill(DatatypeConverter.parseHexBinary(keyPublic), DatatypeConverter.parseHexBinary(keyPrivate), DatatypeConverter.parseHexBinary(keyN))
                        } catch (e: Exception) {
                            println("Invalid key")
//                            keyN = ""
//                            keyPrivate = ""
//                            keyPublic = ""
                        }
                        //keyPair = RSAKeyPairFill(DatatypeConverter.parseHexBinary(keyPublic), DatatypeConverter.parseHexBinary(keyPrivate), DatatypeConverter.parseHexBinary(keyN))
                        val rsaCipherService = RSACipherService(keyPair, mode)
                        if (cipher.isNotEmpty()) {
                            message = convertByteArrayToString(rsaCipherService.decrypt(Base64.getDecoder().decode(cipher)))

                        }
                    }
                ) {
                    Text("Decrypt")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.padding(5.dp),
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
            }


            OutlinedTextField(
                value = keyPublic,
                onValueChange = { keyPublic = it },
                label = { Text("Public Key:") },
                singleLine = true
            )
            OutlinedTextField(
                value = keyPrivate,
                onValueChange = { keyPrivate = it },
                label = { Text("Private Key:") },
                singleLine = true
            )
            OutlinedTextField(
                value = keyN,
                onValueChange = { keyN = it },
                label = { Text("N:") },
                singleLine = true
            )


        }
    }
}

@Composable
@Preview
fun CipherPreview() {
    Cipher(
        navController = rememberNavController()
    )
}