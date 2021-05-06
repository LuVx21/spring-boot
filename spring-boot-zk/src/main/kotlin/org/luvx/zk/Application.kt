package org.luvx.zk

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

private val log = mu.KotlinLogging.logger {}

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
    log.info { "启动成功......" }
}
