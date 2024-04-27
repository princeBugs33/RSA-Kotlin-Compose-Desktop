package edu.kdmk.cipher.implementation

interface CipherService {
    fun encrypt(data: ByteArray): ByteArray
    fun decrypt(data: ByteArray): ByteArray
}