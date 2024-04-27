import edu.kdmk.cipher.implementation.RSAKeyPairGenerate
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.math.BigInteger
import java.security.SecureRandom
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.jvm.isAccessible

private val logger = KotlinLogging.logger {}

class RSAKeyPairTest {

    @Test
    fun testGeneratePhi() {
        val rsaKeyPairGen = RSAKeyPairGenerate(SecureRandom())
        val generatePhi = RSAKeyPairGenerate::class.declaredMemberFunctions.first { it.name == "generatePhi" }
        generatePhi.isAccessible = true

        val p = BigInteger.valueOf(11)
        val q = BigInteger.valueOf(13)
        val phi = BigInteger.valueOf(120)

        val result = generatePhi.call(rsaKeyPairGen, p, q) as BigInteger

        assertEquals(phi, result)
    }

    @Test
    fun testGeneratePublicKey() {
        val rsaKeyPairGen = RSAKeyPairGenerate(SecureRandom())
        val generatePublicKey = RSAKeyPairGenerate::class.declaredMemberFunctions.first { it.name == "generatePublicKey" }
        generatePublicKey.isAccessible = true

        val phi = BigInteger.valueOf(120) // You can replace this with a value that makes sense for your test
        val secureRandom = SecureRandom()
        val result = generatePublicKey.call(rsaKeyPairGen, phi, secureRandom) as BigInteger

        // Add your assertions here. For example:
        assertTrue(result > BigInteger.ONE && result < phi && result.gcd(phi) == BigInteger.ONE)
    }

    @Test
    fun testGeneratePrivateKey() {
        val rsaKeyPairGen = RSAKeyPairGenerate(SecureRandom())
        val generatePrivateKey = RSAKeyPairGenerate::class.declaredMemberFunctions.first { it.name == "generatePrivateKey" }
        generatePrivateKey.isAccessible = true

        val phi = BigInteger.valueOf(120)
        val e = BigInteger.valueOf(7)
        val d = BigInteger.valueOf(103)

        val result = generatePrivateKey.call(rsaKeyPairGen, phi, e) as BigInteger
        assertEquals(d, result)
    }

    @Test
    fun testGenerateDistinctProbablePrimes() {
        val rsaKeyPairGen = RSAKeyPairGenerate(SecureRandom())
        val generateDistinctProbablePrimes = RSAKeyPairGenerate::class.declaredMemberFunctions.first { it.name == "generateDistinctProbablePrimes" }
        generateDistinctProbablePrimes.isAccessible = true

        val secureRandom = SecureRandom()
        val result = generateDistinctProbablePrimes.call(rsaKeyPairGen, secureRandom) as Pair<BigInteger, BigInteger>

        assertNotEquals(result.first, result.second)
    }

    @Test
    fun getPublicKey() {
    }

    @Test
    fun getPrivateKey() {
    }

    @Test
    fun getKeysAsPair() {
    }
}