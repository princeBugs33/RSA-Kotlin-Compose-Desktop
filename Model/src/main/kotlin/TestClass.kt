package edu.kdmk.cipher.implementation

import io.github.oshai.kotlinlogging.KotlinLogging
import java.math.BigInteger

private val logger = KotlinLogging.logger {}

class TestClass {

    val testString: String

    fun returnString(): String {
        return "Hello World!"
    }

    init {
        testString = returnString()
    }

    private fun testFunction() {
        //println("Hello World!")
        logger.info { "Testing eoooooooaduioha;dh ;" }
        var bigInteger = BigInteger("1234567890")

    }
}
