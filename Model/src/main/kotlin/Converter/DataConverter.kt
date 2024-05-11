package edu.kdmk.cipher.implementation.Converter

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.commons.io.FileUtils
import java.io.File

private val logger = KotlinLogging.logger {}

fun convertByteArrayToString(data: ByteArray): String {
    return data.toString(Charsets.UTF_8)
}

fun convertStringToByteArray(data: String): ByteArray {
    return data.toByteArray(Charsets.UTF_8)
}

fun convertByteArrayToFile(data: ByteArray, path: String) {
    val file = File(path)
    try {
        FileUtils.writeByteArrayToFile(file, data)
    } catch (e: Exception) {
//        println("Error: $e")
        logger.error { "Error: $e" }
    }
}

fun convertFileToByteArray(path: String): ByteArray {
    val file = File(path)
    var byteArray = ByteArray(0)
    try {
        byteArray = FileUtils.readFileToByteArray(file)
    } catch (e: Exception) {
//        println("Error: $e")
        logger.error { "Error: $e" }
    }
    return byteArray
}
