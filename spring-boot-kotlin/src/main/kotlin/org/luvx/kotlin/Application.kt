package org.luvx.kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

private val log = mu.KotlinLogging.logger {}

@ComponentScan(basePackages = ["org.luvx.kotlin", "org.luvx.java"])
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
    log.info { "启动成功......" }
}
