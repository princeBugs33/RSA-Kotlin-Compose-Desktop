package edu.kdmk.cipher.implementation

class StringConverter {
    fun convertToString(data: ByteArray): String {
        return data.toString(Charsets.UTF_8)
    }

    fun convertToByteArray(data: String): ByteArray {
        return data.toByteArray(Charsets.UTF_8)
    }
}