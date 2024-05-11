package edu.kdmk.cipher.implementation.RSABlindSignature

import edu.kdmk.cipher.implementation.RSAKeyGen.KeyPair
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.xml.bind.DatatypeConverter
import java.math.BigInteger
import java.security.SecureRandom

private val logger = KotlinLogging.logger {}

class RSABlindSignatureService(private val keyPair: KeyPair, private val secureRandom: SecureRandom) {
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
            throw RuntimeException("Error initializing RSABlindSignatureService", e)
        }
    }

    fun sign(data: ByteArray): ByteArray {
        val data = BigInteger(1, data)
        var blinder: BigInteger
//        do {
//            blinder = BigInteger(n.bitLength(), secureRandom)
//        } while (blinder.gcd(n) != BigInteger.ONE)
        do {
            blinder = BigInteger(n.bitLength(), secureRandom)
        } while (blinder.gcd(n) != BigInteger.ONE || blinder >= n || blinder <= BigInteger.ONE)

        // m' ≡ mr^e (mod N)
        logger.info { "Blinder: " + DatatypeConverter.printHexBinary(blinder.toByteArray()) }
        val blindedData = (data.multiply(blinder.modPow(publicKey, n))).mod(n)

        // s' ≡ (m')^d (mod N)
        val blindSignature = blindedData.modPow(privateKey, n)

        // Unblind the signature
        val signature = (blindSignature.multiply(blinder.modInverse(n))).mod(n)

        return signature.toByteArray()
    }

    fun verify(data: ByteArray, signature: ByteArray): Boolean {
        val data = BigInteger(1, data)
        val signature = BigInteger(1, signature)
        return signature.modPow(publicKey, n) == data
    }
}