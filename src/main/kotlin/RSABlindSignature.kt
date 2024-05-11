import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.kdmk.cipher.implementation.Converter.convertFileToByteArray
import edu.kdmk.cipher.implementation.Converter.convertStringToByteArray
import edu.kdmk.cipher.implementation.Converter.hashFromByteArray
import edu.kdmk.cipher.implementation.Converter.hashStringFromByteArray
import edu.kdmk.cipher.implementation.RSABlindSignature.RSABlindSignatureService
import edu.kdmk.cipher.implementation.RSAKeyGen.KeyPair
import edu.kdmk.cipher.implementation.RSAKeyGen.RSAKeyPairFill
import edu.kdmk.cipher.implementation.RSAKeyGen.RSAKeyPairGenerate
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.xml.bind.DatatypeConverter
import java.awt.FileDialog
import java.awt.Frame
import java.security.SecureRandom

private val logger = KotlinLogging.logger {}

enum class DataConverterType {
    MESSAGE,
    FILE;

    override fun toString(): String {
        return when (this) {
            MESSAGE -> "Message"
            FILE -> "File"
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BlindSignature(navController: NavController) {
    val secureRandom = SecureRandom()
    var keyPair: KeyPair = RSAKeyPairGenerate(secureRandom)
    var messageORfilePath by remember { mutableStateOf("") }
    var signature by remember { mutableStateOf("") }
    var keyPrivate by remember { mutableStateOf(DatatypeConverter.printHexBinary(keyPair.getPrivateKey())) }
    var keyPublic by remember { mutableStateOf(DatatypeConverter.printHexBinary(keyPair.getPublicKey())) }
    var keyN by remember { mutableStateOf(DatatypeConverter.printHexBinary(keyPair.getN())) }
    var isVerifyDialogOpen by remember { mutableStateOf(false) }
    var verificationStatus by remember { mutableStateOf(false) }
    var dataConverterType by remember { mutableStateOf(DataConverterType.MESSAGE) }
    var isMessageUsable by remember { mutableStateOf(true) }
    var messageORfilePathText by remember { mutableStateOf("Message") }


    MaterialTheme {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
//            OutlinedTextField(
//                value = message,
//                onValueChange = { message = it },
//                label = { Text("Message:") }
//            )
//            OutlinedTextField(
//                value = signature,
//                onValueChange = { signature = it },
//                label = { Text("Signature:") }
//            )
            Row (
                modifier = Modifier.fillMaxWidth().height(270.dp),

            ) {
                OutlinedTextField(
                    value = messageORfilePath,
                    onValueChange = { messageORfilePath = it },
                    label = { Text(messageORfilePathText) },
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 2.5.dp)
                        .fillMaxHeight(),
                    singleLine = false,
                    enabled = isMessageUsable
                )
                OutlinedTextField(
                    value = signature,
                    onValueChange = { signature = it },
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
                        navController.navigate(route = Navigation.Screen.RSACipher.route)
                    },
                    enabled = true
                ) {
                    Text("RSA Cipher")
                }

                Button(
                    modifier = Modifier.padding(5.dp),
                    onClick = {
                        keyPair = RSAKeyPairGenerate(secureRandom)
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
//                        if (dataConverterType == DataConverterType.MESSAGE) {
//                            dataConverterType = DataConverterType.FILE
//                            isMessageUsable = true
//                        } else {
//                            dataConverterType = DataConverterType.MESSAGE
//                            isMessageUsable = false
//                        }
                        when (dataConverterType) {
                            DataConverterType.MESSAGE -> {
                                dataConverterType = DataConverterType.FILE
                                logger.info { "Switched to File signature" }
                                isMessageUsable = false
                                messageORfilePathText = "File path:"
                                messageORfilePath = ""
                            }
                            DataConverterType.FILE -> {
                                dataConverterType = DataConverterType.MESSAGE
                                logger.info { "Switched to Message signature" }
                                isMessageUsable = true
                                messageORfilePathText = "Message:"
                                messageORfilePath = ""
                            }
                        }
                    }
                ) {
                    //Text(dataConverterType.toString())
                    when (dataConverterType) {
                        DataConverterType.MESSAGE -> {
                            Text("Switch to File")
                        }
                        DataConverterType.FILE -> {
                            Text("Switch to Message")
                        }
                    }
                }

                if (dataConverterType == DataConverterType.FILE) {
                    Button(
                        modifier = Modifier.padding(5.dp),
                        onClick = {
                            val filePath = pickFile()
                            logger.info { "Selected file: $filePath" }
                            if (filePath != null) {
                                messageORfilePath = filePath
                            }
                        }
                    ) {
                        Text("Select file")
                    }
                }

                Button(
                    modifier = Modifier.padding(5.dp),
                    onClick = {
                        try {
                            keyPair = RSAKeyPairFill(
                                DatatypeConverter.parseHexBinary(keyPublic),
                                DatatypeConverter.parseHexBinary(keyPrivate),
                                DatatypeConverter.parseHexBinary(keyN)
                            )
                            when (dataConverterType) {
                                DataConverterType.MESSAGE -> {
                                    if (messageORfilePath.isNotEmpty()) {
                                        signature = signMessage(secureRandom, keyPair, messageORfilePath)
                                    }

                                }
                                DataConverterType.FILE -> {
                                    if (messageORfilePath.isNotEmpty()) {
                                        signature = signFile(secureRandom, keyPair, messageORfilePath)
                                    }

                                }
                            }

                        } catch (e: Exception) {
                            logger.error { "Error while signing!" }
                        }
                    }
                ) {
                    Text("Sign")
                }

                Button(
                    modifier = Modifier.padding(5.dp),
                    onClick = {
                        try {
                            keyPair = RSAKeyPairFill(
                                DatatypeConverter.parseHexBinary(keyPublic),
                                DatatypeConverter.parseHexBinary(keyPrivate),
                                DatatypeConverter.parseHexBinary(keyN)
                            )
                            when (dataConverterType) {
                                DataConverterType.MESSAGE -> {
                                    if (messageORfilePath.isNotEmpty() && signature.isNotEmpty()) {
                                        verificationStatus = verifyMessage(secureRandom, keyPair, messageORfilePath, signature)
                                        isVerifyDialogOpen = true
                                    }
                                }
                                DataConverterType.FILE -> {
                                    if (messageORfilePath.isNotEmpty() && signature.isNotEmpty()) {
                                        verificationStatus =
                                            verifyFile(secureRandom, keyPair, messageORfilePath, signature)
                                        isVerifyDialogOpen = true
                                    }
                                }

                            }


                        } catch (e: Exception) {
                            logger.error { "Error while verifying!" }
                        }
                    }
                ) {
                    Text("Verify")
                }
            }

            OutlinedTextField(
                value = keyPublic,
                onValueChange = { keyPublic = it },
                label = { Text("Public Key:") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = keyPrivate,
                onValueChange = { keyPrivate = it },
                label = { Text("Private Key:") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = keyN,
                onValueChange = { keyN = it },
                label = { Text("N:") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

        }

        if (isVerifyDialogOpen) {
            AlertDialog(
                onDismissRequest = {  },
                title = { Text(text = "Verification") },
                //text = { Text("Your verification message here") },
                text = {
                    if (verificationStatus) {
                        Text("Verification successful!")
                    } else {
                        Text("Verification failed!")
                    }
                },
                confirmButton = {
                    Button(onClick = { isVerifyDialogOpen = false }) {
                        Text("OK")
                    }
                },
//                properties = DialogProperties(scrimColor = Color.Transparent)
            )

        }

    }


}

@Composable
@Preview
fun BlindSignaturePreview() {
    BlindSignature(navController = rememberNavController())
}

fun signMessage(secureRandom: SecureRandom, keyPair: KeyPair, message: String): String {

    val convertedMessage = convertStringToByteArray(message)
    val hashedMessage = hashFromByteArray(convertedMessage)
    logger.info { "Hashed message: " + hashStringFromByteArray(hashedMessage) }

    val rsaBlindSignatureService = RSABlindSignatureService(keyPair, secureRandom)
    val signature = rsaBlindSignatureService.sign(hashedMessage)
    //logger.info { "Signature: " + convertByteArrayToString(signature) }
    //return convertByteArrayToString(signature)
    return DatatypeConverter.printHexBinary(signature)
}

fun verifyMessage(secureRandom: SecureRandom, keyPair: KeyPair, message: String, signature: String): Boolean {
    val convertedMessage = convertStringToByteArray(message)
    val hashedMessage = hashFromByteArray(convertedMessage)
    val convertedSignature = DatatypeConverter.parseHexBinary(signature)

    val rsaBlindSignatureService = RSABlindSignatureService(keyPair, secureRandom)
    logger.info { "Verification status : " + rsaBlindSignatureService.verify(hashedMessage, convertedSignature)}
    return rsaBlindSignatureService.verify(hashedMessage, convertedSignature)

}

fun pickFile(): String? {
    val dialog = FileDialog(Frame(), "Select File to Open")
    dialog.mode = FileDialog.LOAD
    dialog.isVisible = true
    return dialog.files.firstOrNull()?.absolutePath
}

fun signFile(secureRandom: SecureRandom, keyPair: KeyPair, message: String): String {
    val readFile = convertFileToByteArray(message)
    val hashedFile = hashFromByteArray(readFile)
    logger.info { "Hashed file: " + hashStringFromByteArray(hashedFile) }

    val rsaBlindSignatureService = RSABlindSignatureService(keyPair, secureRandom)
    val signature = rsaBlindSignatureService.sign(hashedFile)
    return DatatypeConverter.printHexBinary(signature)
}

fun verifyFile(secureRandom: SecureRandom, keyPair: KeyPair, message: String, signature: String): Boolean {
    val readFile = convertFileToByteArray(message)
    val hashedFile = hashFromByteArray(readFile)
    val convertedSignature = DatatypeConverter.parseHexBinary(signature)

    val rsaBlindSignatureService = RSABlindSignatureService(keyPair, secureRandom)
    logger.info { "Verification status : " + rsaBlindSignatureService.verify(hashedFile, convertedSignature)}
    return rsaBlindSignatureService.verify(hashedFile, convertedSignature)

}