package org.luvx.kotlin

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

//@SpringBootTest
class ApplicationKtTest {
    @Test
    fun testA() {
        val k = K()
        println(
                k.method("world")
        )
    }
}