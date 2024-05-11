package edu.kdmk.cipher.implementation.RSAKeyGen

import io.github.oshai.kotlinlogging.KotlinLogging
import java.math.BigInteger
import java.security.SecureRandom

private val logger = KotlinLogging.logger {}

//"data class" could be used but its not recommended for this case (requires at least one constructor parameter)
//allow key size to be set in constructor
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

    private fun generatePrivateKey(phi: BigInteger, e: BigInteger): BigInteger {
        return e.modInverse(phi)
    }

    private fun generatePublicKey(phi: BigInteger, secureRandom: SecureRandom): BigInteger {
        var e: BigInteger
        do {
            e = BigInteger(phi.bitLength(), secureRandom)
        } while (e < BigInteger.ONE || e > phi || e.gcd(phi) != BigInteger.ONE) //!!!! tu jest bomba bo gdzies widdzialem ze 2 i jeszcze copilot wali <= >= takze beka
        return e
    }

    private fun generatePhi(p: BigInteger, q: BigInteger): BigInteger {
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))
    }

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
        return n.toByteArray()
    }

    override fun getPublicKey(): ByteArray {
        return publicKey.toByteArray()
    }

    override fun getPrivateKey(): ByteArray {
        return privateKey.toByteArray()
    }

    override fun getKeysAsPair(): Pair<ByteArray, ByteArray> {
        return Pair(getPublicKey(), getPrivateKey())
    }

    override fun KeysAndNAsTriple(): Triple<ByteArray, ByteArray, ByteArray> {
        return Triple(getPublicKey(), getPrivateKey(), getN())
    }
}

//package edu.kdmk.cipher.implementation
//
//import io.github.oshai.kotlinlogging.KotlinLogging
//import java.math.BigInteger
//import java.security.SecureRandom
//
//private val logger = KotlinLogging.logger {}
//
////"data class" could be used but its not recommended for this case (requires at least one constructor parameter)
////allow key size to be set in constructor
//class RSAKeyPairGen : KeyPairGen {
//
//    val KEY_SIZE = 1024 //private not required as it is final and we want to have access to this field in tests
//    val publicKey: BigInteger
//    val privateKey: BigInteger
//
//    init {
//        logger.info { "Generating RSA key pair" }
//        val secureRandom = SecureRandom()
//        val (p, q) = generateDistinctProbablePrimes(secureRandom)
//        val n = p.multiply(q)
////        val p = BigInteger.valueOf(5) //for testing
////        val q = BigInteger.valueOf(7) //for testing
//        val phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)) //Euler's totient function
//        publicKey = generatePublicKey(phi, secureRandom)
//        privateKey = publicKey.modInverse(phi)
//    }
//
//    private fun generatePublicKey(phi: BigInteger, secureRandom: SecureRandom): BigInteger {
//        var e: BigInteger
//        do {
//            e = BigInteger(phi.bitLength(), secureRandom)
//        } while (e < BigInteger.ONE || e > phi || e.gcd(phi) != BigInteger.ONE) //!!!! tu jest bomba bo gdzies widdzialem ze 2 i jeszcze copilot wali <= >= takze beka
//        return e
//    }
//
//    private fun generateDistinctProbablePrimes(secureRandom: SecureRandom): Pair<BigInteger, BigInteger> {
//        val PQ_SIZE = KEY_SIZE / 2
//        val p = BigInteger.probablePrime(PQ_SIZE, secureRandom)
//        var q: BigInteger
//        do {
//            q = BigInteger.probablePrime(PQ_SIZE, secureRandom) //guarantees p != q
//        } while (p == q)
//        return Pair(p, q)
//    }
//
//    override fun getPublicKey(): ByteArray {
//        return publicKey.toByteArray()
//    }
//
//    override fun getPrivateKey(): ByteArray {
//        return privateKey.toByteArray()
//    }
//
//    override fun getKeysAsPair(): Pair<ByteArray, ByteArray> {
//        return Pair(getPublicKey(), getPrivateKey())
//    }
//}

//
//package edu.kdmk.cipher.implementation
//
//import io.github.oshai.kotlinlogging.KotlinLogging
//import java.math.BigInteger
//import java.security.SecureRandom
//
//private val logger = KotlinLogging.logger {}
//
////"data class" could be used but its not recommended for this case (requires at least one constructor parameter)
////allow key size to be set in constructor
//class RSAKeyPairGen : KeyPairGen {
//
//    val KEY_SIZE = 1024 //private not required as it is final and we want to have access to this field in tests
//    private val p: BigInteger
//    private val q: BigInteger
//    val n: BigInteger
//    private val phi: BigInteger
//    val publicKey: BigInteger
//    val privateKey: BigInteger
//
//    init {
//        logger.info { "Generating RSA key pair" }
//        val secureRandom = SecureRandom()
//        val pqPair = generateDistinctProbablePrimes(secureRandom) // val (p, q) = generateDistinctProbablePrimes(secureRandom)
//        p = pqPair.first
//        q = pqPair.second
//        n = p.multiply(q)
//        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)) //Euler's totient function
//        publicKey = generatePublicKey(phi, secureRandom)
//        privateKey = publicKey.modInverse(phi)
//    }
//
//    private fun generatePublicKey(phi: BigInteger, secureRandom: SecureRandom): BigInteger {
//        var e: BigInteger
//        do {
//            e = BigInteger(phi.bitLength(), secureRandom)
//        } while (e < BigInteger.ONE || e > phi || e.gcd(phi) != BigInteger.ONE) //!!!! tu jest bomba bo gdzies widdzialem ze 2 i jeszcze copilot wali <= >= takze beka
//        return e
//    }
//
//    public fun generateDistinctProbablePrimes(secureRandom: SecureRandom): Pair<BigInteger, BigInteger> {
//        val PQ_SIZE = KEY_SIZE / 2
//        val p = BigInteger.probablePrime(PQ_SIZE, secureRandom)
//        var q: BigInteger
//        do {
//            q = BigInteger.probablePrime(PQ_SIZE, secureRandom) //guarantees p != q
//        } while (p == q)
//        return Pair(p, q)
//    }
//
//    override fun getPublicKey(): ByteArray {
//        return publicKey.toByteArray()
//    }
//
//    override fun getPrivateKey(): ByteArray {
//        return privateKey.toByteArray()
//    }
//
//    override fun getKeysAsPair(): Pair<ByteArray, ByteArray> {
//        return Pair(getPublicKey(), getPrivateKey())
//    }
//
//    override fun toString(): String {
//        return "RSAKeyPairGen(KEY_SIZE=$KEY_SIZE, n=$n, publicKey=$publicKey, privateKey=$privateKey)"
//    }
//
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as RSAKeyPairGen
//
//        if (publicKey != other.publicKey) return false
//        if (privateKey != other.privateKey) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = publicKey.hashCode()
//        result = 31 * result + privateKey.hashCode()
//        return result
//    }
//
//}