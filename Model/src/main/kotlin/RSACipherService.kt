package edu.kdmk.cipher.implementation

import java.math.BigInteger

enum class OperationMode {
    ENCRYPT_PRIVATE_DECRYPT_PUBLIC,
    ENCRYPT_PUBLIC_DECRYPT_PRIVATE
}

class RSACipherService(private val keyPair: KeyPair, private val operationMode: OperationMode) : CipherService {
    val n = BigInteger(keyPair.getN())
    val publicKey = BigInteger(keyPair.getPublicKey())
    val privateKey = BigInteger(keyPair.getPrivateKey())

    override fun encrypt(data: ByteArray): ByteArray {
        val data = BigInteger(data)
        return when (operationMode) {
            OperationMode.ENCRYPT_PRIVATE_DECRYPT_PUBLIC -> data.modPow(privateKey, n).toByteArray()
            OperationMode.ENCRYPT_PUBLIC_DECRYPT_PRIVATE -> data.modPow(publicKey, n).toByteArray()
        }}

    override fun decrypt(data: ByteArray): ByteArray {
        val data = BigInteger(data)
        return when (operationMode) {
            OperationMode.ENCRYPT_PRIVATE_DECRYPT_PUBLIC -> data.modPow(publicKey, n).toByteArray()
            OperationMode.ENCRYPT_PUBLIC_DECRYPT_PRIVATE -> data.modPow(privateKey, n).toByteArray()
        }}

}