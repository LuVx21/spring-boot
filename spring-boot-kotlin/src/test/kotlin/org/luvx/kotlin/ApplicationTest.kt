package org.luvx.kotlin

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class ApplicationTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun getTest() {
//        val result = testRestTemplate
//                // ...
//                .getForEntity("/user/1", User::class.java)

//        assertNotNull(result)
//        assertEquals(result?.statusCode, HttpStatus.OK)
//        assertEquals(result?.body, "hello world")
    }
}