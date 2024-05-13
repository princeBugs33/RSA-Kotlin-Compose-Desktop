package edu.kdmk.cipher.implementation.RSAKeyGen

import io.github.oshai.kotlinlogging.KotlinLogging
import java.math.BigInteger
import java.security.SecureRandom

private val logger = KotlinLogging.logger {}

data class RSAKeyPairGenerate(private val secureRandom: SecureRandom) : KeyPair {

    val KEY_SIZE = 1024 //private not required as it is final and we want to have access to this field in tests
    val publicKey: BigInteger
    val privateKey: BigInteger
    val n: BigInteger

    init {
        logger.info { "Generating RSA key pair" }
        val (p, q) = generateDistinctProbablePrimes(secureRandom)
        n = p.multiply(q)
        val phi = generatePhi(p, q)
        publicKey = generatePublicKey(phi, secureRandom)
        privateKey = generatePrivateKey(phi, publicKey)
    }

    // Generowanie klucza prywatnego poprzez znalezienie odwrotności modularnej klucza publicznego i phi
    private fun generatePrivateKey(phi: BigInteger, e: BigInteger): BigInteger {
        return e.modInverse(phi)
    }

    // Generowanie klucza publicznego poprzez znalezienie liczby 'e', która jest względnie pierwsza z phi i 1 < e < phi
    private fun generatePublicKey(phi: BigInteger, secureRandom: SecureRandom): BigInteger {
        var e: BigInteger
        do {
            e = BigInteger(phi.bitLength(), secureRandom)
        } while (e < BigInteger.ONE || e > phi || e.gcd(phi) != BigInteger.ONE)
        return e
    }

    // Obliczanie funkcji Eulera (phi) dla modułu poprzez pomnożenie (p-1) i (q-1)
    private fun generatePhi(p: BigInteger, q: BigInteger): BigInteger {
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))
    }

    // Generowanie dwóch różnych względnie pierwszych liczb pierwszych o rozmiarze KEY_SIZE/2
    private fun generateDistinctProbablePrimes(secureRandom: SecureRandom): Pair<BigInteger, BigInteger> {
        val PQ_SIZE = KEY_SIZE / 2
        val p = BigInteger.probablePrime(PQ_SIZE, secureRandom)
        var q: BigInteger
        do {
            q = BigInteger.probablePrime(PQ_SIZE, secureRandom) //guarantees p != q
        } while (p == q)
        return Pair(p, q)
    }

    override fun getN(): ByteArray {
        //logger.info { "N: " + n.toByteArray().size }
        return n.toByteArray()
    }

    override fun getPublicKey(): ByteArray {
        //logger.info { "Public key: " + publicKey.toByteArray().size }
        return publicKey.toByteArray()
    }

    override fun getPrivateKey(): ByteArray {
        //logger.info { "Private key: " + privateKey.toByteArray().size }
        return privateKey.toByteArray()
    }

    override fun getKeysAsPair(): Pair<ByteArray, ByteArray> {
        return Pair(getPublicKey(), getPrivateKey())
    }

    override fun KeysAndNAsTriple(): Triple<ByteArray, ByteArray, ByteArray> {
        return Triple(getPublicKey(), getPrivateKey(), getN())
    }
}
