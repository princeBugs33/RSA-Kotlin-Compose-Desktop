package edu.kdmk.cipher.implementation

import jakarta.xml.bind.DatatypeConverter
import java.security.MessageDigest

fun hashFromByteArray(data: ByteArray): ByteArray {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    return messageDigest.digest(data)
}

fun hashStringFromByteArray(data: ByteArray): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hash = messageDigest.digest(data)
    return DatatypeConverter.printHexBinary(hash)
}