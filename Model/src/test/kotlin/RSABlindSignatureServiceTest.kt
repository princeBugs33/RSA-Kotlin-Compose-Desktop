import edu.kdmk.cipher.implementation.*
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.security.SecureRandom

private val logger = KotlinLogging.logger {}

class RSABlindSignatureServiceTest {

    @Test
    fun blindSignatureTest() {
        val secureRandom = SecureRandom()
        val keyPair : KeyPair = RSAKeyPairGenerate(secureRandom)
        val testString = "chuj na podlodze"
        val convertedString = convertStringToByteArray(testString)

        val rsaBlindSignatureService = RSABlindSignatureService(keyPair, secureRandom)
        val signature = rsaBlindSignatureService.sign(convertedString)
        assertTrue(rsaBlindSignatureService.verify(convertedString, signature))
    }

    @Test
    fun blindSignatureTest2() {
        val secureRandom = SecureRandom()
        val keyPair : KeyPair = RSAKeyPairGenerate(secureRandom)
        val testString = "Hello World!"
        val convertedString = convertStringToByteArray(testString)
        val modifiedTestString = "Hello World"
        val modifiedConvertedString = convertStringToByteArray(modifiedTestString)

        val rsaBlindSignatureService = RSABlindSignatureService(keyPair, secureRandom)
        val signature = rsaBlindSignatureService.sign(convertedString)
        assertFalse(rsaBlindSignatureService.verify(modifiedConvertedString, signature))
    }

    @Test
    fun blindSignatureTest3() {
        val secureRandom = SecureRandom()
        val keyPair : KeyPair = RSAKeyPairGenerate(secureRandom)
        val testString = "chuj na podlodze"
        val convertedString = convertStringToByteArray(testString)
        val hashedString = hashFromByteArray(convertedString)

        val rsaBlindSignatureService = RSABlindSignatureService(keyPair, secureRandom)
        val signature = rsaBlindSignatureService.sign(hashedString)
        assertTrue(rsaBlindSignatureService.verify(hashedString, signature))
    }

    @Test
    fun blindSignatureTest4() {
        val readFile = convertFileToByteArray("testowy.jpg")
        val hashedFile = hashFromByteArray(readFile)
        logger.info { "Hashed file: " + hashStringFromByteArray(hashedFile) }

        val secureRandom = SecureRandom()
        val keyPair : KeyPair = RSAKeyPairGenerate(secureRandom)
        val rsaBlindSignatureService = RSABlindSignatureService(keyPair, secureRandom)
        val signature = rsaBlindSignatureService.sign(hashedFile)
        assertTrue(rsaBlindSignatureService.verify(hashedFile, signature))



    }
}