import edu.kdmk.cipher.implementation.TestClass
import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.security.MessageDigest
import jakarta.xml.bind.DatatypeConverter

class TestClassTest {

    @Test
    fun test() {
        val mock = spyk<TestClass>(recordPrivateCalls = true)
        every { mock["returnString"]() } returns "Hello Mockk!"
        assertEquals("Hello Mockk!", mock.returnString())
        assertEquals("Hello Mockk!", mock.testString)
    }

    @Test
    fun hashTest() {
        val text = "Hello World!"
        val byteArray = text.toByteArray()
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(byteArray)
        val hashString = DatatypeConverter.printHexBinary(hash)
        println(hashString)
    }
}