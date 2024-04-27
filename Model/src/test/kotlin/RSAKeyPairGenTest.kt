import edu.kdmk.cipher.implementation.RSAKeyPairGen
import io.github.oshai.kotlinlogging.KotlinLogging
import io.mockk.every
import io.mockk.mockkConstructor
import io.mockk.spyk
import io.mockk.unmockkConstructor
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.math.BigInteger

private val logger = KotlinLogging.logger {}

class RSAKeyPairGenTest {



    @Test
    fun keyGenTest() {
        val rsaKeyPairGen = RSAKeyPairGen()
        logger.info { "Public key: ${rsaKeyPairGen.privateKey}" }
        logger.info { "Private key: ${rsaKeyPairGen.publicKey}" }
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