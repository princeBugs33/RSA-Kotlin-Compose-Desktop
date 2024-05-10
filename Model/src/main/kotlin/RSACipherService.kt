package edu.kdmk.cipher.implementation

import io.github.oshai.kotlinlogging.KotlinLogging
import java.math.BigInteger

private val logger = KotlinLogging.logger {}

class RSACipherService(private val keyPair: KeyPair, private val operationMode: OperationMode) : CipherService {
//    val n = BigInteger(keyPair.getN())
//    val publicKey = BigInteger(keyPair.getPublicKey())
//    val privateKey = BigInteger(keyPair.getPrivateKey())
    val n: BigInteger
    val publicKey: BigInteger
    val privateKey: BigInteger

    init {
        try {
            n = BigInteger(keyPair.getN())
            publicKey = BigInteger(keyPair.getPublicKey())
            privateKey = BigInteger(keyPair.getPrivateKey())
        } catch (e: Exception) {
            logger.error { "Error initializing RSABlindSignatureService" }
            throw RuntimeException("Error initializing RSACipherService", e)
        }
    }

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