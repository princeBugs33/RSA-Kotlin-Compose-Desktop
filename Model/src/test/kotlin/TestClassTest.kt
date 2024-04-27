import edu.kdmk.cipher.implementation.TestClass
import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TestClassTest {

    @Test
    fun test() {
        val mock = spyk<TestClass>(recordPrivateCalls = true)
        every { mock["returnString"]() } returns "Hello Mockk!"
        assertEquals("Hello Mockk!", mock.returnString())
        assertEquals("Hello Mockk!", mock.testString)
    }
}